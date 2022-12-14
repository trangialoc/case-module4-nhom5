package com.codegym.vn.repository;

import com.codegym.vn.model.Classes;
import com.codegym.vn.model.Course;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICourseRepository extends PagingAndSortingRepository<Course, Long> {
    Course findByName(String name);
    Iterable<Course> findAllByNameContaining(String name);
}
