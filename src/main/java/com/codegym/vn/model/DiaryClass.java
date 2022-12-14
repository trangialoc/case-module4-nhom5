package com.codegym.vn.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class DiaryClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate localDate;
    private String note;
    @OneToOne
    private Teacher teacher;
    @ManyToOne
    private Classes classes;

}
