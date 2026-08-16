package com.hospital.Service;

import com.hospital.Entity.Insurance;
import com.hospital.Entity.Patient;
import com.hospital.Repository.PatientsRepository;
import com.hospital.dto.InsuranceResponseDto;
import com.hospital.dto.Patient.PatientRequestDto;
import com.hospital.dto.Patient.PatientResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PatientService {

    private final PatientsRepository patientsRepository;

    public List<Patient> fetchAllPatient(){
        return  patientsRepository.findAllpatientsWithAppointents();
    }

    public PatientResponseDTO getPatientById(Long id){
        Patient patient = patientsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        Insurance patientInsurance = patient.getInsurance();
        InsuranceResponseDto insuranceDto = (patientInsurance != null)
                ? getInsurance(patientInsurance)
                : null;

        return  PatientResponseDTO.builder()
                .id(patient.getId())
                .name(patient.getName())
                .birthDate(patient.getBirthDate())
                .email(patient.getEmail())
                .gender(patient.getGender())
                .bloodGroup(patient.getBloodGroup())
                .insurance(insuranceDto)
                .build();
    }

    public PatientResponseDTO getPatientByName(String name) {
        Patient patient = patientsRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        Insurance patientInsurance = patient.getInsurance();

        InsuranceResponseDto insuranceDto = (patientInsurance != null)
                ? getInsurance(patientInsurance)
                : null;


        return  PatientResponseDTO.builder()
                .id(patient.getId())
                .name(patient.getName())
                .birthDate(patient.getBirthDate())
                .email(patient.getEmail())
                .gender(patient.getGender())
                .bloodGroup(patient.getBloodGroup())
                .insurance(insuranceDto)
                .build();
    }

    public  String DeletepatientById(Long patientID){
        patientsRepository.deleteById(patientID);
        return "${patientID} succefully deleted";
    }

    public Patient AddPatient(PatientRequestDto dto) {
        return patientsRepository.save(
                Patient.builder()
                        .name(dto.getName())
                        .email(dto.getEmail())
                        .birthDate(dto.getBirthDate()) // Already LocalDate
                        .gender(dto.getGender())
                        .bloodGroup(dto.getBloodGroup())
                        .build()
        );
    }

    public InsuranceResponseDto getInsurance(Insurance patientInsurance){
      return InsuranceResponseDto.builder()
                .id(patientInsurance.getId())
                .polciyNumber(patientInsurance.getPolciyNumber())
                .provider(patientInsurance.getProvider())
                .createdAt(patientInsurance.getCreatedAt())
                .validUntil(patientInsurance.getValidUntil())
                .build();
    }
}
