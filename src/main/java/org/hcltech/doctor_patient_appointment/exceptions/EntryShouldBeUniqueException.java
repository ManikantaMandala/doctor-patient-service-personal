package org.hcltech.doctor_patient_appointment.exceptions;

public class EntryShouldBeUniqueException extends RuntimeException {
    public EntryShouldBeUniqueException(String message) {
        super(message);
    }
    
}
