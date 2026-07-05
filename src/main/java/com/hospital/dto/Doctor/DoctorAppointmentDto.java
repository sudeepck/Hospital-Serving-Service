package com.hospital.dto.Doctor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DoctorAppointmentDto {
    private Long id;
    private String name;
    private String specilazation;
    private String email;
    private String phone;
}
