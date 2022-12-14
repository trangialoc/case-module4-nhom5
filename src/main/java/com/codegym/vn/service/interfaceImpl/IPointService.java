package com.codegym.vn.service.interfaceImpl;

import com.codegym.vn.model.Point;
import com.codegym.vn.service.InterfaceGeneral;

import java.util.Optional;

public interface IPointService extends InterfaceGeneral<Point> {
    Iterable<Point> findPointByStudentID(Long id);
}
