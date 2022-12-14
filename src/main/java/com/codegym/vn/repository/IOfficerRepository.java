package com.codegym.vn.repository;

import com.codegym.vn.model.Officer;
import com.codegym.vn.model.Teacher;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IOfficerRepository extends PagingAndSortingRepository<Officer, Long> {
    Iterable<Officer> findAllByNameContaining(String name);
}
