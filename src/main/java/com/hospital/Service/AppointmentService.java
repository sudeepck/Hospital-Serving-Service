package com.hospital.Service;

import com.hospital.Entity.Appointment;
import com.hospital.Entity.Doctor;
import com.hospital.Entity.Patient;
import com.hospital.Repository.AppointmentRepository;
import com.hospital.Repository.DoctorRepository;
import com.hospital.Repository.PatientsRepository;
import com.hospital.dto.Appointment.AppointmentResponseDto;
import com.hospital.dto.Doctor.DoctorAppointmentDto;
import com.hospital.dto.InsuranceResponseDto;
import com.hospital.dto.Patient.PatientResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private  final AppointmentRepository appointmentRepository;
    private  final DoctorRepository doctorRepository;
    private  final PatientsRepository patientsRepository;



    @Transactional
    public  AppointmentResponseDto createNewAppointment(Appointment appointment, Long doctorId, Long patientId){
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("No doctore found with id:" + " " + doctorId));
        Patient patient = patientsRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient Not found with id:" + " " + patientId));

        System.out.println(appointment.getAppointmentTime() +  " "   + appointment.getCreatedAt());
        if(appointment.getId() != null)
            throw  new IllegalArgumentException("Appointment should not have ");

        LocalDateTime appointmentTime = appointment.getAppointmentTime();

        // Check for Doctor schedule conflict
        boolean doctorConflict = appointmentRepository.existsByDoctorIdAndAppointmentTime(doctorId, appointmentTime);
        if (doctorConflict) {
            throw new IllegalArgumentException("Doctor already has an appointment at this time");
        }

        // Check for Patient schedule conflict
        boolean patientConflict = appointmentRepository.existsByPatientIdAndAppointmentTime(patientId, appointmentTime);
        if (patientConflict) {
            throw new IllegalArgumentException("Patient already has an appointment at this time");
        }

        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        patient.getAppointments().add(appointment);

        Appointment savedAppointment = appointmentRepository.save(appointment); // save required becz creating appointment for the first Time
        return convertToDto(savedAppointment);
    }

    @Transactional
    public  AppointmentResponseDto reassignAppointmentToAnotherDoctor(Long appointmentId, Long doctorId){
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("No doctore found with id:" + " " + doctorId));
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new EntityNotFoundException("No appointment found with id:" + " " + appointmentId));

        appointment.setDoctor(doctor);
        doctor.getAppointment().add(appointment); // to maintain biDirectionl
        return convertToDto(appointment);
    }

    @Transactional
    public List<AppointmentResponseDto> getAllAppointment() {
        List<Appointment> appointments = appointmentRepository.findAll();

        return appointments.stream().map(appointment -> {
            return AppointmentResponseDto.builder()
                    .id(appointment.getId())
                    .appointmentTime(appointment.getAppointmentTime())
                    .reason(appointment.getReason())
                    .doctor(
                            DoctorAppointmentDto.builder()
                                    .id(appointment.getDoctor().getId())
                                    .name(appointment.getDoctor().getName())
                                    .specilazation(appointment.getDoctor().getSpecilazation())
                                    .build()
                    )
                    .patient(
                            PatientResponseDTO.builder()
                                    .id(appointment.getPatient().getId())
                                    .name(appointment.getPatient().getName())
                                    .email(appointment.getPatient().getEmail())
                                    .birthDate(appointment.getPatient().getBirthDate())
                                    .gender(appointment.getPatient().getGender())
                                    .bloodGroup(appointment.getPatient().getBloodGroup())
                                    .build()
                    )
                    .build();
        }).toList();
    }




    private AppointmentResponseDto convertToDto(Appointment appointment) {
        Doctor doctor = appointment.getDoctor();
        Patient patient = appointment.getPatient();


        // Doctor DTO
        DoctorAppointmentDto doctorDto = DoctorAppointmentDto.builder()
                .id(doctor.getId())
                .name(doctor.getName())
                .specilazation(doctor.getSpecilazation())
                .build();

        InsuranceResponseDto insuranceDto = null;
        if (patient.getInsurance() != null) {
            insuranceDto = InsuranceResponseDto.builder()
                    .id(patient.getInsurance().getId())
                    .provider(patient.getInsurance().getProvider())
                    .polciyNumber(patient.getInsurance().getPolciyNumber())
                    .validUntil(patient.getInsurance().getValidUntil())
                    .build();
        }


        // Patient DTO
        PatientResponseDTO patientDto = PatientResponseDTO.builder()
                .id(patient.getId())
                .name(patient.getName())
                .email(patient.getEmail())
                .insurance(insuranceDto)
                .build();

        // Final Appointment Response DTO
        return AppointmentResponseDto.builder()
                .id(appointment.getId())
                .appointmentTime(appointment.getAppointmentTime())
                .reason(appointment.getReason())
                .doctor(doctorDto)
                .patient(patientDto)
                .build();
    }
}
