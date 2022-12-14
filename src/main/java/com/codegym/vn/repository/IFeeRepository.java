package com.codegym.vn.repository;

import com.codegym.vn.model.Fee;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface IFeeRepository extends PagingAndSortingRepository<Fee, Long> {
    Iterable<Fee> findByNameContaining(String string);
    Optional<Fee> findByStudent_Id(Long id);
}
