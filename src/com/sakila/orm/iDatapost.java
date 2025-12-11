package com.sakila.orm;

import java.util.List;


public interface iDatapost<T> {
    boolean insert(T entity);
    boolean update(T entity);
    boolean delete(int id);
    T getById(int id);
    List<T> getAll();
    List<T> search(String text);
}