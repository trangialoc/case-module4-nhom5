package com.codegym.vn.service.Impl;

import com.codegym.vn.model.Student;
import com.codegym.vn.repository.IStudentRepository;
import com.codegym.vn.service.interfaceImpl.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class StudentService implements IStudentService {
    @Autowired
    private IStudentRepository repository;

    @Override
    public Iterable<Student> findAll() {
        return repository.findAll();
    }

    @Override
    public Student save(Student student) {
        return repository.save(student);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Page<Student> findPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Optional<Student> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Iterable<Student> findByName(String name) {
        return repository.findAllByNameContaining(name);
    }

    @Override
    public Optional<Student> findStudentByAppUserId(Long id) {
        return repository.findByAppUser_Id(id);
    }

    @Override
    public Iterable<Student> findStudentByClassName(String className) {
        return repository.findByClasses_Name(className);
    }
}
