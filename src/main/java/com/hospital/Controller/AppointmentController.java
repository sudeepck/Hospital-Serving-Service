package com.hospital.Controller;

import com.hospital.Entity.Appointment;
import com.hospital.Service.AppointmentService;
import com.hospital.dto.Appointment.AppointmentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    // 1. Create new appointment
    @PostMapping("/create")
    public ResponseEntity<AppointmentResponseDto> createAppointment(
            @RequestBody Appointment appointment,
            @RequestParam Long doctorId,
            @RequestParam Long patientId) {
        AppointmentResponseDto createdAppointment = appointmentService.createNewAppointment(appointment, doctorId, patientId);
        return ResponseEntity.ok(createdAppointment);
    }

    // 2. Reassign to another doctor
    @PutMapping("/reassign")
    public ResponseEntity<AppointmentResponseDto> reassignDoctor(
            @RequestParam Long appointmentId,
            @RequestParam Long doctorId
    ) {
        AppointmentResponseDto updated = appointmentService.reassignAppointmentToAnotherDoctor(appointmentId, doctorId);
        return ResponseEntity.ok(updated);
    }

    // 3. Get all appointments
    @GetMapping
    public ResponseEntity<List<AppointmentResponseDto>> getAllAppointments() {
        return ResponseEntity.ok(appointmentService.getAllAppointment());
    }

}
