package com.edusyspro.api.service;

import com.edusyspro.api.dto.custom.UpdateField;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface CustomService<T, U> {

    T save(T entity);

    List<T> saveAll(List<T> entities);

    List<T> fetchAll();

    List<T> fetchAll(String schoolId);

    Page<T> fetchAll(String schoolId, Pageable pageable);

    List<T> fetchAll(Object ...args);

    Page<T> fetchAll(Pageable pageable, Object ...args);

    List<T> fetchAllById(U id);

    List<T> fetchAllById(Object ...arg);

    Page<T> fetchAllByOtherEntityId(String otherEntityId, Pageable pageable);

    List<T> fetchAllByOtherEntityId(String otherEntityId);

    List<T> fetchAllByOtherEntityId(Object ...arg);

    T fetchOneById(U id);

    T fetchOneById(U id, String schoolId);

    T fetchOneById(U id, Object ...args);

    T fetchOneByCustomColumn(String columnValue, String schoolId);

    T fetchOneByCustomColumn(String columnValue);

    T fetchOneByCustomColumn(String columnValue, Object ...args);

    T fetchOneById(Object ...arg);

    int update(T entity);

    Map<String, Boolean> update(T entity, U id);

    int patch(U id, UpdateField field);

    int delete(T entity);

    Map<String, Long> count(U id);

    Map<String, Long> count(String schoolId);

    Map<String, Long> count(Object ...args);
}
