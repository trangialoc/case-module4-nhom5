package com.codegym.vn.service.Impl;

import com.codegym.vn.model.StatusStudent;
import com.codegym.vn.repository.IStatusStudentRepository;
import com.codegym.vn.service.interfaceImpl.IStatusStudent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.thymeleaf.standard.expression.IStandardConversionService;

import java.util.Optional;
@Service
public class StatusStudentServiceImpl implements IStatusStudent {

    @Autowired
    private IStatusStudentRepository repository;
    @Override
    public Iterable<StatusStudent> findAll() {
        return repository.findAll();
    }

    @Override
    public StatusStudent save(StatusStudent statusStudent) {
        return repository.save(statusStudent);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Page<StatusStudent> findPage(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<StatusStudent> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Iterable<StatusStudent> findByName(String name) {
        return null;
    }
}
