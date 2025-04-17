package org.hcltech.doctor_patient_appointment.exceptions;

public class EmailShouldBeUniqueException extends RuntimeException {

    public EmailShouldBeUniqueException(String message) {
        super(message);
    }
}
