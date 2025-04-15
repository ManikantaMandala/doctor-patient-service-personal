package org.hcltech.doctor_patient_appointment.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@MappedSuperclass
@NoArgsConstructor
public class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
