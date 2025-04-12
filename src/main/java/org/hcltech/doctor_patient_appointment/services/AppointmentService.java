package org.hcltech.doctor_patient_appointment.services;

import org.hcltech.doctor_patient_appointment.daos.services.AppointmentDaoService;
import org.hcltech.doctor_patient_appointment.repositories.AppointmentRepository;
import org.springframework.stereotype.Service;

@Service
public class AppointmentService {

    private final AppointmentDaoService appointmentDaoService;

    public AppointmentService(AppointmentDaoService appointmentDaoService) {
        this.appointmentDaoService = appointmentDaoService;
    }
}
