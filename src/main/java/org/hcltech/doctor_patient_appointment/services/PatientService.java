package org.hcltech.doctor_patient_appointment.services;

import javax.print.Doc;

import org.hcltech.doctor_patient_appointment.daos.services.DoctorDaoService;
import org.hcltech.doctor_patient_appointment.daos.services.PatientDaoService;
import org.hcltech.doctor_patient_appointment.dtos.patient.CreatePatientDto;
import org.hcltech.doctor_patient_appointment.dtos.patient.PatientDto;
import org.hcltech.doctor_patient_appointment.dtos.patient.UpdatePatientDto;
import org.hcltech.doctor_patient_appointment.exceptions.DoctorCanNotHaveMorePatientsException;
import org.hcltech.doctor_patient_appointment.exceptions.DoctorNotAssignedToPatientException;
import org.hcltech.doctor_patient_appointment.exceptions.EntryShouldBeUniqueException;
import org.hcltech.doctor_patient_appointment.exceptions.RecordNotFoundInDbException;
import org.hcltech.doctor_patient_appointment.mapper.PatientMapper;
import org.hcltech.doctor_patient_appointment.models.Doctor;
import org.hcltech.doctor_patient_appointment.models.Patient;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    private final PatientDaoService patientDaoService;
    private final DoctorDaoService doctorDaoService;
    private final PatientMapper patientMapper;

    public PatientService(PatientDaoService patientDaoService, PatientMapper patientMapper, DoctorDaoService doctorDaoService) {
        this.patientDaoService = patientDaoService;
        this.patientMapper = patientMapper;
        this.doctorDaoService = doctorDaoService;
    }

    public PatientDto createPatient(CreatePatientDto createPatientDto) {
        Patient patient = patientMapper.toEntityFromCreatePatientDto(createPatientDto);

        if(checkUsernameIfExists(patient.getUserName())) {
            throw new RuntimeException("username should be unique");
        }
        if(checkEmailIfExists(patient.getEmail())) {
            throw new RuntimeException("email should be unique");
        }
        Patient createdPatient = patientDaoService.savePatient(patient);
        return patientMapper.toDto(createdPatient);
    }

    private boolean checkEmailIfExists(String email) {
        return patientDaoService.checkIfExistByEmail(email);
    }
    private boolean checkUsernameIfExists(String username) {
        return patientDaoService.checkIfExistByUsername(username);
    }

    public PatientDto getPatientById(Long id) {
        Patient patientById = patientDaoService.getPatientById(id);
        if(patientById == null) {
            throw new RecordNotFoundInDbException("patient not found");
        }
        return patientMapper.toDto(patientById);
    }

    public void updatePatientDetails(Long id, UpdatePatientDto patientDto) {
        Patient patient = patientMapper.toEntityFromUpdatePatientDto(patientDto);
        if(checkUsernameIfExists(patient.getUserName())) {
            throw new EntryShouldBeUniqueException("username should be unique");
        }
        if(checkEmailIfExists(patient.getEmail())) {
            throw new EntryShouldBeUniqueException("email should be unique");
        }
        patientDaoService.updatePatient(id, patient);
    }

    public void deletePatient(Long id) {
        patientDaoService.deleteById(id);
    }

    public void allocatePatientToDoctor(Long doctorId, Long patientId) {
        Patient patient = patientDaoService.getPatientById(patientId);
        Doctor doctor = doctorDaoService.getDoctorById(doctorId);

        if (doctor == null || patient == null) {
            throw new RecordNotFoundInDbException("not found", doctorId == null ? "DOCTOR": "PATIENT");
        }

        patient.setDoctor(doctor);

        patientDaoService.savePatient(patient);
    }

    public void deallocatePatientFromDoctor(Long doctorId, Long patientId) {
        Patient patientById = patientDaoService.getPatientById(patientId);
        Doctor doctorById = doctorDaoService.getDoctorById(doctorId);

        if (doctorById == null || patientById == null) {
            throw new RecordNotFoundInDbException("not found", doctorId == null ? "DOCTOR": "PATIENT");
        }

        if(patientById.getDoctor() != doctorById) {
            throw new DoctorNotAssignedToPatientException("patient is not allocated to this doctor");
        }
        
        patientById.setDoctor(null);
        patientDaoService.savePatient(patientById);

        doctorById.getPatients().remove(patientById);
        doctorDaoService.saveDoctor(doctorById);
    }
}
