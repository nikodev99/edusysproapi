package com.edusyspro.api.service;

import java.util.List;

public interface CustomService<T, U> {

    List<T> fetchAll();

    List<T> fetchAll(int page);

    List<T> fetchAll(int page, int pageSize);

    List<T> fetchAll(int page, int pageSize, String sort);

    List<T> fetchAll(Object ...args);

    List<T> fetchAllById(int page, int pageSize, U id);

    List<T> fetchAllById(Object ...arg);

    T fetchOneById(U id);

    int update(T entity);

    int delete(T entity);

}
