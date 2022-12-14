package com.codegym.vn.service.interfaceImpl;

import com.codegym.vn.model.Student;
import com.codegym.vn.model.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ITeacherService {
    Iterable<Teacher> findAll();

    Teacher save(Teacher teacher);

    void delete(Long id);

    Page<Teacher> findPage(Pageable pageable);

    Optional<Teacher> findById(Long id);

    Iterable<Teacher>findByName(String name);

    Optional<Teacher> findByAppUsername (String name);
}
