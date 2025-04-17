package org.hcltech.doctor_patient_appointment.controllers;

import java.util.List;

import org.hcltech.doctor_patient_appointment.dtos.patient.PatientDto;
import org.hcltech.doctor_patient_appointment.dtos.patient.UpdatePatientDto;
import org.hcltech.doctor_patient_appointment.services.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {

	private final PatientService patientService;

	public PatientController(PatientService patientService) {
		this.patientService = patientService;
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping
	public ResponseEntity<List<PatientDto>> getPatients() {
		List<PatientDto> patients = patientService.getPatients();

		return ResponseEntity.ok(patients);
	}

	/**
	 * get patient details by id
	 *
	 * @param Long id
	 *
	 * @return PatientDto patientDto
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN', 'ROLE_DOCTOR', 'ROLE_PATIENT')")
	@GetMapping("/{id}")
	public ResponseEntity<PatientDto> getPatientById(@PathVariable @Valid Long id) {
		PatientDto patientDto = patientService.getPatientById(id);

		return ResponseEntity.ok(patientDto);
	}

	/**
	 * update patient details by id.
	 * 
	 * @implNote This UpdatePatientDto does not contain doctor details. so, If the
	 *           patient's doctor details are not updated then
	 *           it won't change the previous patient details
	 *           TODO: need to check above behaviour
	 * @return String successfully or not successfully
	 */
	@PreAuthorize("hasRole('ROLE_DOCTOR', 'ROLE_ADMIN')")
	@PutMapping("/{id}/{doctorId}")
	public ResponseEntity<String> updatePatientDetails(@PathVariable Long id,
			@RequestBody UpdatePatientDto patientDto, @PathVariable Long doctorId) {
		patientService.updatePatientDetailsIfSameDoctor(id, patientDto, doctorId);

		return ResponseEntity.ok("Resource updated successfully");
	}

	@PreAuthorize("hasRole('ROLE_PATIENT', 'ROLE_ADMIN')")
	@PutMapping("/{id}/{patientUserName}")
	public ResponseEntity<String> updatePatientDetailsIfSamePatient(@PathVariable Long id,
			@RequestBody UpdatePatientDto patientDto, @RequestParam String patientUserName) {
		patientService.updatePatientDetailsIfSamePatient(id, patientDto, patientUserName);

		return ResponseEntity.ok("Resource updated successfully");
	}

	/**
	 * This is to allocate the patient to doctor.
	 *
	 * @implNote This is hard allocation
	 *           (meaning: it doesn't check for previous doctor)
	 *
	 * @return String successfully or not successfully
	 */
	@PreAuthorize("hasRole('ROLE_DOCTOR', 'ROLE_ADMIN')")
	@GetMapping("allocate/{doctorId}")
	public ResponseEntity<String> allocatePatientToDoctor(
			@PathVariable Long doctorId,
			@RequestParam Long patientId) {
		// Logic to allocate patient to doctor
		patientService.allocatePatientToDoctor(doctorId,
				patientId);

		return ResponseEntity.ok("Patient allocated to doctor successfully");
	}

	@PreAuthorize("hasRole('ROLE_PATIENT', 'ROLE_ADMIN')")
	@GetMapping("allocate/{doctorId}/patient/{username}")
	public ResponseEntity<String> allocatePatientToDoctorIfSamePatient(
			@PathVariable Long doctorId, @RequestParam Long patientId,
			@RequestParam String username) {
		// Logic to allocate patient to doctor
		patientService.allocatePatientToDoctorIfSamePatient(doctorId, patientId, username);

		return ResponseEntity.ok("Patient allocated to doctor successfully");
	}

	/**
	 * This is to deallocate the doctor from a patient
	 *
	 * @apiNote it checks whether the doctor is allocated to patient first then it
	 *          tries to deallocate the doctor
	 * @implNote it uses database's {@link jakarta.persistence.CascadeType} to
	 *           reflect the changes
	 */
	@PreAuthorize("hasRole('ROLE_DOCTOR', 'ROLE_ADMIN')")
	@GetMapping("deallocate/{doctorId}")
	public ResponseEntity<String> deallocatePatientFromDoctor(
			@PathVariable Long doctorId,
			@RequestParam Long patientId) {
		patientService.deallocatePatientFromDoctor(doctorId, patientId);

		return ResponseEntity.ok("Patient deallocated from doctor successfully");
	}

	@PreAuthorize("hasRole('ROLE_DOCTOR', 'ROLE_ADMIN', 'ROLE_PATIENT')")
	@GetMapping("deallocate/{doctorId}/{patientUserName}")
	public ResponseEntity<String> deallocatePatientFromDoctorIfSamePatient(@PathVariable Long doctorId,
			@RequestParam Long patientId, @PathVariable String patientUserName) {
		patientService.deallocatePatientFromDoctorIfSamePatient(doctorId, patientId, patientUserName);

		return ResponseEntity.ok("Patient deallocated from doctor successfully");
	}

	/**
	 * This is to delete the patient and remove the relationship between doctor
	 *
	 * @implNote it doesn't use OrphanRemovalAction. So, if we break the
	 *           relationship between Patient(Parent) and Doctor(Child) it won't
	 *           effect the Doctor(Child)
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePatient(@PathVariable Long id) {
		patientService.deletePatient(id);

		return ResponseEntity.ok("Resource deleted successfully");
	}

	@PreAuthorize("hasRole('ROLE_PATIENT', 'ROLE_ADMIN')")
	@DeleteMapping("/{id}/{userName}")
	public ResponseEntity<String> deletePatientIfSamePatient(
			@PathVariable Long id, @PathVariable String userName) {
		patientService.deletePatient(id, userName);

		return ResponseEntity.ok("Resource deleted successfully");
	}
}
