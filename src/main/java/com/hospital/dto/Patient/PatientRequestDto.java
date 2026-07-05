package com.hospital.dto.Patient;

import com.hospital.Entity.Type.BloodGroupType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class PatientRequestDto {
    private String name;
    private LocalDate birthDate;
    private String email;
    private String gender;
    private BloodGroupType bloodGroup;
}
