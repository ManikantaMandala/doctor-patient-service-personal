package org.hcltech.doctor_patient_appointment.models;

import jakarta.persistence.*;
import lombok.*;
import org.hcltech.doctor_patient_appointment.enums.Gender;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Patient extends Users {
	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	@Column(nullable = false)
	private Integer age;

	@Column(nullable = false)
	@Enumerated(value = EnumType.STRING)
	private Gender gender;

	@Column(nullable = false)
	private String phoneNumber;

	@Column(nullable = false)
	private String address;

	@ManyToOne
	@JoinColumn(name = "fk_appointment_doctor", referencedColumnName = "id")
	private Doctor doctor;
}
