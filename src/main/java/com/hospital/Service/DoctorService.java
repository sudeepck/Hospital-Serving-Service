package com.hospital.Service;

import com.hospital.Entity.Doctor;
import com.hospital.Repository.DoctorRepository;
import com.hospital.dto.Doctor.DoctorRequestDto;
import com.hospital.dto.Doctor.DoctorResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoctorService {
    private  final DoctorRepository doctorRepository;

    public DoctorResponseDto AddDoctor(DoctorRequestDto doctorDto){
        Doctor doctor = Doctor.builder()
                .name(doctorDto.getName())
                .email(doctorDto.getEmail())
                .specilazation(doctorDto.getSpecilazation())
                .build();

        Doctor savedDoctor = doctorRepository.save(doctor);

        return DoctorResponseDto.builder()
                .id(savedDoctor.getId())
                .name(savedDoctor.getName())
                .email(savedDoctor.getEmail())
                .specilazation(savedDoctor.getSpecilazation())
                .build();
    }

    public DoctorResponseDto getDoctorById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));

        return DoctorResponseDto.builder()
                .id(doctor.getId())
                .name(doctor.getName())
                .email(doctor.getEmail())
                .specilazation(doctor.getSpecilazation())
                .build();
    }
}
