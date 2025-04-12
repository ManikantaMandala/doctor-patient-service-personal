package org.hcltech.doctor_patient_appointment.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;

import java.time.LocalDateTime;

//@Entity
//@Getter
//@Setter
//@ToString(callSuper = true)
//@NoArgsConstructor
//@AllArgsConstructor
public class Appointment extends BaseModel {
//    @Column(nullable = false)
    private LocalDateTime time;

//    @Column(nullable = false)
    private Doctor doctor;

//    @Column(nullable = false)
    private Patient patient;

//    @Column(nullable = false)
//    private Prescription prescription;
}
