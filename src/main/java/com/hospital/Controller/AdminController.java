package com.hospital.Controller;

import com.hospital.Entity.Patient;
import com.hospital.Service.PatientService;
import com.hospital.dto.Patient.PatientResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/admin")
@RestController
@AllArgsConstructor
public class AdminController {

     @Autowired
    private  final PatientService patientService;

    @GetMapping("/patients")
    public List<Patient> getAllPatients(){
        return patientService.fetchAllPatient();
    }

}
