package com.hospital.Service.service;

import com.hospital.Service.dto.AppointmentResponseDto;
import com.hospital.Service.dto.CreateAppointmentRequestDto;
import com.hospital.Service.entity.Appointment;
import com.hospital.Service.entity.Doctor;
import com.hospital.Service.entity.Patient;
import com.hospital.Service.repository.ApointmentRepository;
import com.hospital.Service.repository.DoctorRepository;
import com.hospital.Service.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final ApointmentRepository apointmentRepository;
    private final ModelMapper modelMapper;


    @Transactional
    public AppointmentResponseDto createNewAppointment(CreateAppointmentRequestDto request){
        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with ID: " + request.getPatientId()));
        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with ID: " + request.getDoctorId()));

        Appointment appointment = Appointment.builder()
                .reason(request.getReason())
                .appointmentTime(request.getAppointmentTime())
                .build();

        appointment.setDoctor(doctor); // since appointment is owning side we can leave  ( saving appointment in doctor)
        appointment.setPatient(patient);

        patient.getAppointments().add(appointment);
        doctor.getAppointments().add(appointment);

        appointment =  apointmentRepository.save(appointment);

        return modelMapper.map(appointment, AppointmentResponseDto.class);
    }

    @Transactional
    public Appointment reAssignAppointmentToAnotherDoctor(Long appointmentId, Long doctorId) {
        Appointment appointment = apointmentRepository.findById(appointmentId).orElseThrow();
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();

        appointment.setDoctor(doctor); // this will automatically call the update, because it is dirty
        doctor.getAppointments().add(appointment); // just for bidirectional consistency

        return appointment;
    }

    public List<AppointmentResponseDto> getAllAppointmentsOfDoctor(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow();

        return doctor.getAppointments()
                .stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentResponseDto.class))
                .collect(Collectors.toList());
    }



}
