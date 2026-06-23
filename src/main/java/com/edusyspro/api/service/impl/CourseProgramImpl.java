package com.edusyspro.api.service.impl;

import com.edusyspro.api.auth.response.MessageResponse;
import com.edusyspro.api.dto.*;
import com.edusyspro.api.dto.custom.*;
import com.edusyspro.api.model.CourseProgram;
import com.edusyspro.api.model.CourseProgramTopic;
import com.edusyspro.api.model.enums.ProgramStatus;
import com.edusyspro.api.repository.CourseProgramRepository;
import com.edusyspro.api.repository.CourseProgramTimingRepository;
import com.edusyspro.api.repository.CourseProgramTopicRepository;
import com.edusyspro.api.service.interfaces.CourseProgramService;
import com.edusyspro.api.utils.Datetime;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseProgramImpl implements CourseProgramService {

    private final CourseProgramRepository courseProgramRepository;
    private final CourseProgramTopicRepository courseProgramTopicRepository;
    private final CourseProgramTimingRepository courseProgramTimingRepository;

    public CourseProgramImpl(
            CourseProgramRepository courseProgramRepository,
            CourseProgramTopicRepository courseProgramTopicRepository,
            CourseProgramTimingRepository courseProgramTimingRepository
    ) {
        this.courseProgramRepository = courseProgramRepository;
        this.courseProgramTopicRepository = courseProgramTopicRepository;
        this.courseProgramTimingRepository = courseProgramTimingRepository;
    }

    @Transactional
    public Long saveCourseProgram(CourseProgramRequest program) {
        CourseProgram entity = program.toEntity();
        CourseProgram saved = courseProgramRepository.save(entity);
        return saved.getId();
    }

    @Override
    public Long saveCourseProgramTopic(CourseProgramTopicDTO topic) {
        CourseProgramTopic entity = topic.toEntity();
        CourseProgramTopic saved = courseProgramTopicRepository.save(entity);
        return saved.getId();
    }

    @Override
    public CourseProgramResponse findAllProgramsByTeacherCourseAndClasse(String teacherId, CourseAndClasseIds ids, String academicYear) {
        List<CourseProgramEssential> programs = courseProgramRepository.findAllPerTeacherByCourseClasseAndAcademicYear(
                UUID.fromString(teacherId), ids.courseId(), ids.classId(), UUID.fromString(academicYear)
        );

       return mapToProgramResponse(programs);
    }

    @Override
    public CourseProgramResponse findAllProgramsByTeacherAndClasse(String teacherId, CourseAndClasseIds ids, String academicYear) {
        List<CourseProgramEssential> programs = courseProgramRepository.findAllPerTeacherByClasseAndAcademicYear(
                UUID.fromString(teacherId), ids.courseId(), UUID.fromString(academicYear)
        );

        return mapToProgramResponse(programs);
    }

    @Override
    public List<CourseProgramDTO> findAllProgramsByTeacherByCourseAndClasse(String teacherId, CourseAndClasseIds ids) {
        List<CourseProgramBasic> programs = courseProgramRepository.findAllBasicPerTeacherByCourseClasseAndCurrentAcademicYear(
                UUID.fromString(teacherId), ids.courseId(), ids.classId(), PageRequest.of(0, 2)
        );
        return mapToPrograms(programs);
    }

    @Override
    public List<CourseProgramDTO> findAllProgramsByTeacherByClasse(String teacherId, CourseAndClasseIds ids) {
        List<CourseProgramBasic> programs = courseProgramRepository.findAllBasicPerTeacherByClasseAndCurrentAcademicYear(
                UUID.fromString(teacherId), ids.courseId(), PageRequest.of(0, 2)
        );
        return mapToPrograms(programs);
    }

    @Override
    public MessageResponse changeStatus(Long id, ProgramStatus status) {
        int updatedRow = courseProgramTimingRepository.updateStatus(id, status, Datetime.brazzavilleDatetime());
        return updatedRow > 0 ?
                MessageResponse.builder()
                        .message("Status du thème mis à jour avec succès")
                        .timestamp(ZonedDateTime.now().toString())
                        .isError(false)
                .build() :
                MessageResponse.builder()
                        .message("La tentative de mise à jour du status à échouer")
                        .timestamp(ZonedDateTime.now().toString())
                        .isError(true)
                .build();
    }

    @Override
    public MessageResponse completed(Long id) {
        int updatedRow = courseProgramTimingRepository.completeProgram(id, Datetime.brazzavilleDatetime());
        return updatedRow > 0 ?
                MessageResponse.builder()
                        .message("Thème terminé. Félicitations!")
                        .timestamp(ZonedDateTime.now().toString())
                        .isError(false)
                .build() :
                MessageResponse.builder()
                        .message("Le thème n'a pas été mis à jour")
                        .timestamp(ZonedDateTime.now().toString())
                        .isError(true)
                .build();
    }

    @Override
    public MessageResponse deleteProgram(Long id) {
        try {
            return courseProgramRepository.findById(id)
                    .map(program -> {
                        courseProgramRepository.delete(program);
                        return MessageResponse.builder()
                                .message("Thème supprimé avec succès")
                                .timestamp(Instant.now().toString())
                                .isError(false)
                        .build();
                    })
                    .orElseGet(() -> MessageResponse.builder()
                            .message("Program not found")
                            .timestamp(Instant.now().toString())
                            .isError(true)
                            .build()
                    );
        } catch (Exception e) {
            return MessageResponse.builder()
                    .message("Deletion failed")
                    .timestamp(Instant.now().toString())
                    .isError(true)
                    .description("An error occurred while deleting program with id " + id + ": " + e.getMessage())
            .build();
        }
    }

    @Override
    public MessageResponse deleteTopic(Long id) {
        try {
            return courseProgramTopicRepository.findById(id)
                    .map(program -> {
                        courseProgramTopicRepository.delete(program);
                        return MessageResponse.builder()
                                .message("Sous-thème supprimé avec succès")
                                .timestamp(Instant.now().toString())
                                .isError(false)
                                .build();
                    })
                    .orElseGet(() -> MessageResponse.builder()
                            .message("Topic not found")
                            .timestamp(Instant.now().toString())
                            .isError(true)
                            .build()
                    );
        } catch (Exception e) {
            return MessageResponse.builder()
                    .message("Deletion failed")
                    .timestamp(Instant.now().toString())
                    .isError(true)
                    .description("An error occurred while deleting topic with id " + id + ": " + e.getMessage())
                    .build();
        }
    }

    private CourseProgramResponse mapToProgramResponse(List<CourseProgramEssential> programs) {
        if (programs.isEmpty()) {
            return CourseProgramResponse.builder().semesters(Collections.emptyList()).build();
        }

        List<Long> programIds = programs.stream().map(CourseProgramEssential::getProgramId).toList();

        List<CourseProgramTopicEssential> topics = courseProgramTopicRepository.findAllProgramsTopics(programIds);

        Map<Long, List<CourseProgramTopicEssential>> topicsByProgramId = topics.stream()
                .collect(Collectors.groupingBy(CourseProgramTopicEssential::getProgramId));

        CourseProgramEssential ref = programs.get(0);

        List<CourseProgramSemester> semesters = programs.stream()
                .collect(Collectors.groupingBy(r -> r.getSemester().getSemesterId()))
                .values().stream()
                .map(e -> {
                    List<CourseProgramDTO> programList = e.stream()
                            .map(p -> mapToProgram(p, topicsByProgramId.getOrDefault(
                                    p.getProgramId(), Collections.emptyList()
                            )))
                            .sorted(Comparator.comparing(
                                    cp -> cp.getTiming().getStartDate(),
                                    Comparator.nullsLast(Comparator.naturalOrder())
                            ))
                            .toList();

                    return new CourseProgramSemester(e.get(0).getSemester(), programList);
                })
                .sorted(Comparator.comparing(
                        s -> s.semester().getTemplate().getDisplayOrder(),
                        Comparator.nullsLast(Comparator.naturalOrder())
                ))
                .toList();

        return CourseProgramResponse.builder()
                .semesters(semesters)
                .academicYear(mapToAcademicYear(ref))
                .teacher(mapToTeacher(ref))
                .course(mapToCourse(ref))
                .classe(mapToClasse(ref))
                .build();
    }

    private List<CourseProgramDTO> mapToPrograms(List<CourseProgramBasic> rows) {
        Map<Long, CourseProgramDTO> map = new LinkedHashMap<>();

        for (CourseProgramBasic row : rows) {
            CourseProgramDTO dto = map.computeIfAbsent(row.getId(), id -> CourseProgramDTO.builder()
                    .id(id)
                    .name(row.getProgramName())
                    .topic(new ArrayList<>())
                    .timing(CourseProgramTimingDTO.builder()
                            .status(row.getProgramStatus())
                            .build())
                    .classeName(row.getClasse())
                    .build()
            );

            CourseProgramTopicDTO topics = CourseProgramTopicDTO.builder()
                    .title(row.getTopicTitle())
                    .build();

            dto.getTopic().add(topics);
        }

        return new ArrayList<>(map.values());
    }

    private CourseProgramDTO mapToProgram(CourseProgramEssential programRow, List<CourseProgramTopicEssential> topicRows) {
        List<CourseProgramTopicDTO> topics = topicRows.stream()
                .filter(r -> r.getTopicId() != null)
                .map(this::mapToTopic)
                .sorted(Comparator.comparing(CourseProgramTopicDTO::getOrder, Comparator.nullsLast(Comparator.naturalOrder())))
                .collect(Collectors.toList());

        return CourseProgramDTO.builder()
                .id(programRow.getProgramId())
                .name(programRow.getProgramName())
                .purpose(programRow.getPurpose())
                .description(programRow.getProgramDescription())
                .timing(mapToProgramTiming(programRow))
                .topic(topics)
                .build();
    }

    private CourseProgramTopicDTO mapToTopic(CourseProgramTopicEssential row) {
        return CourseProgramTopicDTO.builder()
                .id(row.getTopicId())
                .title(row.getTopicTitle())
                .description(row.getTopicDescription())
                .order(row.getOrder())
                .timing(mapToTopicTiming(row))
                .build();
    }

    private CourseProgramTimingDTO mapToProgramTiming(CourseProgramEssential row) {
        return CourseProgramTimingDTO.builder()
                .id(row.getProgramTimingId())
                .startDate(row.getProgramStartDate())
                .endDate(row.getProgramEndDate())
                .status(row.getProgramStatus())
                .updatedAt(row.getProgramUpdateDate())
                .completedAt(row.getProgramCompletedDate())
                .build();
    }

    private CourseProgramTimingDTO mapToTopicTiming(CourseProgramTopicEssential row) {
        return CourseProgramTimingDTO.builder()
                .id(row.getTopicTimingId())
                .startDate(row.getTopicStartDate())
                .endDate(row.getTopicEndDate())
                .status(row.getTopicStatus())
                .updatedAt(row.getTopicUpdateDate())
                .completedAt(row.getTopicCompletedDate())
                .build();
    }

    private AcademicYearDTO mapToAcademicYear(CourseProgramEssential row) {
        return AcademicYearDTO.builder()
                .id(row.getAcademicYearId())
                .years(row.getAcademicYear())
                .build();
    }

    private TeacherDTO mapToTeacher(CourseProgramEssential row) {
        return TeacherDTO.builder()
                .personalInfo(row.getTeacher())
                .build();
    }

    private CourseDTO mapToCourse(CourseProgramEssential row) {
        return CourseDTO.builder()
                .course(row.getCourseName())
                .abbr(row.getCourseAbbr())
                .build();
    }

    private ClasseDTO mapToClasse(CourseProgramEssential row) {
        return ClasseDTO.builder()
                .name(row.getClasseName())
                .grade(GradeDTO.builder()
                        .section(row.getSection())
                        .build())
                .build();
    }
}
