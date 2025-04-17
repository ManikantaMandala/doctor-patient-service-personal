package org.hcltech.doctor_patient_appointment.exceptions;

public class PatientUsernameShouldMatchWithTheIdException extends RuntimeException {

	public PatientUsernameShouldMatchWithTheIdException(String message) {
		super(message);
	}
}
