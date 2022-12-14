package com.codegym.vn.service.Impl;

import com.codegym.vn.model.Point;
import com.codegym.vn.repository.IPointRepository;
import com.codegym.vn.service.interfaceImpl.IPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class PointService implements IPointService {
@Autowired
private IPointRepository repository;

    @Override
    public Iterable<Point> findAll() {
        return repository.findAll();
    }

    @Override
    public Point save(Point point) {
        return repository.save(point);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Page<Point> findPage(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Optional<Point> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Iterable<Point> findByName(String name) {
        return null;
    }

    @Override
    public Iterable<Point> findPointByStudentID(Long id) {
        return repository.findByStudent_Id(id);
    }
}
