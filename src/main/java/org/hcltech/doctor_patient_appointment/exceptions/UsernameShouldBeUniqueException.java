package org.hcltech.doctor_patient_appointment.exceptions;

public class UsernameShouldBeUniqueException extends RuntimeException {

    public UsernameShouldBeUniqueException(String message) {
        super(message);
    }
}
