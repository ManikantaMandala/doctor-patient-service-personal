package org.hcltech.doctor_patient_appointment.controllers;

import org.hcltech.doctor_patient_appointment.dtos.admin.AdminDto;
import org.hcltech.doctor_patient_appointment.dtos.authentication.AuthenticationRequestDto;
import org.hcltech.doctor_patient_appointment.dtos.authentication.AuthenticationResponseDto;
import org.hcltech.doctor_patient_appointment.dtos.doctor.DoctorDto;
import org.hcltech.doctor_patient_appointment.dtos.patient.CreatePatientDto;
import org.hcltech.doctor_patient_appointment.dtos.patient.PatientDto;
import org.hcltech.doctor_patient_appointment.enums.Gender;
import org.hcltech.doctor_patient_appointment.services.AdminService;
import org.hcltech.doctor_patient_appointment.services.DoctorService;
import org.hcltech.doctor_patient_appointment.services.PatientService;
import org.hcltech.doctor_patient_appointment.services.authentication.AuthenticationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

	@Mock
	DoctorService doctorService;
	@Mock
	PatientService patientService;
	@Mock
	AuthenticationService authenticationService;
	@Mock
	AdminService adminService;

	@InjectMocks
	AuthenticationController authenticationController;

	@Test
	void testAdminSignUp() {
		AuthenticationRequestDto adminDto = new AuthenticationRequestDto();
		adminDto.setUsername("test_user_name");
		adminDto.setPassword("test_secret");
		adminDto.setEmail("test_admin_email@example.com");
		adminDto.setRoles(new HashSet<>(Set.of("ADMIN")));

		AdminDto resultAdminDto = new AdminDto();
		resultAdminDto.setId(1L);
		resultAdminDto.setUserName(adminDto.getUsername());
		resultAdminDto.setEmail(adminDto.getEmail());
		resultAdminDto.setRoles(adminDto.getRoles());

		when(adminService.createAdmin(adminDto))
			.thenReturn(resultAdminDto);

		ResponseEntity<AdminDto> admin = authenticationController.createAdmin(adminDto);

		assertNotNull(admin);
		assertEquals(HttpStatus.CREATED, admin.getStatusCode());
		assertEquals(resultAdminDto, admin.getBody());
		verify(adminService, times(1)).createAdmin(adminDto);
	}

	@Test
	void testDoctorSignUp() {
		DoctorDto doctorDto = new DoctorDto();

		doctorDto.setUsername("test_doctor_username");
		doctorDto.setName("test_doctor_name");
		doctorDto.setPassword("test_doctor_password");
		doctorDto.setGender(Gender.FEMALE);
		doctorDto.setSpecialization("test_doctor_specialization");
		doctorDto.setPhoneNumber("1234123412");
		doctorDto.setAddress("test_doctor_address");
		doctorDto.setEmail("test_doctor_email@example.com");
		doctorDto.setRoles(Set.of("DOCTOR"));

		DoctorDto resultDoctorDto = new DoctorDto();

		resultDoctorDto.setId(1L);
		resultDoctorDto.setUsername(doctorDto.getUsername());
		resultDoctorDto.setName(doctorDto.getName());
		resultDoctorDto.setPassword(doctorDto.getPassword());
		resultDoctorDto.setGender(doctorDto.getGender());
		resultDoctorDto.setSpecialization(doctorDto.getSpecialization());
		resultDoctorDto.setPhoneNumber(doctorDto.getPhoneNumber());
		resultDoctorDto.setAddress(doctorDto.getAddress());
		resultDoctorDto.setEmail(doctorDto.getEmail());
		resultDoctorDto.setRoles(doctorDto.getRoles());


		when(doctorService.createDoctor(doctorDto))
			.thenReturn(resultDoctorDto);

		ResponseEntity<DoctorDto> doctor = authenticationController.createDoctor(doctorDto);

		assertNotNull(doctor);
		assertEquals(HttpStatus.CREATED, doctor.getStatusCode());
		assertEquals(resultDoctorDto, doctor.getBody());
		verify(doctorService, times(1)).createDoctor(doctorDto);
	}

	@Test
	void testPatientSignUp() {
		CreatePatientDto createPatientDto = new CreatePatientDto();
		createPatientDto.setFirstName("test_first_name");
		createPatientDto.setLastName("test_last_name");
		createPatientDto.setEmail("test_patient_email@example.com");
		createPatientDto.setUsername("test_patient_username");
		createPatientDto.setPassword("test_patient_password");
		createPatientDto.setAge(23);
		createPatientDto.setGender(Gender.FEMALE);
		createPatientDto.setPhoneNumber("9876543210");
		createPatientDto.setAddress("test_patient_address");

		PatientDto resultPatientDto = new PatientDto();
		resultPatientDto.setId(1L);
		resultPatientDto.setFirstName(createPatientDto.getFirstName());
		resultPatientDto.setLastName(createPatientDto.getLastName());
		resultPatientDto.setFullName(createPatientDto.getFirstName() + " " + createPatientDto.getLastName());
		resultPatientDto.setEmail(createPatientDto.getEmail());
		resultPatientDto.setUsername(createPatientDto.getUsername());
		resultPatientDto.setAge(createPatientDto.getAge());
		resultPatientDto.setGender(createPatientDto.getGender());
		resultPatientDto.setPhoneNumber(createPatientDto.getPhoneNumber());
		resultPatientDto.setAddress(createPatientDto.getAddress());
		resultPatientDto.setDoctorId(null);

		when(patientService.createPatient(createPatientDto)).thenReturn(resultPatientDto);

		ResponseEntity<PatientDto> response = authenticationController.createPatient(createPatientDto);

		assertNotNull(response);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(resultPatientDto, response.getBody());

		verify(patientService, times(1)).createPatient(createPatientDto);
	}

	@Test
	void testAdminLogin() {
		AuthenticationRequestDto requestDto = new AuthenticationRequestDto();
		requestDto.setUsername("test_admin_username");
		requestDto.setPassword("test_admin_password");
		requestDto.setEmail("test_admin_email@example.com");
		requestDto.setRoles(Set.of("ADMIN"));

		AuthenticationResponseDto responseDto = new AuthenticationResponseDto();
		responseDto.setAccessToken("test-admin-dummy-token");
		responseDto.setExpiresIn(
				new Date(System.currentTimeMillis() + 2 * 3600 * 1000));
		responseDto.setPrinciple(requestDto.getUsername());

		when(authenticationService.login(requestDto)).thenReturn(responseDto);

		ResponseEntity<AuthenticationResponseDto> 
			response = authenticationController.login(requestDto);

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(responseDto, response.getBody());

		verify(authenticationService, times(1)).login(requestDto);
	}

	@Test
	void testDoctorLogin() {
		AuthenticationRequestDto requestDto = new AuthenticationRequestDto();
		requestDto.setUsername("test_doctor_user");
		requestDto.setPassword("test_doctor_password");
		requestDto.setEmail("test_doctor_email@example.com");
		requestDto.setRoles(Set.of("DOCTOR"));

		AuthenticationResponseDto expectedResponse = new AuthenticationResponseDto();
		expectedResponse.setAccessToken("test-doctor-dummy-token");
		expectedResponse.setExpiresIn(
				new Date(System.currentTimeMillis() + 2 * 3600 * 1000));
		expectedResponse.setPrinciple(requestDto.getUsername());

		when(authenticationService.login(requestDto))
			.thenReturn(expectedResponse);

		ResponseEntity<AuthenticationResponseDto> 
			response = authenticationController.loginDoctor(requestDto);

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(expectedResponse, response.getBody());

		verify(authenticationService, times(1)).login(requestDto);
	}

	@Test
	void testPatientLogin() {
		AuthenticationRequestDto requestDto = new AuthenticationRequestDto();
		requestDto.setUsername("test_patient_user");
		requestDto.setPassword("test_patient_password");
		requestDto.setEmail("test_patient_email@example.com");
		requestDto.setRoles(Set.of("PATIENT"));

		AuthenticationResponseDto expectedResponse = 
			new AuthenticationResponseDto();
		expectedResponse.setAccessToken("test-patient-dummy-token");
		expectedResponse.setExpiresIn(
				new Date(System.currentTimeMillis() + 2 * 3600 * 1000));
		expectedResponse.setPrinciple(requestDto.getUsername());

		when(authenticationService.login(requestDto))
			.thenReturn(expectedResponse);

		ResponseEntity<AuthenticationResponseDto> 
			response = authenticationController.loginPatient(requestDto);

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(expectedResponse, response.getBody());

		verify(authenticationService, times(1)).login(requestDto);
	}
}
