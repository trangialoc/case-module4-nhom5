package com.codegym.vn.service.interfaceImpl;

import com.codegym.vn.model.Student;
import com.codegym.vn.service.InterfaceGeneral;

import java.util.Optional;

public interface IStudentService extends InterfaceGeneral<Student> {
    Optional<Student> findStudentByAppUserId(Long id);
    Iterable<Student> findStudentByClassName(String className);
}
