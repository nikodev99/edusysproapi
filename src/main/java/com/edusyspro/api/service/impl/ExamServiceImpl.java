package com.edusyspro.api.service.impl;

import com.edusyspro.api.dto.*;
import com.edusyspro.api.dto.custom.*;
import com.edusyspro.api.exception.sql.NotFountException;
import com.edusyspro.api.repository.ExamRepository;
import com.edusyspro.api.service.interfaces.AssignmentService;
import com.edusyspro.api.service.interfaces.EnrollmentService;
import com.edusyspro.api.service.interfaces.ExamService;
import com.edusyspro.api.service.interfaces.ScoreService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExamServiceImpl implements ExamService {

    private final ExamRepository examRepository;
    private final AssignmentService assignmentService;
    private final ScoreService scoreService;
    private final EnrollmentService enrollmentService;

    @Autowired
    public ExamServiceImpl(
            ExamRepository examRepository,
            AssignmentService assignmentService,
            ScoreService scoreService,
            EnrollmentService enrollmentService
    ) {
        this.examRepository = examRepository;
        this.assignmentService = assignmentService;
        this.scoreService = scoreService;
        this.enrollmentService = enrollmentService;
    }

    @Override
    public List<ExamDTO> findAllClasseExams(Integer classeId, String academicYear) {
        return examRepository.findClasseExamsAssignments(classeId, UUID.fromString(academicYear)).stream()
                .map(ExamEssential::toDto)
                .toList();
    }

    @Override
    public ExamDTO findClasseExamsAssignments(Long examId, Integer classeId, String academicYear) {
       ExamDTO examDTO = examRepository.findClasseExamsAssignments(examId, classeId, UUID.fromString(academicYear))
               .orElseThrow(() -> new NotFountException("No Exam Found for ExamID: " + examId))
               .toDto();

       List<AssignmentDTO> assignments = assignmentService.findAllClasseExamAssignments(classeId, academicYear, examDTO.getId());
       return getExamAssignments(examDTO, assignments, null);
    }

    @Override
    public ExamResponse findClasseExamWithCalculations(Long examId, Integer classeId, String academicYear) {
        ExamDTO exam = examRepository.findClasseExamsAssignments(examId, classeId, UUID.fromString(academicYear))
                .orElseThrow(() -> new NotFountException("No Exam Found for ExamID: " + examId))
                .toDto();

        //Get all assignments for this exam
        List<AssignmentDTO> assignments = assignmentService.findAllClasseExamAssignments(classeId, academicYear, exam.getId());

        if (assignments.isEmpty()) {
            return new ExamResponse(exam, List.of(), List.of(),ExamStatistics.builder().build());
        }

        List<EnrollmentDTO> students = enrollmentService.getClasseEnrolledStudents(classeId, academicYear);
        List<Long> assignmentIds = assignments.stream().map(AssignmentDTO::getId).toList();
        List<ScoreDTO> allScores = scoreService.getAllAssignmentScores(assignmentIds);

        Map<Long, Map<UUID, ScoreDTO>> scoreMap = buidScoreMap(allScores);

        List<ExamView> examViews = students.stream()
                .map(student -> calculateStudentExamView(student, assignments, scoreMap))
                .toList();

        examViews = assignRanking(examViews);

        ExamStatistics statistics = calculateExamStatics(examViews);
        statistics.setTotalAssignments(assignments.size());
        statistics.setTotalMarks(Optional.of(allScores).orElseGet(Collections::emptyList).stream().mapToDouble(ScoreDTO::getObtainedMark).sum());

        return new ExamResponse(exam, examViews, assignments, statistics);
    }

    @Override
    public List<ExamProgress> getStudentExamProgress(String studentId, Integer classeId, String academicYear) {
        List<ExamDTO> exams = findAllClasseExams(classeId, academicYear);

        return exams.stream()
                .map(e -> {
                    List<AssignmentDTO> assignments = assignmentService.findAllClasseExamAssignments(classeId, academicYear, e.getId());
                    if (assignments.isEmpty()) return null;

                    List<Long> assignmentIds = assignments.stream().map(AssignmentDTO::getId).toList();
                    List<ScoreDTO> allScores = scoreService.getAssignmentScoresByStudent(assignmentIds, studentId);
                    Map<Long, ScoreDTO> scoreMap = allScores.stream()
                            .collect(Collectors.toMap(s -> s.getAssignment().getId(), s -> s));
                    assignments.forEach(a -> {
                        if (scoreMap.containsKey(a.getId())) {
                            a.setMarks(List.of(scoreMap.get(a.getId())));
                        }
                    });

                    List<ExamViewNested> nested = calculateNestedView(assignments);
                    Double average = calculateTotalAverage(nested);
                    return new ExamProgress(
                            e.getExamType().getName(),
                            average,
                            e.getStartDate() != null ? e.getStartDate().toString() : ""
                    );
                })
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(ExamProgress::examDate))
                .toList();
    }


    @Override
    public ExamDTO findStudentExamsAssignments(Long examId, Integer classeId, String academicYear, String studentId) {
        ExamDTO exam = examRepository.findClasseExamsAssignments(examId, classeId, UUID.fromString(academicYear))
                .orElseThrow(() -> new NotFountException("No Exam Found for ExamID: " + examId))
                .toDto();

        List<AssignmentDTO> assignments = assignmentService.findAllClasseExamAssignments(classeId, academicYear, exam.getId());
        return getExamAssignments(exam, assignments, studentId);
    }

    @Override
    public ExamResponse findStudentExamWithCalculations(Long examId, Integer classeId, String academicYear, String studentId) {
        ExamDTO exam = examRepository.findClasseExamsAssignments(examId, classeId, UUID.fromString(academicYear))
                .orElseThrow(() -> new NotFountException("No Exam Found for ExamID: " + examId))
                .toDto();

        //Get all assignments for the student classe exam
        List<AssignmentDTO> assignments = assignmentService.findAllClasseExamAssignments(classeId, academicYear, examId);

        if (assignments.isEmpty()) {
            return new ExamResponse(exam, List.of(), List.of(),ExamStatistics.builder().build());
        }

        List<Long> assignmentIds = assignments.stream().map(AssignmentDTO::getId).toList();
        List<ScoreDTO> allScores = scoreService.getAssignmentScoresByStudent(assignmentIds, studentId);

        Map<Long, Map<UUID, ScoreDTO>> scoreMap = buidScoreMap(allScores);

        EnrollmentDTO student = EnrollmentDTO.builder()
                .student(StudentDTO.builder()
                        .id(UUID.fromString(studentId))
                        .build())
                .build();

        ExamView examViews = calculateStudentExamView(student, assignments, scoreMap);

        ExamStatistics statistics = calculateExamStatics(List.of(examViews));
        statistics.setTotalAssignments(assignments.size());
        statistics.setTotalMarks(Optional.of(allScores).orElseGet(Collections::emptyList).stream().mapToDouble(ScoreDTO::getObtainedMark).sum());

        return new ExamResponse(exam, List.of(examViews), assignments, statistics);
    }

    @Override
    public ExamDTO findExamById(Long examId) {
        return examRepository.findExamById(examId)
                .orElseThrow(() -> new NotFountException("Exam Not found"))
                .toDto();
    }

    @Override
    public List<ExamDTO> findAllSchoolExams(String schoolId, String academicYear) {
        return examRepository.findAllSchoolExams(
                UUID.fromString(schoolId),
                UUID.fromString(academicYear)
        ).stream().map(ExamEssential::toDto).toList();
    }

    private ExamDTO getExamAssignments(ExamDTO exam, List<AssignmentDTO> assignments, String studentId) {
        if (!assignments.isEmpty()) {
            List<Long> assignmentIds = assignments.stream().map(AssignmentDTO::getId).toList();
            List<ScoreDTO> scores = studentId != null
                ? scoreService.getAssignmentScoresByStudent(assignmentIds, studentId)
                : scoreService.getAllAssignmentScores(assignmentIds);

            Map<Long, List<ScoreDTO>> scoresByAssignments = scores.stream()
                    .filter(score -> score.getAssignment() != null )
                    .collect(Collectors.groupingBy(score -> score.getAssignment().getId()));

            assignments.parallelStream()
                    .forEach(
                            assignment -> assignment.setMarks(scoresByAssignments.getOrDefault(assignment.getId(), Collections.emptyList()))
                    );

            exam.setAssignments(assignments);
        }
        return exam;
    }

    private Map<Long, Map<UUID, ScoreDTO>> buidScoreMap(List<ScoreDTO> scores) {
        return scores.stream()
                .collect(Collectors.groupingBy(
                        score -> score.getAssignment().getId(),
                        Collectors.toMap(
                                score -> score.getStudent().getId(),
                                score -> score
                        )
                ));
    }

    private ExamView calculateStudentExamView(EnrollmentDTO student, List<AssignmentDTO> assignments, Map<Long, Map<UUID, ScoreDTO>> scoreMap) {
        UUID studentId = student.getStudent().getId();

        List<AssignmentDTO> studentAssignments = assignments.stream()
                .map(assignment -> {
                    AssignmentDTO copy = AssignmentDTO.builder().build();
                    BeanUtils.copyProperties(assignment, copy);

                    Map<UUID, ScoreDTO> assignmentScore = scoreMap.get(assignment.getId());
                    if (assignmentScore != null && assignmentScore.containsKey(studentId)) {
                        copy.setMarks(List.of(assignmentScore.get(studentId)));
                    }else {
                        copy.setMarks(List.of());
                    }
                    return copy;
                })
                .toList();

        List<AssignmentTypeAverage> typeAverages = calculateTypeAverages(studentAssignments);
        List<ExamViewNested> nested = calculateNestedView(studentAssignments);
        Double totalAverage = calculateTotalAverage(nested);

        return new ExamView(
                studentId,
                student.getStudent(),
                typeAverages,
                totalAverage,
                0,
                nested
        );
    }

    private List<ExamView> assignRanking(List<ExamView> examViews) {
        List<ExamView> sorted = examViews.stream()
                .sorted((a, b) -> Double.compare(
                        b.getTotalAverage() != null ? b.getTotalAverage() : 0.0,
                        a.getTotalAverage() != null ? a.getTotalAverage() : 0.0
                ))
                .toList();

        for (int i = 0; i < sorted.size(); i++) {
            sorted.get(i).setRank(i + 1);
        }

        return sorted;
    }

    private ExamStatistics calculateExamStatics(List<ExamView> examViews) {
        ExamStatistics stats = ExamStatistics.builder().build();

        long successCount = examViews.stream()
                .filter(v -> v.getTotalAverage() != null && v.getTotalAverage() >= 10.0)
                .count();

        double successRate = !examViews.isEmpty() ? (successCount * 100.0) / examViews.size() : 0.0;
        stats.setSuccessRate(Math.round(successRate * 100.0) / 100.0);

        List<Double> averages = examViews.stream()
                .map(ExamView::getTotalAverage)
                .filter(Objects::nonNull)
                .sorted()
                .toList();

        if (!averages.isEmpty()) {
            int mid = averages.size() / 2;
            double median = averages.size() % 2 == 0
                    ? (averages.get(mid) + averages.get(mid - 1)) /2.0
                    : averages.get(mid);
            stats.setMedianAverage(median);
        }else {
            stats.setMedianAverage(0.0);
        }

        return stats;
    }

    private Double calculateTotalAverage(List<ExamViewNested> nested) {
        if (nested.isEmpty())
            return 0.0;

        double sum =  nested.stream()
                .mapToDouble(n -> n.subjectAverage() != null ? n.subjectAverage() : 0.0)
                .sum();

        return Math.round((sum / nested.size()) * 100.0) / 100.0;
    }

    private List<ExamViewNested> calculateNestedView(List<AssignmentDTO> assignments) {
        return assignments.stream()
                .collect(Collectors.groupingBy(a -> a.getSubject() != null ? a.getSubject() : a.getExamName()))
                .entrySet().stream()
                .map(entry -> {
                    List<AssignmentDTO> subjectAssignments = entry.getValue();
                    Double subjectAverage = calculateAverage(subjectAssignments);

                    return new ExamViewNested(
                            entry.getKey(),
                            subjectAssignments,
                            subjectAverage
                    );
                })
                .toList();
    }

    private List<AssignmentTypeAverage> calculateTypeAverages(List<AssignmentDTO> assignments) {
        return assignments.stream()
                .collect(Collectors.groupingBy(AssignmentDTO::getType))
                .entrySet().stream()
                .map(entry -> {
                    Double avg = calculateAverage(entry.getValue());
                    return new AssignmentTypeAverage(entry.getKey(), avg);
                })
                .toList();
    }

    private Double calculateAverage(List<AssignmentDTO> assignments) {
        double totalMarks = 0.0;
        int totalCoefficient = 0;

        for (AssignmentDTO assignment: assignments) {
            if (assignment.getMarks() != null && !assignment.getMarks().isEmpty()) {
                double mark = assignment.getMarks().get(0).getObtainedMark() != null ? assignment.getMarks().get(0).getObtainedMark() : 0.0 ;
                int coefficient = assignment.getCoefficient() != null ? assignment.getCoefficient() : 1;

                totalMarks += mark * coefficient;
                totalCoefficient += coefficient;
            }
        }

        return totalMarks / totalCoefficient;
    }
}
