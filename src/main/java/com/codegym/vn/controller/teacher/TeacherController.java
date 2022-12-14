package com.codegym.vn.controller.teacher;

import com.codegym.vn.model.*;
import com.codegym.vn.service.interfaceImpl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/teachers")
public class TeacherController {
    @Autowired
    private ITeacherService iTeacherService;
    @Autowired
    private IClassesService iClassesService;
    @Autowired
    private IAppUserService iAppUserService;
    @Autowired
    private IStudentService iStudentService;
    @Autowired
    private IDiaryClassService iDiaryClassService;
    @Autowired
    private IDiaryStudentService iDiaryStudentService;

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

    @GetMapping("/showStudent")
    public ResponseEntity<?> showStudentByClass() {
        Optional<Teacher> teacher = iTeacherService.findByAppUsername(getPrincipal());
        Optional<Classes> classes = iClassesService.findById(teacher.get().getClasses().getId());
        Iterable<Student> students = iStudentService.findStudentByClassName(classes.get().getName());
        if (!students.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    @GetMapping("/showTeacher")
    public ResponseEntity<?> showTeacherByClass() {
        Iterable<Teacher> teachers = iTeacherService.findAll();
        if (!teachers.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }

    @PostMapping("/createDiaryClass")
    public ResponseEntity<?> createDiaryClass(@RequestPart("json") DiaryClass diaryClass) {
        DiaryClass diaryClassCreate = iDiaryClassService.save(diaryClass);
        return new ResponseEntity<>(diaryClassCreate, HttpStatus.CREATED);
    }

    @GetMapping("/showTeacherID")
    public ResponseEntity<?> showTeacherInfo() {
        Optional<Teacher> teacher = iTeacherService.findByAppUsername(getPrincipal());
        return new ResponseEntity<>(teacher, HttpStatus.OK);
    }

    @GetMapping("/detailStudent/{id}")
    public ResponseEntity<?> detailStudent(@PathVariable("id") Long id) {
        Optional<Student> student = iStudentService.findById(id);
        if (!student.isPresent()) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(student, HttpStatus.OK);
    }

    @PostMapping("/createDiaryStudent")
    public ResponseEntity<?> createDiaryStudent(@RequestPart("json") DiaryStudent diaryStudent) {
        DiaryStudent diaryStudentCreate = iDiaryStudentService.save(diaryStudent);
        return new ResponseEntity<>(diaryStudentCreate, HttpStatus.CREATED);
    }

//    @GetMapping("/showClassID")
//    public ResponseEntity<?> showClassID(){
//        Optional<Teacher> teacher = iTeacherService.findByAppUsername(getPrincipal());
//
//
//    }
}
