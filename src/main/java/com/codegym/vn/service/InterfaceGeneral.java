package com.codegym.vn.service;

import com.codegym.vn.model.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface InterfaceGeneral<T> {
    Iterable<T> findAll();

    T save(T t);

    void delete(Long id);

    Page<T> findPage(Pageable pageable);

    Optional<T> findById(Long id);

    Iterable<T>findByName(String name);

}
