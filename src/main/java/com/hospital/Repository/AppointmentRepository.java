package com.hospital.Repository;

import com.hospital.Entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // Check if doctor has an appointment at exact date/time
    boolean existsByDoctorIdAndAppointmentTime(Long doctorId, LocalDateTime appointmentTime);

    // Check if patient has an appointment at exact date/time
    boolean existsByPatientIdAndAppointmentTime(Long patientId, LocalDateTime appointmentTime);
}