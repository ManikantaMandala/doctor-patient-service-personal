package org.hcltech.doctor_patient_appointment.services.authentication;

import org.hcltech.doctor_patient_appointment.daos.services.DoctorDaoService;
import org.hcltech.doctor_patient_appointment.daos.services.PatientDaoService;
import org.hcltech.doctor_patient_appointment.models.Doctor;
import org.hcltech.doctor_patient_appointment.models.Patient;
import org.hcltech.doctor_patient_appointment.models.Users;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final DoctorDaoService doctorDaoService;
    private final PatientDaoService patientDaoService;

    public JpaUserDetailsService(DoctorDaoService doctorDaoService, PatientDaoService patientDaoService) {
        this.doctorDaoService = doctorDaoService;
        this.patientDaoService = patientDaoService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Doctor> doctor = doctorDaoService.getDoctorByUsername(username);
        Optional<Patient> patient = patientDaoService.getPatientByUsername(username);

        // System.out.println(doctor);
        // System.out.println(patient);

        if (doctor.isEmpty() && patient.isEmpty()) {
            throw new UsernameNotFoundException(username + "user not found");
        }

        if (doctor.isEmpty()) {
            return toUserDetails(patient.get().getUser());
        }

        return toUserDetails(doctor.get().getUser());
    }

    private UserDetails toUserDetails(Users person) {

        return User.withUsername(person.getUserName())
                .password(person.getPassword())
                .roles(person.getRoles().split(","))
                .build();
    }
}
