package com.codegym.vn.controller.admin;

import com.codegym.vn.model.Officer;
import com.codegym.vn.service.interfaceImpl.IOfficerService;
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
@RequestMapping("admin/officers")
public class AdminOfficerController {
    @Autowired
    private IOfficerService iOfficerService;
    @Value("${upload.path}")
    private String upload;

    @Value("${render.path}")
    private String render;


    @GetMapping
    public ResponseEntity<Iterable<Officer>> showAll() {
        Iterable<Officer> officers = iOfficerService.findAll();
        if (!officers.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(officers, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Officer> showOne(@PathVariable("id") Long id) {
        Optional<Officer> officer = iOfficerService.findById(id);
        if (!officer.isPresent()) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(officer.get(), HttpStatus.OK);
    }

    @GetMapping("/page")
    public ResponseEntity<Page<Officer>> showPage(@PageableDefault(value = 1) Pageable pageable) {
        Page<Officer> officers = iOfficerService.findPage(pageable);
        if (!officers.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(officers, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<Officer> createTeacher(@RequestPart("json") Officer officer,
                                                 @RequestPart("file") MultipartFile file){
        String fileName = file.getOriginalFilename();
        try {
            FileCopyUtils.copy(file.getBytes(), new File(upload + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        officer.setImage(render + fileName);
        Officer officerCreate = iOfficerService.save(officer);
        return new ResponseEntity<>(officerCreate, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Officer> editStudent(@RequestPart("json") Officer officerEdit,
                                               @RequestPart("file") MultipartFile file,
                                               @PathVariable("id") Long id){
        Optional<Officer> officer = iOfficerService.findById(id);
        if (!officer.isPresent()) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        String fileName = file.getOriginalFilename();
        try {
            FileCopyUtils.copy(file.getBytes(), new File(upload + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        officerEdit.setId(officer.get().getId());
        officerEdit.setImage(render + fileName);
        officerEdit = iOfficerService.save(officerEdit);
        return new ResponseEntity<>(officerEdit, HttpStatus.OK);

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Officer> delete(@PathVariable("id") Long id){
        Optional<Officer> officer = iOfficerService.findById(id);
        if(!officer.isPresent()){
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        iOfficerService.delete(id);
        return new ResponseEntity<>(officer.get(),HttpStatus.OK);
    }

    @GetMapping("/search")
    public  ResponseEntity<Iterable<Officer>> showAllByName(@RequestParam("search") String search){
        Iterable<Officer> officers = iOfficerService.findByName(search);
        if(!officers.iterator().hasNext()){
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(officers,HttpStatus.OK);
    }
}
