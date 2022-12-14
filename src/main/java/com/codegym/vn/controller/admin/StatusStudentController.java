package com.codegym.vn.controller.admin;

import com.codegym.vn.model.AppUser;
import com.codegym.vn.model.StatusStudent;
import com.codegym.vn.service.interfaceImpl.IStatusStudent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("admin/statusStudents")
public class StatusStudentController {
    @Autowired
    private IStatusStudent iStatusStudent;
    @GetMapping
    public ResponseEntity<Iterable<StatusStudent>> showAll() {
        Iterable<StatusStudent> statusStudents = iStatusStudent.findAll();
        if (!statusStudents.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(statusStudents, HttpStatus.OK);
    }
}
