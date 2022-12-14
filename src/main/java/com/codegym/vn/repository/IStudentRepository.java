package com.codegym.vn.repository;

import com.codegym.vn.model.Student;
import com.codegym.vn.model.Teacher;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IStudentRepository extends PagingAndSortingRepository<Student, Long> {
    Iterable<Student> findAllByNameContaining(String name);
    Optional<Student> findByAppUser_Id(Long id);
    Iterable<Student> findByClasses_Name(String name);
}
