package org.hcltech.doctor_patient_appointment.controllers;

import org.hcltech.doctor_patient_appointment.dtos.doctor.DoctorDto;
import org.hcltech.doctor_patient_appointment.dtos.doctor.PatientDoctorDto;
import org.hcltech.doctor_patient_appointment.dtos.patient.DoctorPatientDto;
import org.hcltech.doctor_patient_appointment.services.DoctorService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/v1/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

	/**
	 * @apiNote This API is used to get doctor details but not the patient 
	 * details with it.
	 *
	 * @param Long id
	 *
	 * @return DoctorDto doctor
	 */
    @PreAuthorize("hasRole('ROLE_PATIENT', 'ROLE_ADMIN', 'ROLE_DOCTOR')")
    @GetMapping
    public ResponseEntity<List<PatientDoctorDto>> getDoctors() {
        List<PatientDoctorDto> doctorDtoList = doctorService.getDoctorsList();

        return ResponseEntity.ok(doctorDtoList);
    }

	/**
	 * This API is used to get doctor details but not the patient 
	 * details with it. Only patient ids are returned.
	 *
	 * @param Long id
	 *
	 * @return DoctorDto doctor
	 */
    @PreAuthorize("hasRole('ROLE_DOCTOR', 'ROLE_ADMIN')")
    @GetMapping("/{doctorId}")
    public ResponseEntity<DoctorDto> getDoctorByDoctorId(@PathVariable Long doctorId) {
        DoctorDto doctorDto = doctorService.getDoctorById(doctorId);

        return ResponseEntity.ok(doctorDto);
    }

	/**
	 * This API is used to get doctor's patient details
	 * 
	 * @param Long id
	 *
	 * @return List<DoctorPatientDto> patients
	 */
    @PreAuthorize("hasRole('ROLE_DOCTOR', 'ROLE_ADMIN')")
    @GetMapping("/patients/{doctorId}")
    public ResponseEntity<List<DoctorPatientDto>> getPatientsByDoctorId(@PathVariable @Valid Long doctorId) {
        List<DoctorPatientDto> patients = doctorService.getPatientsByDoctorId(doctorId);

        return ResponseEntity.ok(patients);
    }

	/**
	 * This API is used to update details of the doctor
	 * @apiNote For allocating the patient, 
	 * use allocate doctor route in the patient controller 
	 * TODO: check this after completion
	 *
	 * @param Long id
	 * @param DoctorDto doctor
	 *
	 * TODO: add the null cases and exception cases in the documentation
	 */
    @PreAuthorize("hasRole('ROLE_DOCTOR', 'ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<String> updateDoctorDetails(@PathVariable Long id, @RequestBody DoctorDto doctorDto) {
        doctorService.updateDoctorDetails(id, doctorDto);

        return ResponseEntity.ok("Resource updated successfully");
    }

	/**
	 * This API is used to delete the doctor account
	 * @apiNote And it only has the permission to ADMIN and DOCTOR
	 * @implNote uses hard delete
	 *
	 * @param Long id
	 *
	 * @return String 
	 * TODO: add the null cases and exception cases
	 */
    @PreAuthorize("hasRole('ROLE_ADMIN', 'ROLE_DOCTOR')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctorById(id);

        return ResponseEntity.ok("Resource deleted successfully");
    }
}
