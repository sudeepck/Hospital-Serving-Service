package com.hospital.Service.repository;

import com.hospital.Service.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApointmentRepository extends JpaRepository<Appointment, Long> {
}