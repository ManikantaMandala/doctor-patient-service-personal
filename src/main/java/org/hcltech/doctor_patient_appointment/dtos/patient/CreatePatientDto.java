package org.hcltech.doctor_patient_appointment.dtos.patient;

import lombok.*;
import org.hcltech.doctor_patient_appointment.enums.Gender;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CreatePatientDto {
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private Integer age;
    private Gender gender;
    private String phoneNumber;
    private String address;
}
