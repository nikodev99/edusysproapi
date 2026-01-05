package com.edusyspro.api.utils;

import com.edusyspro.api.dto.AssignmentDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Average {
    public static Map<String, Double> calculateSubjectAverage(List<AssignmentDTO> assignments){
        Map<String, double[]> accumulator = new HashMap<>();

        assignments.forEach(a -> {
            String subject = a.getSubject() != null
                    ? a.getSubject().getCourse()
                    : a.getExamName();

            double mark = a.getMarks().isEmpty() ? 0 : a.getMarks().get(0).getObtainedMark();
            int coefficient = a.getCoefficient() != null ? a.getCoefficient() : 1;

            accumulator.computeIfAbsent(subject, k -> new double[2]);
            accumulator.get(subject)[0] += mark * coefficient;
            accumulator.get(subject)[1] += coefficient;
        });

        return accumulator.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> e.getValue()[1] == 0 ? 0 : e.getValue()[0] / e.getValue()[1]
                ));
    }

    public static double calculateMarkAverage(Map<String, Double> subjectAverage) {
        if (subjectAverage.isEmpty()) return 0;

        double avg = subjectAverage.values().stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0);

        return Math.round(avg * 100.0) / 100.0;
    }
}
