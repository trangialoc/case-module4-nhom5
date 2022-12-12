package com.codegym.vn.repository;


import com.codegym.vn.model.AppUser;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface IAppUserRepository extends PagingAndSortingRepository<AppUser,Long> {
    Optional<AppUser> findByUsername(String username);
    Iterable<AppUser> findAllByUsernameContaining(String username);

}
