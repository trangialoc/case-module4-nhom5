package com.codegym.vn.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Classes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
