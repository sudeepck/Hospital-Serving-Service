package com.hospital.Entity;

import jakarta.persistence.*;
import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table
@Builder
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column(nullable = false, length = 50)
    private  String name;

    @Column(nullable = false, length = 100)
    private String specilazation;

    @Column(nullable = false, length = 50, unique = true)
    private  String email;

    @OneToMany(mappedBy = "doctor")
    @JsonIgnore
    private List<Appointment> appointment = new ArrayList<>();

    @ManyToMany(mappedBy = "doctorList")
    @JsonIgnore
    private Set<Department> departments = new HashSet<>();
}
