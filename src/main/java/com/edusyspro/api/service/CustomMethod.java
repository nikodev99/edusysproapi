package com.edusyspro.api.service;

import com.edusyspro.api.dto.custom.AttendanceStatusStat;
import com.edusyspro.api.dto.custom.GenderCount;
import com.edusyspro.api.dto.custom.StudentCount;
import com.edusyspro.api.model.enums.AttendanceStatus;
import com.edusyspro.api.model.enums.Gender;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomMethod {

    public static GenderCount genderCountInClasse(List<Object[]> fetchedData) {
        Map<Gender, List<LocalDate>> groupedByGender = fetchedData.stream()
                .collect(Collectors.groupingBy(
                        row -> (Gender) row[0],
                        Collectors.mapping(row -> ((LocalDate) row[1]), Collectors.toList())
                ));

        double overallAverage = groupedByGender.values().stream()
                .flatMap(List::stream)
                .mapToInt(birth -> Period.between(birth, LocalDate.now()).getYears())
                .average()
                .orElse(0);

        return new GenderCount(
                fetchedData.size(),
                overallAverage,
                groupedByGender.entrySet().stream()
                    .map(entry -> new StudentCount(
                            entry.getKey(),
                            entry.getValue().size(),
                            entry.getValue().stream()
                                    .mapToInt(birthday -> Period.between(birthday, LocalDate.now()).getYears())
                                    .average()
                                    .orElse(0)
                            ))
                .toList()
        );
    }

    public static List<AttendanceStatusStat> getStats(List<Object[]> data, String format) {
        Map<String, Map<AttendanceStatus, Long>> groupedData = data.stream()
                .collect(Collectors.groupingBy(row -> ((LocalDate) row[1]).format(DateTimeFormatter.ofPattern(format)),
                        LinkedHashMap::new,
                        Collectors.toMap(
                                row -> (AttendanceStatus) row[0],
                                row -> (Long) row[2],
                                Long::sum,
                                LinkedHashMap::new
                        )));

        return  groupedData.entrySet().stream()
                .limit(10)
                .map(entry -> {
                    Map<AttendanceStatus, Long> statusCounts = entry.getValue();
                    return new AttendanceStatusStat(
                            entry.getKey(),
                            statusCounts.getOrDefault(AttendanceStatus.PRESENT, 0L),
                            statusCounts.getOrDefault(AttendanceStatus.ABSENT, 0L),
                            statusCounts.getOrDefault(AttendanceStatus.LATE, 0L),
                            statusCounts.getOrDefault(AttendanceStatus.EXCUSED, 0L)
                    );
                })
                .toList();
    }

}
