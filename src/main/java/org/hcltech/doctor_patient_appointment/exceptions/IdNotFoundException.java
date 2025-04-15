package org.hcltech.doctor_patient_appointment.exceptions;

import jakarta.persistence.Id;

public class IdNotFoundException extends RuntimeException{
    
    public IdNotFoundException(String message) {
        super(message);
    }
}
