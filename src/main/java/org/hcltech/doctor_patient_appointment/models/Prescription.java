package org.hcltech.doctor_patient_appointment.models;

import jakarta.persistence.Entity;
import lombok.*;

//@Entity
//@Getter
//@Setter
//@ToString
//@NoArgsConstructor
//@AllArgsConstructor
public class Prescription extends BaseModel {
    private Appointment appointment;
    private Doctor doctor;
    private Patient patient;
}
