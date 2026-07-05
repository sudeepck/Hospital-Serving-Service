package com.org.SpringSecurity;

import com.hospital.Entity.Patient;
import com.hospital.Service.PatientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PatientServicetTest {

    @Autowired
    private PatientService patientService;

    @Test
    public void TestPatientRepository() {
        List<Patient> patients = patientService.fetchAllPatient();
        System.out.println(patients);
    }
}
