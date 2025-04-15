package org.hcltech.doctor_patient_appointment.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.hcltech.doctor_patient_appointment.enums.Gender;

import java.util.List;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Doctor extends Person {
    @Email
    @Column(nullable = false, unique = true)
    private String email;

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
    private List<Patient> patients;

//    private List<Appointment> appointments;
}
