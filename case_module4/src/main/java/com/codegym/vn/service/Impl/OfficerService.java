package com.codegym.vn.service.Impl;

import com.codegym.vn.model.Officer;
import com.codegym.vn.repository.IOfficerRepository;
import com.codegym.vn.service.interfaceImpl.IOfficerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OfficerService implements IOfficerService {
    @Autowired
    private IOfficerRepository repository;

    @Override
    public Iterable<Officer> findAll() {
        return repository.findAll();
    }

    @Override
    public Officer save(Officer officer) {
        return repository.save(officer);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Page<Officer> findPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Optional<Officer> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Iterable<Officer> findByName(String name) {
        return repository.findAllByNameContaining(name);
    }
}
