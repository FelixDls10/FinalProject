package com.sakila.orm;

import java.util.List;

public interface iDatapost<T> {

    boolean insert(T entity);      // POST
    boolean update(T entity);      // PUT
    boolean delete(int id);        // DELETE
    T getById(int id);             // GET by PK
    List<T> getAll();              // GET all
    List<T> search(String text);   // GET filtrado
}
