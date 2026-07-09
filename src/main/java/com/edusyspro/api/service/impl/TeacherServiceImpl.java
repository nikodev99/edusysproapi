package com.edusyspro.api.service.impl;

import com.edusyspro.api.dto.*;
import com.edusyspro.api.dto.custom.*;
import com.edusyspro.api.exception.sql.AlreadyExistException;
import com.edusyspro.api.exception.sql.NotFountException;
import com.edusyspro.api.model.Individual;
import com.edusyspro.api.model.Teacher;
import com.edusyspro.api.model.enums.IndividualType;
import com.edusyspro.api.model.enums.OperationType;
import com.edusyspro.api.model.enums.Section;
import com.edusyspro.api.repository.ClasseRepository;
import com.edusyspro.api.repository.CourseRepository;
import com.edusyspro.api.repository.TeacherRepository;
import com.edusyspro.api.repository.context.UpdateContext;
import com.edusyspro.api.service.CustomMethod;
import com.edusyspro.api.service.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TeacherServiceImpl implements TeacherServiceInterface {

    private final TeacherRepository teacherRepository;
    private final ScheduleService scheduleService;
    private final AcademicYearService academicYearService;
    private final UpdateContext updateContext;
    private final CourseProgramService courseProgramService;
    private final IndividualReferenceService individualReferenceService;
    private final ReprimandService reprimandService;
    private final TeachingReportService teachingReportService;
    private final ClasseRepository classeRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public TeacherServiceImpl(
            TeacherRepository teacherRepository,
            ScheduleService scheduleService,
            AcademicYearService academicYearService,
            UpdateContext updateContext,
            CourseProgramService courseProgramService,
            IndividualReferenceService individualReferenceService,
            ReprimandService reprimandService,
            TeachingReportService teachingReportService,
            ClasseRepository classeRepository,
            CourseRepository courseRepository
    ) {
        this.teacherRepository = teacherRepository;
        this.scheduleService = scheduleService;
        this.academicYearService = academicYearService;
        this.updateContext = updateContext;
        this.courseProgramService = courseProgramService;
        this.individualReferenceService = individualReferenceService;
        this.reprimandService = reprimandService;
        this.teachingReportService = teachingReportService;
        this.classeRepository = classeRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public TeacherDTO save(TeacherDTO entity) {
        if (teacherEmailExists(entity)) {
            throw new AlreadyExistException("L'adresse e-mail est déjà utilisée. Veuillez en fournir une autre.");
        }else {
            Teacher teacherEntity = TeacherDTO.toEntity(entity);
            Individual teacherPersonalInfo = entity.getPersonalInfo();
            if (teacherPersonalInfo != null) {
                String reference = individualReferenceService.generateReference(IndividualType.TEACHER);
                teacherPersonalInfo.setReference(reference);
            }

            Teacher insertedTeacher = teacherRepository.save(teacherEntity);
            if (insertedTeacher.getId() != null) {
                entity = TeacherDTO.fromEntity(insertedTeacher);
            }
        }
        return entity;
    }

    @Override
    public List<TeacherDTO> saveAll(List<TeacherDTO> entities) {
        return List.of();
    }

    @Override
    public List<TeacherDTO> fetchAll() {
        return List.of();
    }

    @Override
    public List<TeacherDTO> fetchAll(String schoolId) {
        return List.of();
    }

    @Override
    public Page<TeacherDTO> fetchAll(String schoolId, Pageable pageable) {
        Page<TeacherEssential> teacherEssentials = teacherRepository.findAllBySchoolId(UUID.fromString(schoolId), pageable);
        return teacherEssentials.map(t -> getTeacherDTO(t, UUID.fromString(schoolId)));
    }

    @Override
    public List<TeacherDTO> fetchAll(Object... args) {
        UUID schoolId = UUID.fromString(args[0].toString());
        String lastname = "%" + args[1].toString() + "%";
        return teacherRepository.findAllBySchoolId(schoolId, lastname).stream()
                .map(TeacherEssential::toTeacher)
                .toList();
    }

    @Override
    public Page<TeacherDTO> fetchAll(Pageable pageable, Object... args) {
        UUID schoolId = UUID.fromString(args[0].toString());
        UUID teacherId = UUID.fromString(args[1].toString());
        return teacherRepository.findAllBySchoolId(schoolId, teacherId, pageable).map(t -> getTeacherDTO(t, schoolId));
    }

    @Override
    public List<TeacherDTO> fetchAllById(UUID id) {
        return List.of();
    }

    @Override
    public List<TeacherDTO> fetchAllById(Object... arg) {
        return List.of();
    }

    @Override
    public Page<TeacherDTO> fetchAllByOtherEntityId(String otherEntityId, Pageable pageable) {
        return null;
    }

    @Override
    public List<TeacherDTO> fetchAllByOtherEntityId(String otherEntityId) {
        return teacherRepository.findAllClasseTeachers(Integer.parseInt(otherEntityId)).stream()
                .map(TeacherClasseCourse::toTeacher)
                .toList();
    }

    @Override
    public List<TeacherDTO> fetchAllByOtherEntityId(Object... arg) {
        int classeId = Integer.parseInt(arg[0].toString());
        Section section = Section.valueOf(arg[1].toString());
        return teacherRepository.findAllTeacherBasicValue(classeId, section).stream()
                .map(TeacherBasic::toDTO)
                .toList();
    }

    @Override
    public TeacherDTO fetchOneById(UUID id) {
        return null;
    }

    @Override
    public TeacherDTO fetchOneById(UUID id, String schoolId) {
        TeacherDTO dto = teacherRepository.findTeacherById(id, UUID.fromString(schoolId))
                        .orElseThrow(() -> new NotFountException("Teacher was not found"))
                        .toTeacher();
        List<ClassBasicValue> classes = teacherRepository.findTeacherClasses(dto.getId(), UUID.fromString(schoolId));
        List<CourseEssential> courses = teacherRepository.findTeacherEssentialCourses(dto.getId(), UUID.fromString(schoolId));

        List<List<CourseProgramDTO>> programs = new ArrayList<>();

        if (!courses.isEmpty()) {
            UUID academicYearId = academicYearService.getCurrentAcademicYear(schoolId).getId();
            List<ScheduleDTO> teacherSchedules = scheduleService.getTeacherSchedule(academicYearId.toString(), dto.getId().toString());
            List<ScheduleDTO> distinctSchedule = teacherSchedules.stream()
                    .filter(s -> s.getClasse() != null)
                    .filter(s -> s.getCourse() != null)
                    .collect(Collectors.toMap(
                            s -> new AbstractMap.SimpleEntry<>(
                                    s.getClasse().getId(), s.getCourse().getId()
                            ),
                            Function.identity(),
                            (first, second) -> first,
                            LinkedHashMap::new
                    ))
                    .values()
                    .stream()
                    .toList();

            for (ScheduleDTO s : distinctSchedule) {
                List<CourseProgramDTO> p = courseProgramService.findAllProgramsByTeacherByCourseAndClasse(
                        dto.getId().toString(),
                        new CourseAndClasseIds(s.getCourse().getId(), s.getClasse().getId())
                );
                programs.add(p);
            }
        }else {
            for (ClassBasicValue c : classes) {
                List<CourseProgramDTO> p = courseProgramService.findAllProgramsByTeacherByClasse(
                        dto.getId().toString(),
                        new CourseAndClasseIds(null, c.id())
                );
                programs.add(p);
            }
        }

        dto.setAClasses(classes.stream().map(ClassBasicValue::toClasse).toList());
        dto.setCourses(courses.stream().map(CourseEssential::toCourse).toList());
        dto.setCourseProgram(programs);
        return dto;
    }

    @Override
    public TeacherDTO fetchOneById(UUID id, Object... args) {
        return null;
    }

    @Override
    public TeacherDTO fetchOneByCustomColumn(String columnValue, String schoolId) {
        return null;
    }

    @Override
    public TeacherDTO fetchOneByCustomColumn(String columnValue) {
        return null;
    }

    @Override
    public TeacherDTO fetchOneByCustomColumn(String columnValue, Object... args) {
        UUID schoolId = UUID.fromString(args[0].toString());
        Boolean hasCourse = (Boolean) args[1];
        TeacherDTO teacher = TeacherDTO.builder().build();
        if (hasCourse) {
            List<CourseBasicValue> courses = teacherRepository.findTeacherCourses(UUID.fromString(columnValue), schoolId);
            teacher.setCourses(courses.stream().map(CourseBasicValue::toCourse).toList());
        }else {
            List<ClassBasicValue> classes = teacherRepository.findTeacherClasses(UUID.fromString(columnValue), schoolId);
            teacher.setAClasses(classes.stream().map(ClassBasicValue::toClasse).toList());
        }
        return teacher;
    }

    @Override
    public TeacherDTO fetchOneById(Object... arg) {
        Long teacherId = (Long) arg[0];
        Integer classeId = (Integer) arg[1];
        return teacherRepository.findTeacherBasicValue(teacherId, classeId)
                .orElseThrow(() -> new NotFountException("Teacher not found"))
                .toDTO();
    }

    @Override
    public int update(TeacherDTO entity) {
        return 0;
    }

    @Override
    public Map<String, Boolean> update(TeacherDTO entity, UUID id) {
        return Map.of();
    }

    @Override
    public int patch(UUID id, UpdateField field) {
        return updateContext.updateTeacherField(field.field(), field.value(), id);
    }

    @Override
    public int delete(TeacherDTO entity) {
        return 0;
    }

    @Override
    public Map<String, Long> count(UUID id) {
        return Map.of();
    }

    @Override
    public Map<String, Long> count(String schoolId) {
       return Map.of();
    }

    @Override
    public Map<String, Long> count(Object... args) {
        UUID id = UUID.fromString(args[0].toString());
        UUID schoolId = UUID.fromString(args[1].toString());
        return Map.of("count", teacherRepository.countTeacherStudents(id, schoolId));
    }

    @Override
    public List<Map<String, Object>> countStudentsByClasse(UUID teacherId, UUID schoolId) {
        List<Object[]> counts = teacherRepository.countAllTeacherStudentsByClasses(teacherId, schoolId);
        return counts.stream()
                .map(row -> Map.of(
                        "classe", row[0],
                        "count", row[1]
                ))
                .toList();
    }

    @Override
    public GenderCount countAllTeachers(String schoolId) {
        List<Object[]> countTeachers = teacherRepository.countAllTeachers(UUID.fromString(schoolId));
        return CustomMethod.genderCountInClasse(countTeachers);
    }

    @Override
    public Map<String, Object> fetchTeacherWidgets(String teacherId, String academicYear) {
        Optional<IndividualBasic> personalInfo = teacherRepository.findTeacherPersonalInfo(UUID.fromString(teacherId));
        if (personalInfo.isPresent()) {
            UUID id = UUID.fromString(teacherId);
            IndividualBasic p = personalInfo.get();
            Long teacherStudentNumber = teacherRepository.countTeacherStudents(id, UUID.fromString(academicYear));
            Long teacherReportNumber = teachingReportService.getReportCountByTeacher(teacherId, academicYear);
            Long teacherReprimandedStudent = reprimandService.countStudentReprimandedByTeacher(p.id(), academicYear);
            return Map.of(
                    "students", teacherStudentNumber,
                    "reports", teacherReportNumber,
                    "reprimands", teacherReprimandedStudent
            );
        }
        return Map.of();
    }

    @Override
    @Transactional
    public int updateTeacherClasses(String teacherId, String schoolId, OperationType operation, List<Integer> classeIds) throws AccessDeniedException {
        throwIfTeacherNotFound(UUID.fromString(teacherId));

        List<Integer> validIds = classeRepository.findValidIdsForSchool(classeIds, UUID.fromString(schoolId));
        if (validIds.size() != classeIds.size()) {
            throw new AccessDeniedException("You don't have access to these classes");
        }

        int updated = 0;
        if(operation == OperationType.ADD) {
            Set<Integer> alreadyAssigned = new HashSet<>(teacherRepository.findAssignedClassIds(UUID.fromString(teacherId)));
            for (Integer classId: classeIds) {
                if (!alreadyAssigned.contains(classId)) {
                    updated = teacherRepository.linkClass(UUID.fromString(teacherId), classId);
                }
            }
        }else {
            updated = teacherRepository.unlinkClasses(UUID.fromString(teacherId), classeIds);
        }
        return updated;
    }

    @Override
    public int updateTeacherCourses(String teacherId, String schoolId, OperationType operation, List<Integer> coursesIds) throws AccessDeniedException {
        throwIfTeacherNotFound(UUID.fromString(teacherId));

        List<Integer> validIds = courseRepository.findValidIdsForSchool(coursesIds, UUID.fromString(schoolId));
        if (validIds.size() != coursesIds.size()) {
            throw new AccessDeniedException("You don't have access to these classes");
        }

        int updated = 0;
        if (operation == OperationType.ADD) {
            Set<Integer> alreadyAssigned = new HashSet<>(teacherRepository.findAssignedCourseIds(UUID.fromString(teacherId)));
            for (Integer courseId: coursesIds) {
                if (!alreadyAssigned.contains(courseId)) {
                    updated = teacherRepository.linkCourse(UUID.fromString(teacherId), courseId);
                }
            }
        }else {
            updated = teacherRepository.unlinkCourses(UUID.fromString(teacherId), coursesIds);
        }
        return updated;   
    }

    private void throwIfTeacherNotFound(UUID teacherId) {
        if (!teacherRepository.existsById(teacherId)) {
            throw new NotFountException("Teacher not found");
        }
    }

    private TeacherDTO getTeacherDTO(TeacherEssential t, UUID schoolId) {
        TeacherDTO teacherDTO = t.toTeacher();
        List<CourseBasicValue> courses = teacherRepository.findTeacherCourses(t.id(), schoolId);
        List<ClassBasicValue> classes = teacherRepository.findTeacherClasses(t.id(), schoolId);
        teacherDTO.setCourses(courses.stream().map(CourseBasicValue::toCourse).toList());
        teacherDTO.setAClasses(classes.stream().map(ClassBasicValue::toClasse).toList());
        return teacherDTO;
    }

    private boolean teacherEmailExists(TeacherDTO teacherDTO) {
        return teacherRepository.existsByPersonalInfoEmailIdAndSchoolId(teacherDTO.getPersonalInfo().getEmailId(), teacherDTO.getSchool().getId());
    }
}
