package org.hcltech.doctor_patient_appointment.dtos;

import lombok.*;
import org.hcltech.doctor_patient_appointment.enums.Gender;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PatientDto {
    private Long id;
    private String name;
    private String firstName;
    private String lastName;
    private Integer age;
    private Gender gender;
    private String phoneNumber;
    private Long doctorId;
}
