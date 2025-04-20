package org.hcltech.doctor_patient_appointment.controllers;

import org.hcltech.doctor_patient_appointment.dtos.admin.AdminDto;
import org.hcltech.doctor_patient_appointment.dtos.authentication.AuthenticationRequestDto;
import org.hcltech.doctor_patient_appointment.dtos.authentication.AuthenticationResponseDto;
import org.hcltech.doctor_patient_appointment.dtos.doctor.DoctorDto;
import org.hcltech.doctor_patient_appointment.dtos.patient.CreatePatientDto;
import org.hcltech.doctor_patient_appointment.dtos.patient.PatientDto;
import org.hcltech.doctor_patient_appointment.services.authentication.AuthenticationService;
import org.hcltech.doctor_patient_appointment.services.AdminService;
import org.hcltech.doctor_patient_appointment.services.DoctorService;
import org.hcltech.doctor_patient_appointment.services.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import jakarta.validation.Valid;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    private final DoctorService doctorService;
    private final PatientService patientService;
    private final AuthenticationService authenticationService;
    private final AdminService adminService;

    public AuthenticationController(DoctorService doctorService,
            AuthenticationService authenticationService,
            AdminService adminService,
            PatientService patientService) {
        this.doctorService = doctorService;
        this.authenticationService = authenticationService;
        this.patientService = patientService;
        this.adminService = adminService;
    }

    // admin
    @PostMapping("/admin/signup")
    public ResponseEntity<AdminDto> createAdmin(
            @RequestBody @Valid AuthenticationRequestDto adminDto) {
        AdminDto admin = adminService.createAdmin(adminDto);

        return ResponseEntity.created(null).body(admin);
    }

    @PostMapping("/admin/login")
    public ResponseEntity<AuthenticationResponseDto> login(
            @RequestBody AuthenticationRequestDto authenticationRequestDto) {
        AuthenticationResponseDto responseBody = authenticationService.login(authenticationRequestDto);

        return ResponseEntity.ok(responseBody);
    }

    // doctor
    // create doctor
    @PostMapping("/doctor/signup")
    public ResponseEntity<DoctorDto> createDoctor(@RequestBody @Valid DoctorDto doctorDto) {
        DoctorDto createdDoctorDto = doctorService.createDoctor(doctorDto);

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/doctor/{id}")
                .buildAndExpand(createdDoctorDto.getId())
                .toUri();

        return ResponseEntity.created(location).body(createdDoctorDto);
    }

    // login doctor
    @PostMapping("/doctor/login")
    public ResponseEntity<AuthenticationResponseDto> loginDoctor(
            @RequestBody AuthenticationRequestDto authenticationRequestDto) {
        System.out.println(authenticationRequestDto);
        AuthenticationResponseDto responseBody = authenticationService.login(authenticationRequestDto);

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    // patient
    // register patient
    @PostMapping("/patient/signup")
    public ResponseEntity<PatientDto> createPatient(@RequestBody @Validated CreatePatientDto patientDto) {
        PatientDto createdPatientDto = patientService.createPatient(patientDto);

        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/v1/patients/{id}")
                .buildAndExpand(createdPatientDto.getId())
                .toUri();

        return ResponseEntity.created(location).body(createdPatientDto);
    }

    @PostMapping("/patient/login")
    public ResponseEntity<AuthenticationResponseDto> loginPatient(
            @RequestBody AuthenticationRequestDto authenticationRequestDto) {
        AuthenticationResponseDto responseBody = authenticationService.login(authenticationRequestDto);

        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }
}
