package com.codegym.vn.repository;

import com.codegym.vn.model.Teacher;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ITeacherRepository extends PagingAndSortingRepository<Teacher,Long> {
    Iterable<Teacher> findAllByNameContaining(String name);
    Optional<Teacher> findByAppUser_Username(String username);
}
