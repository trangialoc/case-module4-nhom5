package com.codegym.vn.repository;

import com.codegym.vn.model.Role;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoleRepository extends PagingAndSortingRepository<Role, Long> {
    Role findByName(String name);
    Iterable<Role> findAllByNameContaining(String name);
}
