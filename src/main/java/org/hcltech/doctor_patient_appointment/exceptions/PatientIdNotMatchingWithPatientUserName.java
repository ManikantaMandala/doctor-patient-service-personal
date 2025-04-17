package org.hcltech.doctor_patient_appointment.exceptions;

public class PatientIdNotMatchingWithPatientUserName extends RuntimeException {

    public PatientIdNotMatchingWithPatientUserName(String message) {
        super(message);
    }
}
