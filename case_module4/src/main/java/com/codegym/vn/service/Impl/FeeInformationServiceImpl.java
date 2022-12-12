package com.codegym.vn.service.Impl;

import com.codegym.vn.model.FeeInformation;
import com.codegym.vn.repository.IFeeInformationRepository;
import com.codegym.vn.service.interfaceImpl.IFeeInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class FeeInformationServiceImpl implements IFeeInformationService {

    @Autowired
    private IFeeInformationRepository repository;
    @Override
    public Iterable<FeeInformation> findAll() {
        return repository.findAll();
    }

    @Override
    public FeeInformation save(FeeInformation feeInformation) {
        return repository.save(feeInformation);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Page<FeeInformation> findPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Optional<FeeInformation> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Iterable<FeeInformation> findByName(String name) {
        return null;
    }
}
