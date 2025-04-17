package org.hcltech.doctor_patient_appointment.exceptions;

public class PatientNotFoundUsingUserNameException extends RuntimeException {

    public PatientNotFoundUsingUserNameException(String message) {
        super(message);
    }
}
