package com.codegym.vn.controller.admin;

import com.codegym.vn.model.Student;
import com.codegym.vn.service.interfaceImpl.IStudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("admin/students")
public class AdminStudentController {
    @Value("${upload.path}")
    private String upload;

    @Value("${render.path}")
    private String render;

    @Autowired
    private IStudentService iStudentService;

    @GetMapping
    public ResponseEntity<Iterable<Student>> showAll() {
        Iterable<Student> students = iStudentService.findAll();
        if (!students.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(students, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Student> showOne(@PathVariable("id") Long id) {
        Optional<Student> student = iStudentService.findById(id);
        if (!student.isPresent()) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(student.get(), HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<Student>> showPage(@PageableDefault(value = 2) Pageable pageable) {
        Page<Student> students = iStudentService.findPage(pageable);
        if (!students.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(students, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestPart("json") Student student,
                                                 @RequestPart("file") MultipartFile file){
        String fileName = file.getOriginalFilename();
        try {
            FileCopyUtils.copy(file.getBytes(), new File(upload + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        student.setImage(render + fileName);
        Student studentCreate = iStudentService.save(student);

        return new ResponseEntity<>(studentCreate, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Student> editStudent(@RequestPart("json") Student studentEdit,
                                               @RequestPart("file") MultipartFile file,
                                               @PathVariable("id") Long id){
        Optional<Student> student = iStudentService.findById(id);
        if (!student.isPresent()) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        String fileName = file.getOriginalFilename();
        try {
            FileCopyUtils.copy(file.getBytes(), new File(upload + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        studentEdit.setId(student.get().getId());
        studentEdit.setImage(render + fileName);
        studentEdit = iStudentService.save(studentEdit);
        return new ResponseEntity<>(studentEdit, HttpStatus.OK);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> delete(@PathVariable("id") Long id){
        Optional<Student> student = iStudentService.findById(id);
        if(!student.isPresent()){
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        iStudentService.delete(id);
        return new ResponseEntity<>(student.get(),HttpStatus.OK);
    }

    @GetMapping("/search")
    public  ResponseEntity<Iterable<Student>> showAllByName(@RequestParam("search") String search){
        Iterable<Student> students = iStudentService.findByName(search);
        if(!students.iterator().hasNext()){
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(students,HttpStatus.OK);
    }

}
