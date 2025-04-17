package org.hcltech.doctor_patient_appointment.dtos.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminDto {
    private Long id;
    private String userName;
    private String email;
    private String roles;
}
