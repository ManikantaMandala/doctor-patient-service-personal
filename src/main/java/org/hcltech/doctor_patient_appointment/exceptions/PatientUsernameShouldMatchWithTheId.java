package org.hcltech.doctor_patient_appointment.exceptions;

public class PatientUsernameShouldMatchWithTheId extends RuntimeException {

    public PatientUsernameShouldMatchWithTheId(String message) {
        super(message);
    }
}
