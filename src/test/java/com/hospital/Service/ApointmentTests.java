package com.hospital.Service;

import com.hospital.Service.dto.AppointmentResponseDto;
import com.hospital.Service.dto.CreateAppointmentRequestDto;
import com.hospital.Service.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
@RequiredArgsConstructor
public class ApointmentTests {

    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void testCreateAppointment() {
        CreateAppointmentRequestDto request = new CreateAppointmentRequestDto();
        request.setPatientId(1L);
        request.setDoctorId(1L);
        request.setReason("Routine checkup");
        request.setAppointmentTime(LocalDateTime.of(2026, 7, 5, 10, 30));

        AppointmentResponseDto response = appointmentService.createNewAppointment(request);
        appointmentService.reAssignAppointmentToAnotherDoctor(response.getId(), 3L);

        System.out.println(response);
    }

}
