package org.hcltech.doctor_patient_appointment.repositories;

import org.hcltech.doctor_patient_appointment.models.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    boolean existsByEmail(String email);
    boolean existsByUserName(String username);

    Optional<Doctor> findByUserName(String username);
    Optional<Doctor> findByEmail(String email);
}
