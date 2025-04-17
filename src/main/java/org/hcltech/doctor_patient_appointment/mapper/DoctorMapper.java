package org.hcltech.doctor_patient_appointment.mapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.hcltech.doctor_patient_appointment.dtos.doctor.DoctorDto;
import org.hcltech.doctor_patient_appointment.dtos.doctor.PatientDoctorDto;
import org.hcltech.doctor_patient_appointment.dtos.patient.DoctorPatientDto;
import org.hcltech.doctor_patient_appointment.models.Doctor;
import org.hcltech.doctor_patient_appointment.models.Patient;
import org.springframework.stereotype.Service;

@Service
public class DoctorMapper {

	private final PatientMapper patientMapper;

	public DoctorMapper(PatientMapper patientMapper) {
		this.patientMapper = patientMapper;
	}

	public DoctorDto toDto(Doctor doctor) {
		DoctorDto doctorDto = new DoctorDto();

		doctorDto.setId(doctor.getId());
		doctorDto.setEmail(doctor.getEmail());
		doctorDto.setName(doctor.getName());
		doctorDto.setGender(doctor.getGender());
		doctorDto.setSpecialization(doctor.getSpecialization());
		doctorDto.setPhoneNumber(doctor.getPhoneNumber());
		doctorDto.setAddress(doctor.getAddress());
		doctorDto.setUsername(doctor.getUserName());
		doctorDto.setRoles(doctor.getRoles());

		if (doctor.getPatients() == null) {
			doctor.setPatients(new ArrayList<>());
		} else {
			List<Long> patientDtos = doctor.getPatients()
					.stream()
					.map(patient -> patient.getId())
					.toList();
			doctorDto.setPatientDtos(patientDtos);
		}

		return doctorDto;
	}

	public PatientDoctorDto toPatientDoctorDto(Doctor doctor) {
		PatientDoctorDto patientDoctorDto = new PatientDoctorDto();

		patientDoctorDto.setName(doctor.getName());
		patientDoctorDto.setGender(doctor.getGender());
		patientDoctorDto.setSpecialization(doctor.getSpecialization());
		patientDoctorDto.setEmail(doctor.getEmail());
		patientDoctorDto.setUsername(doctor.getUserName());
		patientDoctorDto.setAddress(doctor.getAddress());
		patientDoctorDto.setPhoneNumber(doctor.getPhoneNumber());
		patientDoctorDto.setAvailable(doctor.getPatients().size() < 4);

		return patientDoctorDto;
	}

	public Doctor toEntity(DoctorDto doctorDto) {
		Doctor doctor = new Doctor();

		doctor.setUserName(doctorDto.getUsername());
		doctor.setEmail(doctorDto.getEmail());
		doctor.setName(doctorDto.getName());
		doctor.setAddress(doctorDto.getAddress());
		doctor.setGender(doctorDto.getGender());
		doctor.setSpecialization(doctorDto.getSpecialization());
		doctor.setPhoneNumber(doctorDto.getPhoneNumber());
		doctor.setPassword(doctorDto.getPassword());
		doctor.setRoles(doctorDto.getRoles() == null ? 
		new HashSet<>(Collections.singleton("DOCTOR")) : doctorDto.getRoles());

		return doctor;
	}

	public DoctorPatientDto toDoctorPatientDto(Patient patient) {
		DoctorPatientDto doctorPatientDto = new DoctorPatientDto();

		doctorPatientDto.setId(patient.getId());
		doctorPatientDto.setFirstName(patient.getFirstName());
		doctorPatientDto.setLastName(patient.getLastName());
		doctorPatientDto.setUsername(patient.getUserName());
		doctorPatientDto.setEmail(patient.getEmail());
		doctorPatientDto.setPhoneNumber(patient.getPhoneNumber());
		doctorPatientDto.setGender(patient.getGender());
		doctorPatientDto.setAddress(patient.getAddress());
		doctorPatientDto.setAge(patient.getAge());

		return doctorPatientDto;
	}

}
