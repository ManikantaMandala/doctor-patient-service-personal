package org.hcltech.doctor_patient_appointment.controllers;

import org.hcltech.doctor_patient_appointment.dtos.patient.PatientDto;
import org.hcltech.doctor_patient_appointment.dtos.patient.UpdatePatientDto;
import org.hcltech.doctor_patient_appointment.services.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/patients")
public class PatientController {

	private final PatientService patientService;

	public PatientController(PatientService patientService) {
		this.patientService = patientService;
	}

	/**
	 * get patient details by id
	 *
	 * @param Long id
	 *
	 * @return PatientDto patientDto
	 */
	@PreAuthorize("hasRole('ROLE_DOCTOR', 'ROLE_ADMIN')")
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
	@PostMapping("/{id}")
	public ResponseEntity<String> updatePatientDetails(@PathVariable Long id,
			@RequestBody UpdatePatientDto patientDto) {
		patientService.updatePatientDetails(id, patientDto);

		return ResponseEntity.ok("Resource updated successfully");
	}

	/**
	 * This is to allocate the patient to doctor.
	 *
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_DOCTOR', 'ROLE_ADMIN', 'ROLE_PATIENT')")
	@GetMapping("allocate/{doctorId}")
	public ResponseEntity<String> allocatePatientToDoctor(@PathVariable Long doctorId, @RequestParam Long patientId) {
		// Logic to allocate patient to doctor
		patientService.allocatePatientToDoctor(doctorId, patientId);

		return ResponseEntity.ok("Patient allocated to doctor successfully");
	}

	/**
	 * This is to deallocate the doctor from a patient
	 * TODO: implement the unallocate api route in doctor with doctor, patient and
	 * admin permissions
	 *
	 */
	@PreAuthorize("hasRole('ROLE_DOCTOR', 'ROLE_ADMIN', 'ROLE_PATIENT')")
	@GetMapping("deallocate/{doctorId}")
	public ResponseEntity<String> deallocatePatientFromDoctor(@PathVariable Long doctorId,
			@RequestParam Long patientId) {
		// Logic to deallocate patient from doctor
		patientService.deallocatePatientFromDoctor(doctorId, patientId);

		return ResponseEntity.ok("Patient deallocated from doctor successfully");
	}

	// delete
	/**
	 *
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN', 'ROLE_PATIENT')")
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deletePatient(@PathVariable Long id) {
		patientService.deletePatient(id);

		return ResponseEntity.ok("Resource deleted successfully");
	}
}
