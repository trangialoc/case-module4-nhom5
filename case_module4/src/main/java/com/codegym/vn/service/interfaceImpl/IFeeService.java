package com.codegym.vn.service.interfaceImpl;

import com.codegym.vn.model.Fee;
import com.codegym.vn.service.InterfaceGeneral;

import java.util.Optional;

public interface IFeeService extends InterfaceGeneral<Fee> {

    Optional<Fee> findStudentByStudentID(Long id);
}
