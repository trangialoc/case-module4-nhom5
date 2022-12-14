package com.codegym.vn.service.Impl;

import com.codegym.vn.model.Student;
import com.codegym.vn.model.Teacher;
import com.codegym.vn.repository.ITeacherRepository;
import com.codegym.vn.service.interfaceImpl.ITeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class TeacherServiceImp  implements ITeacherService {
    @Autowired
    private ITeacherRepository iTeacherRepository;


    @Override
    public Iterable<Teacher> findAll() {
        return iTeacherRepository.findAll();
    }

    @Override
    public Teacher save(Teacher teacher) {
        return iTeacherRepository.save(teacher);
    }

    @Override
    public void delete(Long id) {
        iTeacherRepository.deleteById(id);
    }

    @Override
    public Page<Teacher> findPage(Pageable pageable) {
        return iTeacherRepository.findAll(pageable);
    }

    @Override
    public Optional<Teacher> findById(Long id) {
        return iTeacherRepository.findById(id);
    }

    @Override
    public Iterable<Teacher> findByName(String name) {
        return iTeacherRepository.findAllByNameContaining(name);
    }

    @Override
    public Optional<Teacher> findByAppUsername(String name) {
        return iTeacherRepository.findByAppUser_Username(name);
    }
}
