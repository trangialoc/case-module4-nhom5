package com.codegym.vn.service.interfaceImpl;

import com.codegym.vn.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IRoleService {
    Iterable<Role> findAll();

    Role save(Role role);

    void delete(Long id);

    Page<Role> findPage(Pageable pageable);

    Optional<Role> findById(Long id);

    Iterable<Role>findByName(String name);
}
