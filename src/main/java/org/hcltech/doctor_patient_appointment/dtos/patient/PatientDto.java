package org.hcltech.doctor_patient_appointment.dtos.patient;

import lombok.*;
import org.hcltech.doctor_patient_appointment.enums.Gender;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PatientDto {
    private Long id;
    private String fullName;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private Integer age;
    private Gender gender;
    private String phoneNumber;
    private String address;
    private Long doctorId;
}
