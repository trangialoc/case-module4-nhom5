package com.codegym.vn.service.Impl;

import com.codegym.vn.model.DiaryStudent;
import com.codegym.vn.repository.IDiaryStudentRepository;
import com.codegym.vn.service.interfaceImpl.IDiaryStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class DiaryStudentServiceImpl implements IDiaryStudentService {
    @Autowired
    private IDiaryStudentRepository repository;

    @Override
    public Iterable<DiaryStudent> findAll() {
        return repository.findAll();
    }

    @Override
    public DiaryStudent save(DiaryStudent diaryStudent) {
        return repository.save(diaryStudent);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Page<DiaryStudent> findPage(Pageable pageable) {
        return null;
    }

    @Override
    public Optional<DiaryStudent> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Iterable<DiaryStudent> findByName(String name) {
        return null;
    }
}
