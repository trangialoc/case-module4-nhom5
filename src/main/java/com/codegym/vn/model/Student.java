package com.codegym.vn.model;

import lombok.Data;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
@Data
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String gender;
    @OneToOne
    private Course course;
    @OneToOne
    private AppUser appUser;
    @OneToOne
    private Classes classes;
    private String image;
    @ManyToOne
    private StatusStudent statusStudent;


    @Transient
    MultipartFile multipartFile;
}
