package org.hcltech.doctor_patient_appointment.exceptions;

public class IdNotFoundException extends RuntimeException {

    public IdNotFoundException(String message) {
        super(message);
    }
}
