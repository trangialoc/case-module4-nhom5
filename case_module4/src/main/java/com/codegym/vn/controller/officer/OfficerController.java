package com.codegym.vn.controller.officer;

import com.codegym.vn.model.*;
import com.codegym.vn.service.interfaceImpl.IAppUserService;
import com.codegym.vn.service.interfaceImpl.IClassesService;
import com.codegym.vn.service.interfaceImpl.IPointService;
import com.codegym.vn.service.interfaceImpl.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/officers")
public class OfficerController {
    @Autowired
    IStudentService iStudentService;
    @Autowired
    IAppUserService iAppUserService;
    @Autowired
    IClassesService iClassesService;
    @Autowired
    IPointService iPointService;


    private String getPrincipal() {
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

    @GetMapping("/showClass")
    public ResponseEntity<Iterable<Classes>> showClasses(){
        Iterable<Classes> classes = iClassesService.findAll();
        if (!classes.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(classes, HttpStatus.OK);
    }

    @GetMapping("/showStudent/{classes}")
    public ResponseEntity<Iterable<Student>> showStudentByClass(@PathVariable("classes") String classes){
        Iterable<Student> students = iStudentService.findStudentByClassName(classes);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Point> addPoint(@RequestPart("json") Point point){
        Point pointCreate = iPointService.save(point);
        return new ResponseEntity<>(pointCreate, HttpStatus.CREATED);
    }

    @GetMapping("/getPoint/{id}")
    public ResponseEntity<Iterable<Point>> displayPoint(@PathVariable("id") Long id){
        Iterable<Point> points = iPointService.findPointByStudentID(id);
        return new ResponseEntity<>(points, HttpStatus.OK);
    }

    @PutMapping("editPoint/{id}")
    public ResponseEntity<Point> editPoint(@PathVariable("id") Long id, @RequestPart("json") Point point ){
        Optional<Point> pointEdit = iPointService.findById(id);
        if (!pointEdit.isPresent()) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        point.setId(pointEdit.get().getId());
        point = iPointService.save(point);
        return new ResponseEntity<>(point, HttpStatus.OK);

  }
}
