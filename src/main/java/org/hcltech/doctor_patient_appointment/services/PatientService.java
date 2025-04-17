package org.hcltech.doctor_patient_appointment.services;

import java.util.Objects;
import java.util.Optional;

import org.hcltech.doctor_patient_appointment.daos.services.DoctorDaoService;
import org.hcltech.doctor_patient_appointment.daos.services.PatientDaoService;
import org.hcltech.doctor_patient_appointment.dtos.patient.CreatePatientDto;
import org.hcltech.doctor_patient_appointment.dtos.patient.PatientDto;
import org.hcltech.doctor_patient_appointment.dtos.patient.UpdatePatientDto;
import org.hcltech.doctor_patient_appointment.exceptions.DifferentUserShouldBeSameException;
import org.hcltech.doctor_patient_appointment.exceptions.DoctorCanNotHaveMorePatientsException;
import org.hcltech.doctor_patient_appointment.exceptions.DoctorNotAssignedToPatientException;
import org.hcltech.doctor_patient_appointment.exceptions.DoctorShouldBeAllocatedToPatientException;
import org.hcltech.doctor_patient_appointment.exceptions.EmailShouldBeUniqueException;
import org.hcltech.doctor_patient_appointment.exceptions.EntryShouldBeUniqueException;
import org.hcltech.doctor_patient_appointment.exceptions.PatientIdNotMatchingWithPatientUserName;
import org.hcltech.doctor_patient_appointment.exceptions.PatientNotFoundUsingUserNameException;
import org.hcltech.doctor_patient_appointment.exceptions.PatientUsernameShouldMatchWithTheId;
import org.hcltech.doctor_patient_appointment.exceptions.RecordNotFoundInDbException;
import org.hcltech.doctor_patient_appointment.exceptions.UsernameShouldBeUniqueException;
import org.hcltech.doctor_patient_appointment.mapper.PatientMapper;
import org.hcltech.doctor_patient_appointment.models.Doctor;
import org.hcltech.doctor_patient_appointment.models.Patient;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;

@Service
public class PatientService {

	private final PatientDaoService patientDaoService;
	private final DoctorDaoService doctorDaoService;
	private final PatientMapper patientMapper;

	public PatientService(PatientDaoService patientDaoService, PatientMapper patientMapper,
			DoctorDaoService doctorDaoService) {
		this.patientDaoService = patientDaoService;
		this.patientMapper = patientMapper;
		this.doctorDaoService = doctorDaoService;
	}

	public PatientDto createPatient(CreatePatientDto createPatientDto) {
		Patient patient = patientMapper.toEntityFromCreatePatientDto(createPatientDto);

		if (checkUsernameIfExists(patient.getUserName())) {
			throw new UsernameShouldBeUniqueException("username should be unique");
		}
		if (checkEmailIfExists(patient.getEmail())) {
			throw new EmailShouldBeUniqueException("email should be unique");
		}
		Patient createdPatient = patientDaoService.savePatient(patient);
		return patientMapper.toDto(createdPatient);
	}

	private boolean checkEmailIfExists(String email) {
		if (!patientDaoService.checkIfExistByEmail(email)) {
			return false;
		}
		return true;
	}

	private boolean checkUsernameIfExists(String userName) {
		if (!patientDaoService.checkIfExistByUsername(userName)) {
			return false;
		}
		return true;
	}

	public void deletePatient(Long id) {
		patientDaoService.deleteById(id);
	}

	public void allocatePatientToDoctor(Long doctorId, Long patientId) {
		Patient patient = patientDaoService.getPatientById(patientId);
		Doctor doctor = doctorDaoService.getDoctorById(doctorId);

		if (doctor == null || patient == null) {
			throw new RecordNotFoundInDbException(
					"not found", doctorId == null ? "DOCTOR" : "PATIENT");
		}

		if (doctor.getPatients().size() > 3) {
			throw new DoctorCanNotHaveMorePatientsException(
					"doctor can't have more than 4 patients");
		}

		patient.setDoctor(doctor);

		patientDaoService.savePatient(patient);
	}

	public void deallocatePatientFromDoctor(Long doctorId, Long patientId) {
		Patient patientById = patientDaoService.getPatientById(patientId);
		Doctor doctorById = doctorDaoService.getDoctorById(doctorId);

		if (doctorById == null || patientById == null) {
			throw new RecordNotFoundInDbException(
					"not found", doctorId == null ? "DOCTOR" : "PATIENT");
		}

		if (patientById.getDoctor() != doctorById) {
			throw new DoctorNotAssignedToPatientException(
					"patient is not allocated to this doctor");
		}

		patientById.setDoctor(null);
		patientDaoService.savePatient(patientById);

		// TODO: check this
		// doctorById.getPatients().remove(patientById);
		// doctorDaoService.saveDoctor(doctorById);
	}

	public PatientDto getPatientById(Long id) {
		Patient patientById = patientDaoService.getPatientById(id);

		if (patientById == null) {
			throw new RecordNotFoundInDbException("patient not found");
		}

		return patientMapper.toDto(patientById);
	}

	public PatientDto getPatientByIdIfSamePatient(@Valid Long id,
			@Valid String username) {
		Patient patientById = patientDaoService.getPatientById(id);

		if (patientById == null) {
			throw new RecordNotFoundInDbException("patient not found");
		}

		if (patientById.getUser().getUserName() != username) {
			throw new DifferentUserShouldBeSameException("bad request, different user");
		}

		return patientMapper.toDto(patientById);
	}

	public void updatePatientDetails(Long id, UpdatePatientDto patientDto) {
		Patient patient = patientMapper.toEntityFromUpdatePatientDto(patientDto);

		if (checkUsernameIfExists(patient.getUserName())) {
			throw new EntryShouldBeUniqueException("username should be unique");
		}

		if (checkEmailIfExists(patient.getEmail())) {
			throw new EntryShouldBeUniqueException("email should be unique");
		}

		patientDaoService.updatePatient(id, patient);
	}

	public void updatePatientDetailsIfSameDoctor(Long id,
			UpdatePatientDto patientDto, Long doctorId) {
		Patient patient = patientMapper.toEntityFromUpdatePatientDto(patientDto);

		Patient patientById = patientDaoService.getPatientById(id);

		if (checkUsernameIfExists(patient.getUser().getUserName(), patientById)) {
			throw new EntryShouldBeUniqueException("username should be unique");
		}
		if (checkEmailIfExists(patient.getUser().getEmail(), patientById)) {
			throw new EntryShouldBeUniqueException("email should be unique");
		}
		if (!checkSameDoctorForPatient(patientById, doctorId)) {
			throw new DoctorShouldBeAllocatedToPatientException(
					"doctor should be allocated to update the patient details");
		}

		patientDaoService.updatePatient(id, patient);

	}

	private boolean checkEmailIfExists(String email, Patient patient) {
		if (!Objects.equals(patient.getUser().getEmail(), email)) {
			return false;
		}
		return true;
	}

	private boolean checkUsernameIfExists(String username, Patient patient) {
		if (!Objects.equals(patient.getUser().getUserName(), username)) {
			return false;
		}
		return true;
	}

	private boolean checkSameDoctorForPatient(Patient patient, Long doctorId) {
		if (patient.getDoctor().getId() != doctorId) {
			return false;
		}
		return true;
	}

	private boolean checkSamePatient(Patient patient, String userName) {
		if (!Objects.equals(patient.getDoctor().getUser().getUserName(), userName)) {
			return false;
		}
		return true;
	}

	public void updatePatientDetailsIfSamePatient(Long id,
			UpdatePatientDto patientDto, String patientUserName) {
		Patient patient = patientMapper.toEntityFromUpdatePatientDto(patientDto);

		Patient patientById = patientDaoService.getPatientById(id);

		if (checkUsernameIfExists(patient.getUser().getUserName(), patientById)) {
			throw new EntryShouldBeUniqueException("username should be unique");
		}
		if (checkEmailIfExists(patient.getUser().getEmail(), patientById)) {
			throw new EntryShouldBeUniqueException("email should be unique");
		}
		if (!checkSamePatient(patientById, patientUserName)) {
			throw new PatientUsernameShouldMatchWithTheId(
					"doctor should be allocated to update the patient details");
		}

		patientDaoService.updatePatient(id, patient);
	}

	public void allocatePatientToDoctorIfSamePatient(Long doctorId,
			Long patientId, String patientUserName) {

		Patient patient = patientDaoService.getPatientById(patientId);
		Doctor doctor = doctorDaoService.getDoctorById(doctorId);

		Optional<Patient> patientByUsername = patientDaoService.getPatientByUsername(patientUserName);

		if (patientByUsername.isEmpty()) {
			throw new PatientNotFoundUsingUserNameException("patient not found using username");
		}

		if (patient.getId() != patientByUsername.get().getId()) {
			throw new PatientIdNotMatchingWithPatientUserName("patient id is not matching with patient user name");
		}

		if (doctor == null || patient == null) {
			throw new RecordNotFoundInDbException(
					"not found", doctor == null ? "DOCTOR" : "PATIENT");
		}

		if (doctor.getPatients().size() > 3) {
			throw new DoctorCanNotHaveMorePatientsException(
					"doctor can't have more than 4 patients");
		}

		patient.setDoctor(doctor);

		patientDaoService.savePatient(patient);
	}

	public void deallocatePatientFromDoctorIfSamePatient(Long doctorId,
			Long patientId, String patientUserName) {
		Patient patientById = patientDaoService.getPatientById(patientId);
		Doctor doctorById = doctorDaoService.getDoctorById(doctorId);

		Optional<Patient> patientByUsername = patientDaoService
				.getPatientByUsername(patientUserName);

		if (patientByUsername.isEmpty()) {
			throw new PatientNotFoundUsingUserNameException("patient not found using username");
		}

		if (patientById.getId() != patientByUsername.get().getId()) {
			throw new PatientIdNotMatchingWithPatientUserName("patient id is not matching with patient user name");
		}

		if (doctorById == null || patientById == null) {
			throw new RecordNotFoundInDbException(
					"not found", doctorById == null ? "DOCTOR" : "PATIENT");
		}

		if (doctorById.getPatients().size() > 3) {
			throw new DoctorCanNotHaveMorePatientsException(
					"doctor can't have more than 4 patients");
		}

		patientById.setDoctor(null);
		patientDaoService.savePatient(patientById);
	}
}
