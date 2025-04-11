package com.edusyspro.api.service;

import com.edusyspro.api.dto.custom.GenderCount;
import com.edusyspro.api.model.enums.Gender;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomMethod {

    public static List<GenderCount> genderCountInClasse(List<Object[]> fetchedData) {
        Map<Gender, List<LocalDate>> groupedByGender = fetchedData.stream()
                .collect(Collectors.groupingBy(
                        row -> (Gender) row[0],
                        Collectors.mapping(row -> ((LocalDate) row[1]), Collectors.toList())
                ));

        return groupedByGender.entrySet().stream()
                .map(entry -> {
                    Gender gender = entry.getKey();
                    List<LocalDate> birthdays = entry.getValue();
                    long count = birthdays.size();
                    int averageAge = (int) birthdays.stream()
                            .mapToInt(birthday -> Period.between(birthday, LocalDate.now()).getYears())
                            .average()
                            .orElse(0);
                    return new GenderCount(gender, count, averageAge);
                })
                .toList();
    }

}
