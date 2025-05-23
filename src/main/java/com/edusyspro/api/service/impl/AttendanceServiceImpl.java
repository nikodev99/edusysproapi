package com.edusyspro.api.service.impl;

import com.edusyspro.api.dto.AttendanceDTO;
import com.edusyspro.api.dto.custom.*;
import com.edusyspro.api.model.enums.AttendanceStatus;
import com.edusyspro.api.repository.AttendanceRepository;
import com.edusyspro.api.repository.StudentRepository;
import com.edusyspro.api.service.interfaces.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;

    @Autowired
    public AttendanceServiceImpl(AttendanceRepository attendanceRepository, StudentRepository studentRepository) {
        this.attendanceRepository = attendanceRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public Page<AttendanceDTO> getLastStudentAttendances(long studentId, Pageable pageable) {
        return attendanceRepository.findAttendanceByStudentId(studentId, pageable)
                .map(AttendanceEssential::populate);
    }

    @Override
    public Page<AttendanceDTO> getStudentAttendancesByAcademicYear(long studentId, String academicYearId, Pageable pageable) {
        return attendanceRepository.findAttendanceByStudentAndAcademicYear(
                studentId,
                UUID.fromString(academicYearId),
                pageable
        ).map(AttendanceEssential::populate);
    }

    @Override
    public List<AttendanceDTO> getStudentAttendances(long studentId, String academicYearId) {
        return attendanceRepository.findAllByStudentAndAcademicYear(studentId, UUID.fromString(academicYearId))
                .stream()
                .map(AttendanceEssential::populate)
                .toList();
    }

    @Override
    public List<AttendanceStatusCount> getStudentAttendanceCount(long studentId, String academicYearId) {
        return attendanceRepository.findAttendanceStatusCountByStudent(studentId, UUID.fromString(academicYearId))
                .stream()
                .map(o -> new AttendanceStatusCount((AttendanceStatus) o[0], (long) o[1]))
                .toList();
    }

    @Override
    public List<AttendanceStatusCount> getClasseAttendanceCount(int classeId, String academicYearId) {
        return attendanceRepository.findAttendanceStatusCountByClasse(classeId, UUID.fromString(academicYearId)).stream()
                .map(o -> new AttendanceStatusCount((AttendanceStatus) o[0], (long) o[1]))
                .toList();
    }

    @Override
    public List<AttendanceStatusCount> getSchoolAttendanceCount(String schoolId, String academicYearId) {
        return attendanceRepository.findAttendanceStatusCountBySchool(UUID.fromString(schoolId), UUID.fromString(academicYearId))
                .stream()
                .map(o -> new AttendanceStatusCount((AttendanceStatus) o[0], (long) o[1]))
                .toList();
    }

    @Override
    public List<IndividualAttendanceSummary> getBestStudentAtAttendance(int classeId, String academicYearId) {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Object[]> studentRanked = attendanceRepository.findAttendanceStatusRanking(
                classeId, UUID.fromString(academicYearId), AttendanceStatus.PRESENT, pageable
        );
        return getSummaryStatus(studentRanked);
    }

    @Override
    public List<IndividualAttendanceSummary> getWorstStudentAtAttendance(int classeId, String academicYearId) {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Object[]> studentRanked = attendanceRepository.findAttendanceStatusRanking(
                classeId, UUID.fromString(academicYearId), AttendanceStatus.ABSENT, pageable
        );
        return getSummaryStatus(studentRanked);
    }

    @Override
    public Page<IndividualAttendanceSummary> getStudentAttendanceStatus(int classeId, String academicYearId, Pageable pageable) {
        Page<Long> individualIds = attendanceRepository.findClasseAttendanceStatus(classeId, UUID.fromString(academicYearId), pageable);
        List<Object[]> statuses = attendanceRepository.findClasseAttendanceStatus(
                individualIds.getContent(),
                UUID.fromString(academicYearId)
        );

        List<IndividualAttendanceSummary> summaries = getSummaryStatus(statuses);
        return new PageImpl<>(summaries, individualIds.getPageable(), individualIds.getTotalElements());
    }

    @Override
    public List<IndividualAttendanceSummary> getStudentAttendanceStatus(int classeId, String academicYearId, String name) {
        List<Long> individualIds = attendanceRepository.findClasseAttendanceStatus(classeId, UUID.fromString(academicYearId), "%" +  name + "%");
        List<Object[]> statuses = attendanceRepository.findClasseAttendanceStatus(individualIds, UUID.fromString(academicYearId));
        return getSummaryStatus(statuses);
    }

    @Override
    public List<AttendanceStatusStat> getClasseAttendanceStats(int classeId, String academicYearId) {
        List<LocalDate> dates = attendanceRepository.findRecentAttendanceDate(classeId, UUID.fromString(academicYearId), PageRequest.of(0, 10));
        Collections.reverse(dates);
        System.out.println("DATE_GOT: " + Arrays.deepToString(dates.toArray(new LocalDate[0])));
        List<Object[]> stats = attendanceRepository.findRecentClasseAttendanceStatsPerStatus(classeId, dates, UUID.fromString(academicYearId));

        Map<LocalDate, Map<AttendanceStatus, Long>> dateStatusCount = stats.stream()
                .collect(Collectors.groupingBy(row -> (LocalDate) row[1], Collectors.toMap(
                        row -> (AttendanceStatus) row[0],
                        row -> (Long) row[2],
                        (count1, count2) -> count1,
                        HashMap::new
                )));

        return dates.stream()
                .map(date -> {
                    String formattedDate = date.format(DateTimeFormatter.ofPattern("dd/MM"));
                    Map<AttendanceStatus, Long> statusCounts = dateStatusCount.getOrDefault(date, new HashMap<>());
                    byte present = statusCounts.getOrDefault(AttendanceStatus.PRESENT, 0L).byteValue();
                    byte absent = statusCounts.getOrDefault(AttendanceStatus.ABSENT, 0L).byteValue();
                    byte late = statusCounts.getOrDefault(AttendanceStatus.LATE, 0L).byteValue();
                    byte excused = statusCounts.getOrDefault(AttendanceStatus.EXCUSED, 0L).byteValue();

                    return new AttendanceStatusStat(formattedDate, present, absent, late, excused);
                })
                .toList();
    }

    private List<IndividualAttendanceSummary> getSummaryStatus(Page<Object[]> objectsCount) {
        return getIndividualAttendanceSummaries(objectsCount.stream());
    }

    private List<IndividualAttendanceSummary> getSummaryStatus(List<Object[]> objectsCount) {
        return getIndividualAttendanceSummaries(objectsCount.stream());
    }

    private List<IndividualAttendanceSummary> getIndividualAttendanceSummaries(Stream<Object[]> stream) {
        return stream.collect(Collectors.groupingBy(
                        row -> {
                            IndividualBasic individualBasic = (IndividualBasic) row[0];
                            return individualBasic.toEntity();
                        },
                        LinkedHashMap::new,
                        Collectors.mapping(row -> new IndividualAttendanceCount((AttendanceStatus) row[1], (Long) row[2]), Collectors.toList())
                ))
                .entrySet().stream()
                .map(entry -> new IndividualAttendanceSummary(entry.getKey(), entry.getValue()))
                .toList();
    }
}
