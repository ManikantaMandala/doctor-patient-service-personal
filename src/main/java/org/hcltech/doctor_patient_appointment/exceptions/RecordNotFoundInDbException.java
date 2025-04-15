package org.hcltech.doctor_patient_appointment.exceptions;

public class RecordNotFoundInDbException extends RuntimeException {
    public RecordNotFoundInDbException(String message) {
        super(message);
    }

    public RecordNotFoundInDbException(String message, String entity) {
        super(entity + " " + message);
    }
    
}
