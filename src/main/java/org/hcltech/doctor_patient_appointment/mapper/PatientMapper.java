package org.hcltech.doctor_patient_appointment.mapper;

import org.hcltech.doctor_patient_appointment.daos.services.PatientDaoService;
import org.hcltech.doctor_patient_appointment.dtos.patient.CreatePatientDto;
import org.hcltech.doctor_patient_appointment.dtos.patient.PatientDto;
import org.hcltech.doctor_patient_appointment.dtos.patient.UpdatePatientDto;
import org.hcltech.doctor_patient_appointment.enums.Gender;
import org.hcltech.doctor_patient_appointment.models.Patient;
import org.springframework.stereotype.Service;

@Service
public class PatientMapper {

    private final PatientDaoService patientDaoService;

    public PatientMapper(PatientDaoService patientDaoService) {
        this.patientDaoService = patientDaoService;
    }

    public PatientDto toPatientDto(Patient patient) {
        PatientDto patientDto = new PatientDto();

        patientDto.setDoctorId(patient.getDoctor().getId());

        return patientDto;
    }

    public PatientDto toDto(Patient patient) {
        PatientDto patientDto = new PatientDto();

        patientDto.setId(patient.getId());
        patientDto.setFirstName(patient.getFirstName());
        patientDto.setLastName(patient.getLastName());
        patientDto.setUsername(patient.getUser().getUserName());
        patientDto.setEmail(patient.getUser().getEmail());
        patientDto.setPhoneNumber(patient.getPhoneNumber());
        patientDto.setGender(patient.getGender());
        patientDto.setAddress(patient.getAddress());
        patientDto.setAge(patient.getAge());

        return patientDto;
    }

    public Patient toEntityFromCreatePatientDto(CreatePatientDto patientDto) {
        Patient patient = new Patient();

        patient.setFirstName(patientDto.getFirstName());
        patient.setLastName(patientDto.getLastName());
        patient.getUser().setUserName(patientDto.getUsername());
        patient.getUser().setPassword(patientDto.getPassword());
        patient.getUser().setEmail(patientDto.getEmail());
        patient.setPhoneNumber(patientDto.getPhoneNumber());
        patient.setGender(patientDto.getGender());
        patient.setAddress(patientDto.getAddress());
        patient.setAge(patientDto.getAge());

        return patient;
    }

    public Patient toEntityFromUpdatePatientDto(UpdatePatientDto patientDto) {
        Patient patient = new Patient();

        patient.setFirstName(patientDto.getFirstName());
        patient.setLastName(patientDto.getLastName());
        patient.setAge(patientDto.getAge());
        patient.setGender(patientDto.getGender());
        patient.setPhoneNumber(patientDto.getPhoneNumber());
        patient.setAddress(patientDto.getAddress());

        return patient;
    }
}
