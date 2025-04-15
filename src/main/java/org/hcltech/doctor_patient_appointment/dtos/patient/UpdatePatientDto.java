package org.hcltech.doctor_patient_appointment.dtos.patient;

import org.hcltech.doctor_patient_appointment.enums.Gender;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePatientDto {
    private String fullName;
    private String firstName;
    private String lastName;
    private Integer age;
    private Gender gender;
    private String phoneNumber;
    private String address;
}
