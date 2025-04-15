package org.hcltech.doctor_patient_appointment.controllers.controlleradvices;

import org.hcltech.doctor_patient_appointment.exceptions.BodyNotFoundException;
import org.hcltech.doctor_patient_appointment.exceptions.DoctorCanNotHaveMorePatientsException;
import org.hcltech.doctor_patient_appointment.exceptions.DoctorNotAssignedToPatientException;
import org.hcltech.doctor_patient_appointment.exceptions.EntryShouldBeUniqueException;
import org.hcltech.doctor_patient_appointment.exceptions.IdNotFoundException;
import org.hcltech.doctor_patient_appointment.exceptions.RecordNotFoundInDbException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;

public class ControllerAdvice {
    
    @ExceptionHandler(EntryShouldBeUniqueException.class)
    public ResponseEntity<ErrorResponse> entryShouldbeUnique(EntryShouldBeUniqueException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.create(ex, HttpStatusCode.valueOf(400), ex.getMessage()));
    }

    @ExceptionHandler(RecordNotFoundInDbException.class)
    public ResponseEntity<ErrorResponse> recordNotFoundInDb(RecordNotFoundInDbException ex) {
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.create(ex, HttpStatusCode.valueOf(404), ex.getMessage()));  
    }

    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<ErrorResponse> idNotFound(IdNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.create(ex, HttpStatusCode.valueOf(404), ex.getMessage()));
    }

    @ExceptionHandler(BodyNotFoundException.class)
    public ResponseEntity<ErrorResponse> bodyNotFound(BodyNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.create(ex, HttpStatusCode.valueOf(400), ex.getMessage()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> usernameNotFound(UsernameNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.create(ex, HttpStatusCode.valueOf(400), ex.getMessage()));  
    }

    @ExceptionHandler(DoctorNotAssignedToPatientException.class)
    public ResponseEntity<ErrorResponse> doctorNotAssignedToPatient(DoctorNotAssignedToPatientException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResponse.create(ex, HttpStatusCode.valueOf(409), ex.getMessage()));
    }

    @ExceptionHandler(DoctorCanNotHaveMorePatientsException.class)
    public ResponseEntity<ErrorResponse> doctorCanNotHaveMorePatients(DoctorNotAssignedToPatientException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ErrorResponse.create(ex, HttpStatusCode.valueOf(409), ex.getMessage())); 
    }
}
