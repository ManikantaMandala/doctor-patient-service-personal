package org.hcltech.doctor_patient_appointment.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@ToString(callSuper = true)
@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
public class Person extends BaseModel{
    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false)
    private String password;

    private String roles;
}
