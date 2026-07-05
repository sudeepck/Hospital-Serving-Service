package com.hospital.Service;

import com.hospital.Service.dto.AppointmentResponseDto;
import com.hospital.Service.dto.CreateAppointmentRequestDto;
import com.hospital.Service.entity.Insurance;
import com.hospital.Service.entity.Patient;
import com.hospital.Service.service.InsuranceService;
import jakarta.persistence.Access;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;

@SpringBootTest
@RequiredArgsConstructor
public class InsuranecTests {

    @Autowired
    private InsuranceService insuranceService;

    @Test
    public  void testInsurance(){
        Insurance insurance = Insurance.builder()
                .policyNumber("HDFC_1234")
                .provider("HDFC")
                .validUntil(LocalDate.of(2030, 12, 12))
                .build();

        Patient patient = insuranceService.assignInsuranceToPatient(insurance, 1L);

//        Insurance insurance2 = Insurance.builder()
//                .policyNumber("IDFC_1234")
//                .provider("IDFC")
//                .validUntil(LocalDate.of(2030, 12, 12))
//                .build();
//        Patient patient2 = insuranceService.assignInsuranceToPatient(insurance2, 2L);
//
//
//        System.out.println(patient);

//        var newPatient = insuranceService.disaccociateInsuranceFromPatient(patient.getId());

//        System.out.println(newPatient);
    }

}
