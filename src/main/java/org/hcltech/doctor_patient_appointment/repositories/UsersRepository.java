package org.hcltech.doctor_patient_appointment.repositories;

import java.util.Optional;

import org.hcltech.doctor_patient_appointment.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {

	Optional<Users> findByUserName(String username);
}
