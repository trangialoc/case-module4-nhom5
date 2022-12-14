package com.codegym.vn.controller.admin;

import com.codegym.vn.model.Teacher;
import com.codegym.vn.service.interfaceImpl.ITeacherService;
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
@RequestMapping("/admin/teachers")
public class AdminTeacherController {
    @Value("${upload.path}")
    private String upload;

    @Value("${render.path}")
    private String render;

    @Autowired
    private ITeacherService iTeacherService;

    @GetMapping
    public ResponseEntity<Iterable<Teacher>> showAll() {
        Iterable<Teacher> teachers = iTeacherService.findAll();
        if (!teachers.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Teacher> showOne(@PathVariable("id") Long id) {
        Optional<Teacher> teacher = iTeacherService.findById(id);
        if (!teacher.isPresent()) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(teacher.get(), HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<Teacher>> showPage(@PageableDefault(value = 2) Pageable pageable) {
        Page<Teacher> teachers = iTeacherService.findPage(pageable);
        if (!teachers.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Teacher> createTeacher(@RequestPart("json") Teacher teacher,
                                                 @RequestPart("file") MultipartFile file){
        String fileName = file.getOriginalFilename();
        try {
            FileCopyUtils.copy(file.getBytes(), new File(upload + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        teacher.setImage(render + fileName);
        Teacher teacherCreate = iTeacherService.save(teacher);
        return new ResponseEntity<>(teacherCreate, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Teacher> editStudent(@RequestPart("json") Teacher teacherEdit,
                                               @RequestPart("file") MultipartFile file,
                                               @PathVariable("id") Long id){
        Optional<Teacher> teacher = iTeacherService.findById(id);
        if (!teacher.isPresent()) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        String fileNamePicture = file.getOriginalFilename();
        try {
            FileCopyUtils.copy(file.getBytes(), new File(upload + fileNamePicture));
        } catch (IOException e) {
            e.printStackTrace();
        }
        teacherEdit.setId(teacher.get().getId());
        teacherEdit.setImage(render + fileNamePicture);
        teacherEdit = iTeacherService.save(teacherEdit);
        return new ResponseEntity<>(teacherEdit, HttpStatus.OK);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Teacher> delete(@PathVariable("id") Long id){
        Optional<Teacher> teacher = iTeacherService.findById(id);
        if(!teacher.isPresent()){
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        iTeacherService.delete(id);
        return new ResponseEntity<>(teacher.get(),HttpStatus.OK);
    }

    @GetMapping("/search")
    public  ResponseEntity<Iterable<Teacher>> showAllByName(@RequestParam("search") String search){
        Iterable<Teacher> teachers = iTeacherService.findByName(search);
        if(!teachers.iterator().hasNext()){
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(teachers,HttpStatus.OK);
    }}