package org.hcltech.doctor_patient_appointment.services;

import org.hcltech.doctor_patient_appointment.daos.services.DoctorDaoService;
import org.hcltech.doctor_patient_appointment.dtos.doctor.DoctorDto;
import org.hcltech.doctor_patient_appointment.dtos.doctor.PatientDoctorDto;
import org.hcltech.doctor_patient_appointment.dtos.patient.DoctorPatientDto;
import org.hcltech.doctor_patient_appointment.exceptions.EntryShouldBeUniqueException;
import org.hcltech.doctor_patient_appointment.exceptions.IdNotFoundException;
import org.hcltech.doctor_patient_appointment.exceptions.RecordNotFoundInDbException;
import org.hcltech.doctor_patient_appointment.mapper.DoctorMapper;
import org.hcltech.doctor_patient_appointment.models.Doctor;
import org.hcltech.doctor_patient_appointment.models.Patient;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

	private final PasswordEncoder passwordEncoder;
	private final DoctorDaoService doctorDaoService;
	private final DoctorMapper doctorMapper;

	public DoctorService(
			DoctorDaoService doctorDaoService,
			DoctorMapper doctorMapper,
			PasswordEncoder passwordEncoder) {
		this.doctorDaoService = doctorDaoService;
		this.doctorMapper = doctorMapper;
		this.passwordEncoder = passwordEncoder;
	}

	/**
	 * 
	 */
	public DoctorDto createDoctor(DoctorDto doctorDto) {
		Doctor doctor = doctorMapper.toEntity(doctorDto);

		doctor.setPassword(passwordEncoder.encode(doctor.getPassword()));

		// check username and email
		// should be unique
		if (checkUsernameIfExists(doctor.getUserName())) {
			throw new EntryShouldBeUniqueException("username should be unique");
		}
		if (checkEmailIfExists(doctor.getEmail())) {
			throw new EntryShouldBeUniqueException("username should be unique");
		}

		Doctor createdDoctor = doctorDaoService.saveDoctor(doctor);

		return doctorMapper.toDto(createdDoctor);
	}

	/**
	 *
	 */
	private boolean checkEmailIfExists(String email) {
		return doctorDaoService.checkIfExistByEmail(email);
	}

	/**
	 *
	 */
	private boolean checkUsernameIfExists(String username) {
		return doctorDaoService.checkIfExistByUsername(username);
	}

	/**
	 *
	 */
	public List<PatientDoctorDto> getDoctorsList() {
		return doctorDaoService.getAllDoctors()
				.stream()
				.map(doctorMapper::toPatientDoctorDto)
				.toList();
	}

	/**
	 *
	 */
	public DoctorDto getDoctorById(Long doctorId) {
		Doctor doctorById = doctorDaoService.getDoctorById(doctorId);

		if (doctorById == null) {
			throw new RecordNotFoundInDbException("no doctor with that id");
		}

		return doctorMapper.toDto(doctorById);
	}

	/**
	 *
	 */
	public DoctorDto updateDoctorDetails(Long id, DoctorDto doctorDto) {
		// Didn't use the toEntityAfterCreating because we are checking in
		// getAndUpdateById
		Doctor doctor = doctorMapper.toEntity(doctorDto);
		Optional<Doctor> optionalUpdatedDoctor = doctorDaoService.getAndUpdateById(id, doctor);

		if (optionalUpdatedDoctor.isEmpty()) {
			throw new RecordNotFoundInDbException("User not found");
		}

		return doctorMapper.toDto(optionalUpdatedDoctor.get());
	}

	/**
	 *
	 */
	public void deleteDoctorById(Long id) {
		doctorDaoService.deleteDoctor(id);
	}

	/**
	 *
	 */
	public List<DoctorPatientDto> getPatientsByDoctorId(Long doctorId) {
		if (doctorId == null) {
			throw new IdNotFoundException("no doctor with that id");
		}

		Doctor doctorById = doctorDaoService.getDoctorById(doctorId);
		List<Patient> patients = doctorById.getPatients();

		return patients.stream()
				.map(doctorMapper::toDoctorPatientDto)
				.toList();
	}
}
