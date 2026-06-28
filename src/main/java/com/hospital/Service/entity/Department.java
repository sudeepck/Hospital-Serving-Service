package com.hospital.Service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String name;

    @OneToOne // own's the relationShip
    private Doctor headDoctor;

    // 1 doctor in manny doctors
    // 1 doctor in multiple department
    @ManyToMany
    @JoinTable(name= "department_doctors")// many - many will have jointAble jointColunm won't work
    private Set<Doctor> doctors = new HashSet<>();
}
