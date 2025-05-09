package org.hcltech.doctor_patient_appointment.dtos.doctor;

import lombok.*;
import org.hcltech.doctor_patient_appointment.enums.Gender;

import java.util.List;
import java.util.Set;

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
	private Set<String> roles;
	private List<Long> patientDtos;
}
