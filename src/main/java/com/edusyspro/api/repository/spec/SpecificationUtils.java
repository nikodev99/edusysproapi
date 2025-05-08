package com.edusyspro.api.repository.spec;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public class SpecificationUtils {

    public static <T, Q> void specSorter(Pageable pageable, Root<T> root, CriteriaBuilder cb, CriteriaQuery<Q> cq) {
        if (pageable.getSort().isSorted()) {
            List<Order> orders = new ArrayList<>();
            pageable.getSort().forEach(order -> {
                String property = order.getProperty();

                // Handle nested properties with dot notation
                if (property.contains(".")) {
                    String[] parts = property.split("\\.");
                    jakarta.persistence.criteria.Path<?> path = root;

                    // Build the path by traversing the object graph
                    for (String part : parts) {
                        path = path.get(part);
                    }

                    if (order.isAscending()) {
                        orders.add(cb.asc(path));
                    } else {
                        orders.add(cb.desc(path));
                    }
                } else {
                    // Handle simple properties as before
                    if (order.isAscending()) {
                        orders.add(cb.asc(root.get(property)));
                    } else {
                        orders.add(cb.desc(root.get(property)));
                    }
                }
            });
            cq.orderBy(orders);
        }

    }

}
