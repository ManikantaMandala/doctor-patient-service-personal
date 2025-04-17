package org.hcltech.doctor_patient_appointment.models;

import jakarta.persistence.*;
import lombok.*;
import org.hcltech.doctor_patient_appointment.enums.Gender;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.List;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Doctor extends Users {
	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private Gender gender;

	@Column(nullable = false)
	private String specialization;

	@Column(nullable = false)
	private String phoneNumber;

	@Column(nullable = false)
	private String address;

	@OneToMany(mappedBy = "doctor", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Patient> patients;
}
