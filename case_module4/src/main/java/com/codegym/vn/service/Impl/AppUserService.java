package com.codegym.vn.service.Impl;


import com.codegym.vn.model.AppUser;
import com.codegym.vn.model.Role;
import com.codegym.vn.repository.IAppUserRepository;
import com.codegym.vn.service.interfaceImpl.IAppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppUserService implements IAppUserService {
    @Autowired
    private IAppUserRepository repository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> appUser = repository.findByUsername(username);
        List<Role> roles = new ArrayList<>();
        roles.add(appUser.get().getRole());

        return new User(appUser.get().getUsername(), appUser.get().getPassword(),roles);
    }

    @Override
    public Iterable<AppUser> findAll() {
        return repository.findAll();
    }

    @Override
    public AppUser save(AppUser appUser) {
        return repository.save(appUser);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Page<AppUser> findPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Optional<AppUser> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Iterable<AppUser> findByName(String name) {
        return repository.findAllByUsernameContaining(name);
    }

    @Override
    public Optional<AppUser> findByUsername(String username) {
        return repository.findByUsername(username);
    }

}
