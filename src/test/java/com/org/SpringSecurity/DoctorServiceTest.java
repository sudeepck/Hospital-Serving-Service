package com.org.SpringSecurity;

import com.hospital.Service.DoctorService;
import com.hospital.dto.Doctor.DoctorRequestDto;
import com.hospital.dto.Doctor.DoctorResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DoctorServiceTest {

    @Autowired
    private DoctorService doctorService;

    @Test
    public void TestAddDoctor() {
        DoctorRequestDto doctor  = DoctorRequestDto.builder()
                .name("Dr. Rakesh Metha")
                .specilazation("cardiology")
                .email("rakesh.metha@gmail.com")
                .build();
        DoctorResponseDto doctorData = doctorService.AddDoctor(doctor);
        System.out.println("doctor: " + " " + doctorData);
    }
}
