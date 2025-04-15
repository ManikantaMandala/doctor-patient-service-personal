package org.hcltech.doctor_patient_appointment.exceptions;

public class DoctorNotAssignedToPatientException extends RuntimeException {
    public DoctorNotAssignedToPatientException(String message) {
        super(message);
    }
    
}
