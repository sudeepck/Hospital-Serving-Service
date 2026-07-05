package com.hospital.dto;

import com.hospital.Entity.Type.BloodGroupType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BloodGroupCountReponseEntity {
    private BloodGroupType bloodGroupType;
    private long count;
}
