package org.hcltech.doctor_patient_appointment.exceptions;

public class DifferentUserShouldBeSameException extends RuntimeException {

    public DifferentUserShouldBeSameException(String message) {
        super(message);
    }
}
