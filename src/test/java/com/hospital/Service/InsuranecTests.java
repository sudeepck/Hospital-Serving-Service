package com.hospital.Service;

import com.hospital.Service.entity.Insurance;
import com.hospital.Service.entity.Patient;
import com.hospital.Service.service.InsuranceService;
import jakarta.persistence.Access;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

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

        System.out.println(patient);
    }
}
