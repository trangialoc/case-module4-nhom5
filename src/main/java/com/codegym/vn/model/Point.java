package com.codegym.vn.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double practicePoint;
    private Double theoreticalPoint;

   @ManyToOne
    private Student student;


}
