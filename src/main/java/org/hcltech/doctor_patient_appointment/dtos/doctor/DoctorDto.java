package org.hcltech.doctor_patient_appointment.dtos.doctor;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.hcltech.doctor_patient_appointment.dtos.PatientDto;
import org.hcltech.doctor_patient_appointment.enums.Gender;
import org.hcltech.doctor_patient_appointment.models.Patient;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDto {
    private Long id;
    private String email;
    private String username;
    private String password;
    private String name;
    private Gender gender;
    private String specialization;
    private String phoneNumber;
    private String address;
    private String roles;
    private List<PatientDto> patientDtos;
}
