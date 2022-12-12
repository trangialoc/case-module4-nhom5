package com.codegym.vn.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;

@Entity
@Data
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String gender;
    @OneToOne
    private AppUser appUser;
    private String image;
    @ManyToOne
    private Classes classes;

    @Transient
    MultipartFile multipartFile;

}
