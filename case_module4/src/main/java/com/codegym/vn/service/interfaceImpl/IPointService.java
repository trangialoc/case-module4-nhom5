package com.codegym.vn.service.interfaceImpl;

import com.codegym.vn.model.Point;
import com.codegym.vn.service.InterfaceGeneral;

public interface IPointService extends InterfaceGeneral<Point> {
    Iterable<Point> findPointByStudentID(Long id);
}
