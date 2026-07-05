package com.hospital.Service;

import com.hospital.Service.dto.AppointmentResponseDto;
import com.hospital.Service.dto.CreateAppointmentRequestDto;
import com.hospital.Service.dto.PatientResponseDto;
import com.hospital.Service.entity.Patient;
import com.hospital.Service.service.AppointmentService;
import com.hospital.Service.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@RequiredArgsConstructor
public class ApointmentTests {

    @Autowired
    private AppointmentService appointmentService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PatientService patientService;

    @Test
    public void testCreateAppointment() {
        CreateAppointmentRequestDto request = new CreateAppointmentRequestDto();
        request.setPatientId(1L);
        request.setDoctorId(1L);
        request.setReason("Routine checkup");
        request.setAppointmentTime(LocalDateTime.of(2026, 7, 5, 10, 30));

        CreateAppointmentRequestDto req1 = CreateAppointmentRequestDto.builder()
                .patientId(2L)
                .doctorId(2L)
                .reason("testing")
                .appointmentTime(LocalDateTime.of(2026, 7, 5, 20, 30))
                .build();

        CreateAppointmentRequestDto req2 = CreateAppointmentRequestDto.builder()
                .patientId(2L)
                .doctorId(2L)
                .reason("testing")
                .appointmentTime(LocalDateTime.of(2026, 7, 5, 20, 30))
                .build();


        AppointmentResponseDto response = appointmentService.createNewAppointment(request);
        appointmentService.reAssignAppointmentToAnotherDoctor(response.getId(), 3L);

        System.out.println(response);
    }

    @Test
    public  void testOrphanCsde(){
//        CreateAppointmentRequestDto req = CreateAppointmentRequestDto.builder()
//                .patientId(3L)
//                .doctorId(2L)
//                .reason("testing1")
//                .appointmentTime(LocalDateTime.of(2026, 7, 5, 20, 30))
//                .build();
//
//        CreateAppointmentRequestDto req1 = CreateAppointmentRequestDto.builder()
//                .patientId(3L)
//                .doctorId(2L)
//                .reason("testing2")
//                .appointmentTime(LocalDateTime.of(2026, 7, 5, 20, 30))
//                .build();
//
//        CreateAppointmentRequestDto req2 = CreateAppointmentRequestDto.builder()
//                .patientId(3L)
//                .doctorId(2L)
//                .reason("testing3")
//                .appointmentTime(LocalDateTime.of(2026, 7, 5, 20, 30))
//                .build();
//
//
//
//        AppointmentResponseDto response = appointmentService.createNewAppointment(req);
//        AppointmentResponseDto response1 = appointmentService.createNewAppointment(req1);
//        AppointmentResponseDto response2 = appointmentService.createNewAppointment(req2);
//
//        System.out.println(response);
//        System.out.println(response1);
//        System.out.println(response2);
        List<Patient> patientResponseDto = patientService.getAllPatients();
        for(Patient p : patientResponseDto)
         System.out.println(p);
    }

}
