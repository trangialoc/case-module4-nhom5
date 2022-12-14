package com.codegym.vn.controller.admin;

import com.codegym.vn.model.AppUser;
import com.codegym.vn.model.Course;
import com.codegym.vn.service.interfaceImpl.ICourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("admin/courses")
public class CourseController {
    @Autowired
    private ICourseService iCourseService;

    @GetMapping
    public ResponseEntity<?> showCourses() {
        Iterable<Course> courses = iCourseService.findAll();
        if (!courses.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }
    @GetMapping("/page")
    public ResponseEntity<Page<Course>> showPage(@PageableDefault(value =2) Pageable pageable) {
        Page<Course> courses = iCourseService.findPage(pageable);
        if (!courses.iterator().hasNext()) {
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        Optional<Course> course = iCourseService.findById(id);
        if (!course.isPresent()) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        iCourseService.delete(id);
        return new ResponseEntity<>(course.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestPart("json") Course course) {
        Course courseCreate = iCourseService.save(course);
        return new ResponseEntity<>(courseCreate, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Course> editCourse(@RequestBody Course course, @PathVariable("id") Long id) {
        Optional<Course> courseOptional = iCourseService.findById(id);
        if (!courseOptional.isPresent()) {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        course.setId(courseOptional.get().getId());
        course = iCourseService.save(course);
        return new ResponseEntity<>(course, HttpStatus.OK);
    }

    @GetMapping("/search")
    public  ResponseEntity<Iterable<Course>> showAllByName(@RequestParam("search") String search){
        Iterable<Course> courses = iCourseService.findByName(search);
        if(!courses.iterator().hasNext()){
            new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(courses,HttpStatus.OK);
    }
}
