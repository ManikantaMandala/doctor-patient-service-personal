package org.hcltech.doctor_patient_appointment.services;

import org.hcltech.doctor_patient_appointment.daos.services.PatientDaoService;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

    private final PatientDaoService patientDaoService;

    public PatientService(PatientDaoService patientDaoService) {
        this.patientDaoService = patientDaoService;
    }


}
