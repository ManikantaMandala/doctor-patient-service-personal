package org.hcltech.doctor_patient_appointment.dtos.patient;

import org.hcltech.doctor_patient_appointment.enums.Gender;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DoctorPatientDto {
    Long id;
    String fullName;
    String firstName;
    String lastName;
    String email;
    String username;
    String phoneNumber;
    String address;
    Gender gender;
    Integer age;
}
