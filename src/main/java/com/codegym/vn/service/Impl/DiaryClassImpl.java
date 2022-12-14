package com.codegym.vn.service.Impl;

import com.codegym.vn.model.DiaryClass;
import com.codegym.vn.repository.IDiaryClassRepository;
import com.codegym.vn.service.interfaceImpl.IDiaryClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class DiaryClassImpl implements IDiaryClassService {
    @Autowired
    private IDiaryClassRepository repository;

    @Override
    public Iterable<DiaryClass> findAll() {
        return repository.findAll();
    }

    @Override
    public DiaryClass save(DiaryClass diaryClass) {
        return repository.save(diaryClass);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Page<DiaryClass> findPage(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<DiaryClass> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Iterable<DiaryClass> findByName(String name) {
        return null;
    }
}
