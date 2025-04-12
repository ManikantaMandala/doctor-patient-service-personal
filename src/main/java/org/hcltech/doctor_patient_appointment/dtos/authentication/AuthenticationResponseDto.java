package org.hcltech.doctor_patient_appointment.dtos.authentication;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationResponseDto {
    String accessToken;
//    String tokenType;
    Date expiresIn;
    String principle;
}
