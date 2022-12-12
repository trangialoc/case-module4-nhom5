package com.codegym.vn.repository;

import com.codegym.vn.model.Subject;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISubjectRepository extends PagingAndSortingRepository<Subject, Long> {
    Iterable<Subject> findAllByNameContaining(String name);
}
