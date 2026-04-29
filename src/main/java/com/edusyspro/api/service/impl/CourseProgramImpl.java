package com.edusyspro.api.service.impl;

import com.edusyspro.api.dto.*;
import com.edusyspro.api.dto.custom.CourseAndClasseIds;
import com.edusyspro.api.dto.custom.CourseProgramEssential;
import com.edusyspro.api.dto.custom.CourseProgramResponse;
import com.edusyspro.api.dto.custom.CourseProgramSemester;
import com.edusyspro.api.repository.CourseProgramRepository;
import com.edusyspro.api.service.interfaces.CourseProgramService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CourseProgramImpl implements CourseProgramService {

    private final CourseProgramRepository courseProgramRepository;

    public CourseProgramImpl(CourseProgramRepository courseProgramRepository) {
        this.courseProgramRepository = courseProgramRepository;
    }

    @Override
    public CourseProgramResponse findAllProgramsByTeacherCourseAndClasse(String teacherId, CourseAndClasseIds ids, String academicYear) {
        List<CourseProgramEssential> results = courseProgramRepository.findAllPerTeacherByCourseClasseAndAcademicYear(
                UUID.fromString(teacherId), ids.courseId(), ids.classId(), UUID.fromString(academicYear)
        );

        if (results.isEmpty()) {
            return CourseProgramResponse.builder().semesters(Collections.emptyList()).build();
        }

        CourseProgramEssential ref = results.get(0);

        List<CourseProgramSemester> semesters = results.stream()
                .collect(Collectors.groupingBy(r -> r.getSemester().getSemesterId()))
                .values().stream()
                .map(entry -> {
                    List<CourseProgramDTO> programs = entry.stream()
                            .collect(Collectors.groupingBy(CourseProgramEssential::getProgramId))
                            .values().stream()
                            .map(this::mapToProgram)
                            .sorted(Comparator.comparing(cp -> cp.getTiming().getStartDate(), Comparator.nullsLast(Comparator.naturalOrder())))
                            .toList();

                    return new CourseProgramSemester(entry.get(0).getSemester(), programs);
                })
                .sorted(Comparator.comparing(s -> s.semester().getTemplate().getDisplayOrder(), Comparator.nullsLast(Comparator.naturalOrder())))
                .toList();

        return CourseProgramResponse.builder()
                .semesters(semesters)
                .academicYear(mapToAcademicYear(ref))
                .teacher(mapToTeacher(ref))
                .course(mapToCourse(ref))
                .classe(mapToClasse(ref))
                .build();
    }

    @Override
    public List<CourseProgramDTO> findAllProgramsByTeacherAndClasse(String teacherId, CourseAndClasseIds ids, String academicYear) {
        return List.of();
    }

    private CourseProgramDTO mapToProgram(List<CourseProgramEssential> rows) {
        CourseProgramEssential first = rows.get(0);

        List<CourseProgramTopicDTO> topics = rows.stream()
                .filter(r -> r.getTopicId() != null)
                .map(this::mapToTopic)
                .sorted(Comparator.comparing(CourseProgramTopicDTO::getOrder, Comparator.nullsLast(Comparator.naturalOrder())))
                .collect(Collectors.toList());

        return CourseProgramDTO.builder()
                .id(first.getProgramId())
                .name(first.getProgramName())
                .purpose(first.getPurpose())
                .description(first.getProgramDescription())
                .timing(mapToProgramTiming(first))
                .topic(topics)
                .build();
    }

    private CourseProgramTopicDTO mapToTopic(CourseProgramEssential row) {
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

    private CourseProgramTimingDTO mapToTopicTiming(CourseProgramEssential row) {
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
