package org.hcltech.doctor_patient_appointment.exceptions;

public class DoctorShouldBeAllocatedToPatientException extends RuntimeException {

    public DoctorShouldBeAllocatedToPatientException(String message) {
        super(message);
    }
}
