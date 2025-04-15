package org.hcltech.doctor_patient_appointment.daos.services;

import org.hcltech.doctor_patient_appointment.exceptions.IdNotFoundException;
import org.hcltech.doctor_patient_appointment.exceptions.RecordNotFoundInDbException;
import org.hcltech.doctor_patient_appointment.models.Patient;
import org.hcltech.doctor_patient_appointment.repositories.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PatientDaoService {

	private final PatientRepository patientRepository;

	public PatientDaoService(PatientRepository patientRepository) {
		this.patientRepository = patientRepository;
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
