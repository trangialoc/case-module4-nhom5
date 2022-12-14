package com.codegym.vn.service.interfaceImpl;

import com.codegym.vn.model.Classes;
import com.codegym.vn.model.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IClassesService {
    Iterable<Classes> findAll();

    Classes save(Classes classes);

    void delete(Long id);

    Page<Classes> findPage(Pageable pageable);

    Optional<Classes> findById(Long id);

    Iterable<Classes>findByName(String name);
}
