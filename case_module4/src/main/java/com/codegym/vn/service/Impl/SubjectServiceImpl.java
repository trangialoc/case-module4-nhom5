package com.codegym.vn.service.Impl;

import com.codegym.vn.model.Subject;
import com.codegym.vn.repository.ISubjectRepository;
import com.codegym.vn.service.interfaceImpl.ISubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class SubjectServiceImpl implements ISubjectService {

    @Autowired
    private ISubjectRepository subjectRepository;

    @Override
    public Iterable<Subject> findAll() {
        return subjectRepository.findAll();
    }

    @Override
    public Subject save(Subject subject) {
        return subjectRepository.save(subject);
    }

    @Override
    public void delete(Long id) {
        subjectRepository.deleteById(id);
    }

    @Override
    public Page<Subject> findPage(Pageable pageable) {
        return subjectRepository.findAll(pageable);
    }

    @Override
    public Optional<Subject> findById(Long id) {
        return subjectRepository.findById(id);
    }

    @Override
    public Iterable<Subject> findByName(String name) {
        return subjectRepository.findAllByNameContaining(name);
    }
}
