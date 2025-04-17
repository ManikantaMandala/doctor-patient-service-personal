package org.hcltech.doctor_patient_appointment.daos.services;

import org.hcltech.doctor_patient_appointment.exceptions.IdNotFoundException;
import org.hcltech.doctor_patient_appointment.exceptions.PatientIdNotMatchingWithPatientUserName;
import org.hcltech.doctor_patient_appointment.exceptions.RecordNotFoundInDbException;
import org.hcltech.doctor_patient_appointment.models.Patient;
import org.hcltech.doctor_patient_appointment.repositories.PatientRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PatientDaoService {

	private final PatientRepository patientRepository;

	public PatientDaoService(PatientRepository patientRepository) {
		this.patientRepository = patientRepository;
	}

	public List<Patient> get() {
		return patientRepository.findAll();
	}

	public Patient getPatientById(Long id) {
		if (id == null) {
			throw new IdNotFoundException("bad request, id is null");
		}

		return patientRepository.findById(id).orElse(null);
	}

	public Optional<Patient> getPatientByUsername(String username) {
		return patientRepository.findByUserName(username);
	}

	public Patient savePatient(Patient patient) {
		if (patient == null) {
			throw new IdNotFoundException("bad request, body is null");
		}

		return patientRepository.save(patient);
	}

	public boolean checkIfExistByEmail(String email) {
		if (email == null) {
			throw new IdNotFoundException("bad request, username is null");
		}

		return patientRepository.existsByEmail(email);
	}

	public boolean checkIfExistByUsername(String username) {
		if (username == null) {
			throw new IdNotFoundException("bad request, username is null");
		}

		return patientRepository.existsByUserName(username);
	}

	public void deleteById(Long id) {
		if (id == null) {
			throw new IdNotFoundException("bad request, id is null");
		}

		patientRepository.deleteById(id);
	}

	public void deleteById(Long id, String userName) {
		if (id == null) {
			throw new IdNotFoundException("bad request, id is null");
		}
		if (userName == null) {
			throw new UsernameNotFoundException("bad request, username is null");
		}

		Patient patientById = getPatientById(id);
		Optional<Patient> patientByUsername = getPatientByUsername(userName);

		if(patientById == null) {
			throw new RecordNotFoundInDbException("patient not found");
		}
		if(patientByUsername.isEmpty()) {
			throw new RecordNotFoundInDbException("patient not found using username");
		}
		if(!Objects.equals(patientById.getUserName(), userName)) {
			throw new PatientIdNotMatchingWithPatientUserName("both the values are not matching");
		}

		patientRepository.deleteById(id);
	}

	public void updatePatient(Long id, Patient patient) {
		if (id == null) {
			throw new IdNotFoundException("bad request, id is null");
		}

		Patient patientById = getPatientById(id);

		if (patientById == null) {
			throw new RecordNotFoundInDbException("patient not found");
		}

		patient.setId(id);
		patientRepository.save(patient);
	}
}
