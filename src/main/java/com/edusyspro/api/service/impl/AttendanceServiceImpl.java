package com.edusyspro.api.service.impl;

import com.edusyspro.api.dto.AttendanceDTO;
import com.edusyspro.api.dto.PlanningDTO;
import com.edusyspro.api.dto.custom.*;
import com.edusyspro.api.model.AcademicYear;
import com.edusyspro.api.model.Attendance;
import com.edusyspro.api.model.Individual;
import com.edusyspro.api.model.enums.AttendanceStatus;
import com.edusyspro.api.model.enums.Gender;
import com.edusyspro.api.model.enums.Section;
import com.edusyspro.api.repository.AttendanceRepository;
import com.edusyspro.api.service.CustomMethod;
import com.edusyspro.api.service.interfaces.AcademicYearService;
import com.edusyspro.api.service.interfaces.AttendanceService;
import com.edusyspro.api.service.interfaces.PlanningService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final AcademicYearService academicYearService;
    private final PlanningService planningService;

    @Override
    public Boolean saveAllAttendances(List<AttendanceDTO> attendances, int schoolId, LocalDate attendanceDate) {
        boolean exists = exists(schoolId, attendanceDate);
        boolean saved = false;
        if (!exists) {
            List<Attendance> att = attendances.stream()
                    .map(AttendanceDTO::toEntity)
                    .toList();
            attendanceRepository.saveAll(att);
            saved = true;
        }
        return saved;
    }

    @Override
    public Boolean updateAllAttendances(List<AttendanceDTO> attendances) {
        List<Integer> updatedIds = new ArrayList<>();

        attendances.forEach(attendance -> {
            int updateId = attendanceRepository.updateAttendanceStatus(
                    attendance.getStatus(),
                    attendance.getId()
            );
            updatedIds.add(updateId);
        });

        return updatedIds.stream().allMatch(n -> n > 0);
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
    public AttendanceStatusCount getStudentAttendanceCount(long studentId, String academicYearId) {
        Map<AttendanceStatus, Long> statusCount = attendanceRepository.findAttendanceStatusCountByStudent(studentId, UUID.fromString(academicYearId))
                .stream()
                .collect(Collectors.groupingBy(a -> (AttendanceStatus)a[0], Collectors.counting()));

        return new AttendanceStatusCount(statusCount);
    }

    @Override
    public List<AttendanceDTO> getAllClasseAttendancesPerDate(int classeId, String academicYearId, LocalDate date) {
        return attendanceRepository.findAllClasseAttendancePerDate(
                    classeId,
                    UUID.fromString(academicYearId),
                    getAttendanceDate(date)
                )
                .stream()
                .map(AttendanceStudentIndividual::toDto)
                .toList();
    }

    @Override
    public AttendanceStatusCount getClasseAttendanceCount(int classeId, String academicYearId, LocalDate date) {
        List<AttendanceInfo> attendanceInfos;
        if (date != null) {
            attendanceInfos = attendanceRepository.findAttendanceInfoByClasseAndDate(classeId, UUID.fromString(academicYearId), date);
        }else {
            attendanceInfos = attendanceRepository.findAttendanceInfoByClasse(classeId, UUID.fromString(academicYearId));
        }
        return getAttendanceStatusCount(attendanceInfos);
    }

    @Override
    public AttendanceStatusCount getSchoolAttendanceCount(String academicYearId, LocalDate date) {
        List<AttendanceInfo> attendanceInfos;
        if (date != null) {
            attendanceInfos = attendanceRepository.findAttendanceInfoByAcademicYearAndDate(UUID.fromString(academicYearId), date);
        }else {
            attendanceInfos = attendanceRepository.findAttendanceInfoByAcademicYear(UUID.fromString(academicYearId));
        }
        return getAttendanceStatusCount(attendanceInfos);
    }

    @Override
    public List<AttendanceDTO> getAllSchoolAttendances(String schoolId, String academicYearId) {
        return attendanceRepository.findAllSchoolAttendance(UUID.fromString(schoolId), UUID.fromString(academicYearId))
                .stream()
                .map(AttendanceEssential::populate)
                .toList();
    }

    @Override
    public Page<AttendanceDTO> getAllSchoolPerDateAttendances(String schoolId, String academicYearId, LocalDate date, Pageable pageable) {
        return attendanceRepository.findAllSchoolAttendancePerDate(
                    UUID.fromString(schoolId),
                    UUID.fromString(academicYearId),
                    getAttendanceDate(date),
                    pageable
                )
                .map(AttendanceStudentIndividual::toDto);
    }

    @Override
    public Page<AttendanceDTO> getAllSchoolPerDateAttendances(String schoolId, String academicYearId, LocalDate date, String searchable, Pageable pageable) {
        return attendanceRepository.findAllSchoolAttendancePerDate(
                    UUID.fromString(schoolId),
                    UUID.fromString(academicYearId),
                    date, "%" + searchable + "%", pageable
                )
                .map(AttendanceStudentIndividual::toDto);
    }

    @Override
    public List<IndividualAttendanceSummary> getBestStudentAtAttendance(int classeId, String academicYearId) {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Object[]> studentRanked = attendanceRepository.findAttendanceStatusRanking(
                classeId, UUID.fromString(academicYearId), AttendanceStatus.PRESENT, pageable
        );
        int totalDays = getNumberOfClasseDays(classeId, academicYearId);
        return getSummaryStatus(studentRanked, totalDays);
    }

    @Override
    public List<IndividualAttendanceSummary> getWorstStudentAtAttendance(int classeId, String academicYearId) {
        Pageable pageable = PageRequest.of(0, 5);
        Page<Object[]> studentRanked = attendanceRepository.findAttendanceStatusRanking(
                classeId, UUID.fromString(academicYearId), AttendanceStatus.ABSENT, pageable
        );
        int totalDays = getNumberOfClasseDays(classeId, academicYearId);
        return getSummaryStatus(studentRanked, totalDays);
    }

    @Override
    public List<IndividualAttendanceSummary> getStudentAtAttendanceRanking(String schoolId, String academicYearId, boolean isWorst) {
        Pageable pageable = PageRequest.of(0, 5);
        if (isWorst) {
            Page<Object[]> worstStudents = attendanceRepository.findAttendanceStatusRanking(
                    UUID.fromString(schoolId), UUID.fromString(academicYearId), AttendanceStatus.ABSENT, pageable
            );
            return getSummaryStatus(worstStudents, academicYearId);
        }else {
            Page<Object[]> goodStudents = attendanceRepository.findAttendanceStatusRanking(
                    UUID.fromString(schoolId), UUID.fromString(academicYearId), AttendanceStatus.PRESENT, pageable
            );
            return getSummaryStatus(goodStudents, academicYearId);
        }
    }

    @Override
    public Page<IndividualAttendanceSummary> getStudentAttendanceStatus(int classeId, String academicYearId, Pageable pageable) {
        Page<Long> individualIds = attendanceRepository.findClasseAttendanceStatus(classeId, UUID.fromString(academicYearId), pageable);
        List<Object[]> statuses = attendanceRepository.findClasseAttendanceStatus(
                individualIds.getContent(),
                UUID.fromString(academicYearId)
        );

        int totalDays = getNumberOfClasseDays(classeId, academicYearId);
        List<IndividualAttendanceSummary> summaries = getSummaryStatus(statuses, totalDays);
        return new PageImpl<>(summaries, individualIds.getPageable(), individualIds.getTotalElements());
    }

    @Override
    public List<IndividualAttendanceSummary> getStudentAttendanceStatus(int classeId, String academicYearId, String name) {
        List<Long> individualIds = attendanceRepository.findClasseAttendanceStatus(classeId, UUID.fromString(academicYearId), "%" +  name + "%");
        List<Object[]> statuses = attendanceRepository.findClasseAttendanceStatus(individualIds, UUID.fromString(academicYearId));
        int totalDays = getNumberOfClasseDays(classeId, academicYearId);
        return getSummaryStatus(statuses, totalDays);
    }

    @Override
    public List<AttendanceStatusStat> getClasseAttendanceStats(int classeId, String academicYearId, LocalDate startDate, LocalDate endDate) {
        if (startDate != null && endDate != null) {
            List<Object[]> stats = attendanceRepository.findClasseAttendanceStatsPerDates(classeId, startDate, endDate, UUID.fromString(academicYearId));

            return getAttendanceStats(stats, startDate, endDate);
        }else {
            List<LocalDate> dates = attendanceRepository.findRecentAttendanceDate(classeId, UUID.fromString(academicYearId), PageRequest.of(0, 10));

            List<Object[]> stats = attendanceRepository.findRecentClasseAttendanceStatsPerStatus(classeId, dates, UUID.fromString(academicYearId));
            return CustomMethod.getStats(stats, "dd/MM");
        }
    }

    @Override
    public List<AttendanceStatusStat> getSchoolAttendanceStats(String schoolId, String academicYearId, LocalDate startDate, LocalDate endDate) {
        if (startDate != null && endDate != null) {
            List<Object[]> stats = attendanceRepository.findSchoolAttendanceStatsPerDates(
                    UUID.fromString(schoolId), startDate, endDate, UUID.fromString(academicYearId)
            );

            return getAttendanceStats(stats, startDate, endDate);
        }else {
            List<LocalDate> dates = attendanceRepository.findRecentAttendanceDate(
                    UUID.fromString(schoolId), UUID.fromString(academicYearId), PageRequest.of(0, 10)
            );

            List<Object[]> stats = attendanceRepository.findRecentSchoolAttendanceStatsPerStatus(
                    UUID.fromString(schoolId), dates, UUID.fromString(academicYearId)
            );
            return CustomMethod.getStats(stats, "dd/MM");
        }
    }

    @Override
    public Integer getNumberOfClasseDays(int classeId, String academicYearId) {
        AcademicYear academicYear = academicYearService.getAcademicYearById(academicYearId);
        LocalDate currentDate = LocalDate.now();
        LocalDate endDate = currentDate.isBefore(academicYear.getEndDate()) ? currentDate : academicYear.getEndDate();
        List<PlanningDTO> plannings = planningService.findAllPlanningByClasseThroughoutTheAcademicYear(
                classeId, academicYear.getStartDate(), endDate
        );

        Map<Integer, List<PlanningDTO>> bySemesters = plannings.stream()
                .collect(Collectors.groupingBy(p -> p.getSemestre().getSemesterId()));

        int grandTotalDays = 0;

        for (Map.Entry<Integer, List<PlanningDTO>> entry : bySemesters.entrySet()) {
            List<PlanningDTO> semestersPlannings = entry.getValue();

            LocalDate minStart = semestersPlannings.stream()
                    .map(PlanningDTO::getTermStartDate)
                    .min(LocalDate::compareTo)
                    .orElseThrow();

            LocalDate maxEnd = semestersPlannings.stream()
                    .map(PlanningDTO::getTermEndDate)
                    .max(LocalDate::compareTo)
                    .orElseThrow();

            grandTotalDays = countDays(minStart, maxEnd);
        }

        return grandTotalDays;
    }

    private AttendanceStatusCount getAttendanceStatusCount(List<AttendanceInfo> attendanceInfos) {
        //1. Overall Status count
        Map<AttendanceStatus, Long> statusCount = attendanceInfos.stream()
                .collect(Collectors.groupingBy(
                        AttendanceInfo::getStatus,
                        () -> new EnumMap<>(AttendanceStatus.class),
                        Collectors.counting())
                );

        //2. Status by grade section
        Map<Section, Map<AttendanceStatus, Long>> byGrade = attendanceInfos.stream()
                .collect(Collectors.groupingBy(
                        AttendanceInfo::getSection,
                        LinkedHashMap::new,
                        Collectors.groupingBy(
                                AttendanceInfo::getStatus,
                                () -> new EnumMap<>(AttendanceStatus.class),
                                Collectors.counting())
                ));

        //3. Status by gender
        Map<Gender, Map<AttendanceStatus, Long>> byGender = attendanceInfos.stream()
                .collect(Collectors.groupingBy(
                        AttendanceInfo::getGender,
                        () -> new EnumMap<>(Gender.class),
                        Collectors.groupingBy(
                                AttendanceInfo::getStatus,
                                () -> new EnumMap<>(AttendanceStatus.class),
                                Collectors.counting())
                ));

        return new AttendanceStatusCount(statusCount, byGrade, byGender);
    }

    private Integer countDays(LocalDate start, LocalDate end) {
        int count = 0;
        LocalDate current = start;

        // Loop until the day before the end date
        while (current.isBefore(end)) {
            if (current.getDayOfWeek() != DayOfWeek.SUNDAY) {
                count++;
            }
            current = current.plusDays(1);
        }

        return count;
    }

    private List<IndividualAttendanceSummary> getSummaryStatus(Page<Object[]> objectsCount, int totalDays) {
        return getIndividualAttendanceSummaries(objectsCount.stream(), totalDays);
    }

    private List<IndividualAttendanceSummary> getSummaryStatus(Page<Object[]> objectsCount, String academicYear) {
        return getIndividualAttendanceSummaries(objectsCount.stream(), academicYear);
    }

    private List<IndividualAttendanceSummary> getSummaryStatus(List<Object[]> objectsCount, int totalDays) {
        return getIndividualAttendanceSummaries(objectsCount.stream(), totalDays);
    }

    private List<IndividualAttendanceSummary> getIndividualAttendanceSummaries(Stream<Object[]> stream, String academicYear) {
        class Acc {
            String classeName;
            final List<IndividualAttendanceCount> counts = new ArrayList<>();
        }

        Map<Individual, Acc> map = stream.collect(
                Collectors.groupingBy(
                        row -> ((IndividualBasic) row[0]).toEntity(),
                        LinkedHashMap::new,
                        Collector.of(
                                Acc::new,
                                (acc, row) -> {
                                    acc.classeName = (String) row[4];
                                    acc.counts.add(new IndividualAttendanceCount((AttendanceStatus) row[1], (Long) row[2], (Integer) row[3]));
                                },
                                (acc1, acc2) -> {
                                    if (acc2.classeName != null) {
                                        acc1.classeName = acc2.classeName;
                                    }
                                    acc1.counts.addAll(acc2.counts);
                                    return acc1;
                                }
                        )
                )
        );

        return map.entrySet()
                .stream()
                .map(entry -> {
                    Individual ind = entry.getKey();
                    Acc acc   = entry.getValue();
                    Integer classeId = acc.counts.get(0).classeId();

                    //Now call your helper to get the number of days for that class and academic year:
                    int totalDays = getNumberOfClasseDays(classeId, academicYear);
                    return new IndividualAttendanceSummary(ind, acc.counts, totalDays, acc.classeName);
                })
                .toList();
    }

    private List<IndividualAttendanceSummary> getIndividualAttendanceSummaries(Stream<Object[]> stream, int totalDays) {
        AtomicReference<String> classeName = new AtomicReference<>("");
        return mappingSummary(stream, classeName)
                .entrySet().stream()
                .map(entry -> new IndividualAttendanceSummary(
                        entry.getKey(),
                        entry.getValue(),
                        totalDays,
                        classeName.get())
                )
                .toList();
    }

    private Map<Individual, List<IndividualAttendanceCount>> mappingSummary(Stream<Object[]> stream, AtomicReference<String> classeName) {
        return stream.collect(
                Collectors.groupingBy(
                        row -> {
                            IndividualBasic ib = (IndividualBasic) row[0];
                            return ib.toEntity();
                        },
                        LinkedHashMap::new,
                        Collectors.mapping(
                                row -> {
                                    classeName.set((String) row[4]);
                                    return new IndividualAttendanceCount(
                                            (AttendanceStatus) row[1],
                                            (Long) row[2],
                                            (Integer) row[3]       // this is the classeId
                                    );
                                },
                                Collectors.toList()
                        )
                )
        );
    }

    private List<AttendanceStatusStat> getAttendanceStats(List<Object[]> data, LocalDate startDate, LocalDate endDate) {
        long weeks = ChronoUnit.WEEKS.between(startDate, endDate) + 1;
        long months = ChronoUnit.MONTHS.between(startDate.withDayOfMonth(1), endDate.withDayOfMonth(1)) + 1;

        if (weeks <= 10) {
            return CustomMethod.getStats(data, "yyyy'-W");
        }else if (months <= 10) {
            return CustomMethod.getStats(data, "yyyy-MM");
        }else {
            return CustomMethod.getStats(data, "yyyy");
        }
    }

    private LocalDate getAttendanceDate(LocalDate date) {
        return date != null ? date : LocalDate.now();
    }

    private boolean exists(int schoolId, LocalDate date) {
        long count = attendanceRepository.countAttendanceOfDay(schoolId, date).orElse(0L);
        return count > 0;
    }
}
