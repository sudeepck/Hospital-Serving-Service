package com.hospital.Controller;

import com.hospital.Service.DoctorService;
import com.hospital.dto.Doctor.DoctorRequestDto;
import com.hospital.dto.Doctor.DoctorResponseDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/public/doctor")
public class DoctorController {

    @Autowired
    private final DoctorService doctorService;

    @GetMapping("/{id}")
    public DoctorResponseDto getDoctorById(@PathVariable Long id){
        System.out.println("id values is :" + "=" + id);
        return  doctorService.getDoctorById(id);
    }

    @PostMapping("/AddDoctor")
    public DoctorResponseDto AddDoctor(@RequestBody DoctorRequestDto doctorRequestDto){
        return  doctorService.AddDoctor(doctorRequestDto);
    }
}
