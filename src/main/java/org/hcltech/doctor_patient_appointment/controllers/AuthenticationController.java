package org.hcltech.doctor_patient_appointment.controllers;

import org.hcltech.doctor_patient_appointment.dtos.authentication.AuthenticationRequestDto;
import org.hcltech.doctor_patient_appointment.dtos.authentication.AuthenticationResponseDto;
import org.hcltech.doctor_patient_appointment.dtos.doctor.DoctorDto;
import org.hcltech.doctor_patient_appointment.services.authentication.AuthenticationService;
import org.hcltech.doctor_patient_appointment.services.DoctorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final DoctorService doctorService;
    private final AuthenticationService authenticationService;

    public AuthenticationController(DoctorService doctorService, AuthenticationService authenticationService) {
        this.doctorService = doctorService;
        this.authenticationService = authenticationService;
    }

    // admin

    // doctor
    // create doctor
    @PostMapping("/doctor/signup")
    public ResponseEntity<DoctorDto> createDoctor(@RequestBody @Validated DoctorDto doctorDto) {
        DoctorDto createdDoctorDto = doctorService.createDoctor(doctorDto);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdDoctorDto.getId())
                .toUri();



        return ResponseEntity.created(location).body(createdDoctorDto);
    }

    // login doctor
    @PostMapping("/doctor/login")
    public ResponseEntity<AuthenticationResponseDto> login(
            @RequestBody AuthenticationRequestDto authenticationRequestDto) {
        AuthenticationResponseDto responseBody = authenticationService.login(authenticationRequestDto);

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }


    // patient
}
