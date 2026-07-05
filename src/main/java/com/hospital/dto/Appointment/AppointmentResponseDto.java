package com.hospital.dto.Appointment;

import com.hospital.dto.Doctor.DoctorAppointmentDto;
import com.hospital.dto.Patient.PatientResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppointmentResponseDto {
    private Long id;
    private LocalDateTime appointmentTime;
    private String reason;
    private DoctorAppointmentDto doctor;
    private PatientResponseDTO patient;
}
