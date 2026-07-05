package com.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsuranceResponseDto {
    private Long id;
    private String polciyNumber;
    private String provider;
    private LocalDate createdAt;
    private LocalDate validUntil;
}