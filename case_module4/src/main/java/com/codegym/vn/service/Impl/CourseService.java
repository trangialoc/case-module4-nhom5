package com.codegym.vn.service.Impl;

import com.codegym.vn.model.Course;
import com.codegym.vn.repository.ICourseRepository;
import com.codegym.vn.service.interfaceImpl.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class CourseService implements ICourseService {

    @Autowired
    private ICourseRepository repository;
    @Override
    public Iterable<Course> findAll() {
        return repository.findAll();
    }

    @Override
    public Course save(Course course) {
        return repository.save(course);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Page<Course> findPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Optional<Course> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Iterable<Course> findByName(String name) {
        return repository.findAllByNameContaining(name);
    }
}
