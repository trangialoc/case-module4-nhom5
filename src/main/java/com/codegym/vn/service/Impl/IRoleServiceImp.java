package com.codegym.vn.service.Impl;

import com.codegym.vn.model.Role;
import com.codegym.vn.repository.IRoleRepository;
import com.codegym.vn.service.interfaceImpl.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class IRoleServiceImp implements IRoleService {
    @Autowired
    IRoleRepository repository;
    @Override
    public Iterable<Role> findAll() {
        return repository.findAll();
    }

    @Override
    public Role save(Role role) {
        return repository.save(role);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Page<Role> findPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Optional<Role> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Iterable<Role> findByName(String name) {
        return repository.findAllByNameContaining(name);
    }
}
