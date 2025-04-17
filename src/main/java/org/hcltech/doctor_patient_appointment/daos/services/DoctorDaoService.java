package org.hcltech.doctor_patient_appointment.daos.services;

import org.hcltech.doctor_patient_appointment.exceptions.BodyNotFoundException;
import org.hcltech.doctor_patient_appointment.exceptions.IdNotFoundException;
import org.hcltech.doctor_patient_appointment.exceptions.RecordNotFoundInDbException;
import org.hcltech.doctor_patient_appointment.models.Doctor;
import org.hcltech.doctor_patient_appointment.repositories.DoctorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorDaoService {

    private final DoctorRepository doctorRepository;

    public DoctorDaoService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public Doctor getDoctorById(Long id) {
        if (id == null) {
            throw new IdNotFoundException("bad request, id is null");
        }

        return doctorRepository.findById(id).orElse(null);
    }

    public Doctor saveDoctor(Doctor doctor) {
        if (doctor == null) {
            throw new BodyNotFoundException("bad request, body is null");
        }

        return doctorRepository.save(doctor);
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Optional<Doctor> getAndUpdateById(Long id, Doctor doctor) {
        if (id == null) {
            throw new IdNotFoundException("bad request, id is null");
        }

        return doctorRepository.findById(id).map(d -> {
            d.setName(doctor.getName());
            d.setGender(doctor.getGender());
            d.setSpecialization(doctor.getSpecialization());

            return doctorRepository.save(d);
        });
    }

    public void deleteDoctor(Long id) {
        if (id == null) {
            throw new IdNotFoundException("bad request, id is null");
        }
        Optional<Doctor> doctor = doctorRepository.findById(id);

        if (doctor.isEmpty()) {
            throw new RecordNotFoundInDbException("doctor record, not found");
        }

        doctorRepository.deleteById(id);
    }

    public boolean checkIfExistByEmail(String email) {
        return doctorRepository.existsByEmail(email);
    }

    public boolean checkIfExistByUsername(String userName) {
        return doctorRepository.existsByUserName(userName);
    }

    public Optional<Doctor> getDoctorByEmail(String email) {
        return doctorRepository.findByEmail(email);
    }

    public Optional<Doctor> getDoctorByUsername(String username) {
        return doctorRepository.findByUserName(username);
    }
}
