package org.hcltech.doctor_patient_appointment.mapper;

import org.hcltech.doctor_patient_appointment.daos.services.PatientDaoService;
import org.hcltech.doctor_patient_appointment.dtos.PatientDto;
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
}
