package com.codegym.vn.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "appUsers")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String email;
    private String username;
    private String password;
    private String rePassword;
    private String phoneNumber;
    private int birth;
    private String address;
    private String identify;
    @ManyToOne
    private Role role;
}
