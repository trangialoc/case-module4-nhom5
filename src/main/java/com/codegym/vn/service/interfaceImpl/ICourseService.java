package com.codegym.vn.service.interfaceImpl;

import com.codegym.vn.model.Classes;
import com.codegym.vn.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ICourseService {
    Iterable<Course> findAll();

    Course save(Course course);

    void delete(Long id);

    Page<Course> findPage(Pageable pageable);

    Optional<Course> findById(Long id);

    Iterable<Course>findByName(String name);
}
