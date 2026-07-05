package com.hospital.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hospital.Entity.Type.BloodGroupType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_patient_name_birthdate", columnNames = {"name", "birthDate"})
        }
)
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @Column( unique = true, length = 40, nullable = false)
    private  String name;

    private LocalDate birthDate;

    @Column(unique = true, length = 60, nullable = false)
    private  String email;

    private  String gender;

    @CreationTimestamp
    @Column(updatable = false)
    @ToString.Exclude
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private BloodGroupType bloodGroup;

    @OneToOne(cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinColumn(name = "patient_insurance_id") // owing side FK
    private Insurance insurance;

    @OneToMany(mappedBy = "patient", cascade = {CascadeType.REMOVE}, orphanRemoval = true, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Appointment> appointments = new ArrayList<>();
}
