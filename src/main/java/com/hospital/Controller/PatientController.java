package com.hospital.Controller;

import com.hospital.Entity.Patient;
import com.hospital.Service.PatientService;
import com.hospital.dto.Patient.PatientRequestDto;
import com.hospital.dto.Patient.PatientResponseDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/patient")
@Slf4j
public class PatientController {
    @Autowired
    private  final PatientService patientService;

    @GetMapping("/{id}")
    public PatientResponseDTO getPatientById(@PathVariable Long id){
        System.out.println("id values is :" + "=" + id);
        return  patientService.getPatientById(id);
    }

    @GetMapping("/")
    public List<Patient> fetchAllPatient(){
        return  patientService.fetchAllPatient();
    }

    @PostMapping("/AddPatient")
    public Patient AddPatient(@RequestBody PatientRequestDto patientResponseDTO){
        return  patientService.AddPatient(patientResponseDTO);
    }

    @DeleteMapping("/{id}")
    public String deletePatientById(@PathVariable Long id){
        System.out.println("id values is :" + "=" + id);
        return  patientService.DeletepatientById(id);
    }

    @GetMapping("/dummy/{name}")
    public PatientResponseDTO getPatientByName(@PathVariable String  name){
        System.out.println("id values is :" + "=" + name);
        return  patientService.getPatientByName(name);
    }
}
