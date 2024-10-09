package com.edusyspro.api.controller.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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

}
