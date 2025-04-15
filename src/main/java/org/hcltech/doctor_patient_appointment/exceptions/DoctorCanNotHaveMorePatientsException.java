package org.hcltech.doctor_patient_appointment.exceptions;

public class DoctorCanNotHaveMorePatientsException extends RuntimeException {
    public DoctorCanNotHaveMorePatientsException(String message) {
        super(message);
    }
}
