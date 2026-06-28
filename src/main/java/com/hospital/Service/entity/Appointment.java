package com.hospital.Service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime appointmentTime;

    @Column(length = 500)
    private String reason;

    @ToString.Exclude
    @ManyToOne // y no cascesde here becz removing patent when appointment is removed is wrong
    @JoinColumn(name = "patient_id", nullable = false) // owing the relationShip
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false) // owing the relationship
    @ToString.Exclude
    private Doctor doctor;
}
