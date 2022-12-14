package com.codegym.vn.service.Impl;

import com.codegym.vn.model.Fee;
import com.codegym.vn.repository.IFeeRepository;
import com.codegym.vn.service.interfaceImpl.IFeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class FeeServiceImpl implements IFeeService {
    @Autowired
    private IFeeRepository repository;
    @Override
    public Iterable<Fee> findAll() {
        return repository.findAll();
    }

    @Override
    public Fee save(Fee fee) {
        return repository.save(fee);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Page<Fee> findPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Optional<Fee> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Iterable<Fee> findByName(String name) {
        return repository.findByNameContaining(name);
    }

    @Override
    public Optional<Fee> findStudentByStudentID(Long id) {
        return repository.findByStudent_Id(id);
    }
}
