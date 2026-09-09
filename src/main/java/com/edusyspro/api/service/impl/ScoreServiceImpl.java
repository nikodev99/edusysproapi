package com.edusyspro.api.service.impl;

import com.edusyspro.api.dto.ScoreDTO;
import com.edusyspro.api.dto.custom.*;
import com.edusyspro.api.exception.sql.AlreadyExistException;
import com.edusyspro.api.exception.sql.InsertException;
import com.edusyspro.api.model.Score;
import com.edusyspro.api.repository.ScoreRepository;
import com.edusyspro.api.service.interfaces.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;


import java.util.*;
import java.util.stream.Collectors;

@Service
public class ScoreServiceImpl implements ScoreService {

    private static final int K = 4;               // shrinkage confidence constant
    private static final int MIN_ASSIGNMENTS = 2;  // eligibility floor
    private static final int TOP_N = 3;
    private static final int MAX_RADAR_AXES = 6;
    private final ScoreRepository scoreRepository;

    @Autowired
    public ScoreServiceImpl(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    @Override
    public boolean saveAllScores(List<ScoreDTO> scores, long assignmentId) {
        boolean exists = scoreExists(assignmentId);
        boolean saved = false;
        try {
            if(!exists) {
                List<Score> scoreEntities = scores.stream()
                                .map(ScoreDTO::toEntity)
                                        .toList();
                scoreRepository.saveAll(scoreEntities);
                saved = true;
            }
        }catch (Exception e) {
            throw new InsertException("Error wile inserting data: " + e.getMessage());
        }

        return saved;
    }

    @Override
    public ScoreDTO saveScore(ScoreDTO score, long assignmentId) {
        if (scoreExists(assignmentId, score.getStudent().getId()))
            throw new AlreadyExistException("La note existe déjà pour cet apprenant");

        var savedScore = scoreRepository.save(score.toEntity());
        return ScoreDTO.toDto(savedScore);
    }

    @Override
    public boolean updateAllScores(List<ScoreDTO> scores, long assignmentId) {
        List<Long> ids = scores.stream().map(ScoreDTO::getId).toList();
        Map<Long, ScoreDTO> scoreMap = scores.stream().collect(Collectors.toMap(ScoreDTO::getId, s -> s));
        List<Score> entities = scoreRepository.findAllByIdInAndAssignmentId(ids, assignmentId);
        if (entities.size() != ids.size()) return false;

        entities.forEach(s -> {
            ScoreDTO score = scoreMap.get(s.getId());
            s.setObtainedMark(score.getObtainedMark());
            s.setIsPresent(score.getIsPresent());
        });

        scoreRepository.saveAll(entities);
        return entities.size() == scores.size();
    }

    @Override
    public ScoreDTO updateScore(ScoreDTO score, long assignmentId) {
        var updateRow = scoreRepository.updateScoresByAssignmentId(
                score.getObtainedMark(),
                score.getIsPresent(),
                score.getId(),
                assignmentId
        );
        if (!(updateRow > 0))
            throw new IllegalArgumentException("Mis à jour impossible");

        return score;
    }

    @Override
    public Page<ScoreDTO> getLastScoresByStudent(String studentId, Pageable pageable) {
        return scoreRepository.findLastFiveScoresByStudent(UUID.fromString(studentId), pageable)
                .map(ScoreEssential::toDTO);
    }

    @Override
    public List<RadarAxis> getStudentCourseStats(String studentId, UUID academicYear) {
        return scoreRepository.findStudentCourseStats(UUID.fromString(studentId), academicYear).stream()
                .limit(MAX_RADAR_AXES)
                .map(s -> s.toRadarAxis(MIN_ASSIGNMENTS))
                .toList();
    }

    @Override
    public Page<ScoreDTO> getScoresByStudentPerAcademicYear(String studentId, String academicYearId, Pageable pageable) {
        return scoreRepository.findAllByStudentIdAndAcademicYear(
                UUID.fromString(academicYearId),
                UUID.fromString(studentId),
                pageable
        ).map(ScoreEssential::toDTO);
    }

    @Override
    public List<ScoreDTO> getScoresByStudentPerSubjectPerAcademicYear(String studentId, String academicYearId, int subjectId) {
        return scoreRepository.findAllByStudentIdAcademicYearAndSubjectId(
                UUID.fromString(studentId),
                subjectId,
                UUID.fromString(academicYearId)
        ).stream()
        .map(ScoreEssential::toDTO)
        .collect(Collectors.toList());
    }

    @Override
    public List<ScoreDTO> getAllTeacherMarks(Long teacherId, List<Long> teacherIds) {
        if (teacherId != null) {
            return scoreRepository.findAllTeacherMarks(teacherId).stream()
                    .map(ScoreBasicValue::toDTO)
                    .toList();
        }
        return scoreRepository.findAllTeacherMarks(teacherIds).stream()
                .map(ScoreBasicValue::toDTO)
                .toList();
    }

    @Override
    public Page<ScoreDTO> getAllAssignmentScores(long assignmentId, Pageable pageable) {
        return scoreRepository.findScoresByAssignment(assignmentId, pageable)
                .map(ScoreBasicValue::toDTO);
    }

    @Override
    public List<ScoreDTO> getAssignmentScores(long assignmentId) {
        return scoreRepository.findScoresByAssignment(assignmentId).stream()
                .map(ScoreBasicValue::toDTO)
                .toList();
    }

    @Override
    public List<ScoreDTO> getAllAssignmentScores(List<Long> assignmentId) {
        return scoreRepository.findScoresByAssignmentIds(assignmentId).stream()
                .map(ScoreBasic::toDTO)
                .toList();
    }

    @Override
    public List<ScoreDTO> getAssignmentScoresByStudent(List<Long> assignmentId, String studentId) {
        return scoreRepository.findStudentScoresByAssignmentIds(assignmentId, UUID.fromString(studentId)).stream()
                .map(ScoreBasic::toDTO)
                .toList();
    }

    @Override
    public List<GradeRanking> getBestStudentBySubjectScore(long teacherId, int subjectId, String academicYear) {
        List<StudentStats> stats = scoreRepository.findBestStudentByTeacherScores(teacherId, subjectId, UUID.fromString(academicYear));
        return getGradeRanking(stats);
    }

    @Override
    public List<GradeRanking> getBestStudentByScore(long teacherId, String academicYear) {
        List<StudentStats> stats =  scoreRepository.findBestStudentByTeacherScores(teacherId, UUID.fromString(academicYear));
        return getGradeRanking(stats);
    }

    @Override
    public List<ClasseRanking> getClasseBestStudents(int classeId, String academicYearId) {
        List<StudentStats> stats = scoreRepository.findBestStudentByClasseScores(classeId, UUID.fromString(academicYearId));
        return getClasseRanking(stats);
    }

    @Override
    public List<ClasseRanking> getClasseBestStudentsByCourse(int classeId, String academicYearId, int courseId) {
        List<StudentStats> stats = scoreRepository.findBestStudentByClasseBySubjectScores(classeId, UUID.fromString(academicYearId), courseId);
        return getClasseRanking(stats);
    }

    @Override
    public List<GradeRanking> getStudentCourseStats(int courseId, String academicYearId) {
        List<StudentStats> stats = scoreRepository.findStudentCourseStats(courseId, UUID.fromString(academicYearId));
        return  getGradeRanking(stats);
    }

    @Override
    public List<ScoreAvg> getClasseAvgScore(int classeId, String academicYearId) {
        return scoreRepository.findClasseScoresAvgByClasse(classeId, UUID.fromString(academicYearId))
                .stream()
                .map(score -> new ScoreAvg((String) score[0], (long) score[1]))
                .toList();
    }

    @Override
    public ScoreDTO getStudentAssignmentScore(long assignmentId, String studentId) {
        return scoreRepository.findStudentScoreOfOneAssignment(assignmentId, UUID.fromString(studentId))
                .map(ScoreBasicValue::toDTO)
                .orElseThrow();
    }

    @Override
    public Long countAssignmentSCores(long assignmentId) {
        return scoreRepository.countAssignmentInScores(assignmentId).orElse(0L);
    }

    private List<GradeRanking> getGradeRanking(List<StudentStats> stats) {
        if (stats.isEmpty()) {
            return Collections.emptyList();
        }

        Map<Integer, List<StudentStats>> byGrade = stats.stream()
                .collect(Collectors.groupingBy(StudentStats::gradeId));

        List<GradeRanking> result = new ArrayList<>();

        for (List<StudentStats> gradeStats : byGrade.values()) {
            CohortStats stat = getStats(gradeStats);

            result.add(new GradeRanking(
                    stat.any().gradeId(),
                    stat.any().classeName(),
                    stat.any().section(),
                    stat.any().subSection(),
                    stat.bests(),
                    stat.poors())
            );
        }

        result.sort(Comparator.comparingInt(GradeRanking::gradeId).thenComparing(GradeRanking::section));

        return result;
    }

    private List<ClasseRanking> getClasseRanking(List<StudentStats> stats) {
        if (stats.isEmpty()) {
            return Collections.emptyList();
        }

        Map<Integer, List<StudentStats>> byClasse = stats.stream()
                .collect(Collectors.groupingBy(StudentStats::classeId));

        List<ClasseRanking> result = new ArrayList<>();
        for (List<StudentStats> classeStats : byClasse.values()) {
            CohortStats stat = getStats(classeStats);

            StudentStats any = stat.any();
            result.add(new ClasseRanking(any.classeId(), any.classeName(), any.section(), stat.bests(), stat.poors()));
        }
        result.sort(Comparator.comparingInt(ClasseRanking::classeId).thenComparing(ClasseRanking::section));

        return result;
    }

    private CohortStats getStats(List<StudentStats> stats) {
        double avg = stats.stream()
                .mapToDouble(StudentStats::weightedAverage)
                .average()
                .orElse(0D);

        List<StudentStats> eligible = stats.stream()
                .filter(s -> s.assignmentCount() >= MIN_ASSIGNMENTS)
                .toList();

        Comparator<StudentStats> byScore = Comparator.comparingDouble(s -> getShrinkageConfidence(s, avg));

        List<ScoreDTO> bestEligibleStudents = eligible.stream()
                .sorted(byScore.reversed())
                .limit(TOP_N)
                .map(s -> getStudentScore(s, avg))
                .toList();

        List<ScoreDTO> poorEligibleStudents = eligible.stream()
                .sorted(byScore)
                .limit(TOP_N)
                .map(s -> getStudentScore(s, avg))
                .toList();

        StudentStats any = stats.get(0);

        return new CohortStats(any, bestEligibleStudents, poorEligibleStudents);
    }

    private ScoreDTO getStudentScore(StudentStats s, double avg) {
        ScoreDTO scores = s.toScoreDTO();
        scores.setShrinkMark(getShrinkageConfidence(s, avg));
        return scores;
    }

    private double getShrinkageConfidence(StudentStats s, double avg) {
        long n = s.assignmentCount();
        return ((double) n/(n + K)) * s.weightedAverage() + ((double) K/(n + K)) * avg;
    }

    private boolean scoreExists(long assignmentId) {
        return countAssignmentSCores(assignmentId) > 0L;
    }

    private boolean scoreExists(long assignmentId, UUID studentId) {
        return scoreRepository.countStudentScores(assignmentId, studentId).isPresent();
    }

    public record CohortStats(
            StudentStats any,
            List<ScoreDTO> bests,
            List<ScoreDTO> poors
    ) {}

}
