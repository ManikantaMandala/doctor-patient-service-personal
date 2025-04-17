package org.hcltech.doctor_patient_appointment.dtos.authentication;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequestDto {
    private String username;
    private String password;
    @Email
    private String email;
    private String roles;
}
