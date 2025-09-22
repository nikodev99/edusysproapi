package com.edusyspro.api.controller.utils;

import com.edusyspro.api.dto.custom.UpdateField;
import com.edusyspro.api.model.enums.Role;
import com.edusyspro.api.utils.Datetime;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ControllerUtils {

    public static Pageable setSort(int page, int size, String sortCriteria) {
        Pageable pageable = PageRequest.of(page, size);
        if (sortCriteria != null && !sortCriteria.isEmpty()) {
            List<Sort.Order> orders = Stream.of(sortCriteria.split(","))
                    .map(criteria -> criteria.split(":"))
                    .map(c -> new Sort.Order(Sort.Direction.fromString(c[1]), c[0]))
                    .toList();
            pageable = PageRequest.of(page, size, Sort.by(orders));
        }
        return pageable;
    }

    public static Pageable setPage(int page, int size) {
        return PageRequest.of(page, size);
    }

    public static LocalDate parseDate(String date) {
        LocalDate dateOfTheDay = null;
        if (date != null) {
            if (date.length() > 10) {
                dateOfTheDay = Datetime.zonedDateTime(date).toLocalDate();
            }else {
                dateOfTheDay = LocalDate.parse(date);
            }
        }
        return dateOfTheDay;
    }

    public static List<Role> normalizeRoles(UpdateField field) {
        List<Role> roles = new ArrayList<>();
        if ("roles".equals(field.field())) {
            Object raw = field.value();
            if (raw instanceof List<?>) {
                roles = ((List<?>) raw).stream()
                        .map(Object::toString)
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .map(Role::valueOf)
                        .toList();
            } else if (raw instanceof String) {
                String s = ((String) raw).replaceAll("[\\[\\]\"]", "");
                roles = Arrays.stream(s.split(","))
                        .map(String::trim)
                        .filter(t -> !t.isEmpty())
                        .map(Role::valueOf)
                        .toList();
            }
        }
        return roles;
    }

}
