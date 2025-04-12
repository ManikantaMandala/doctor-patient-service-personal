package org.hcltech.doctor_patient_appointment.mapper;

import org.hcltech.doctor_patient_appointment.dtos.doctor.DoctorDto;
import org.hcltech.doctor_patient_appointment.dtos.PatientDto;
import org.hcltech.doctor_patient_appointment.dtos.doctor.PatientDoctorDto;
import org.hcltech.doctor_patient_appointment.models.Doctor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorMapper {

    private final PatientMapper patientMapper;

    public DoctorMapper(PatientMapper patientMapper) {
        this.patientMapper = patientMapper;
    }

    public DoctorDto toDto(Doctor doctor) {
        DoctorDto doctorDto = new DoctorDto();

        doctorDto.setId(doctor.getId());
        doctorDto.setName(doctor.getName());
        doctorDto.setSpecialization(doctor.getSpecialization());
        doctorDto.setGender(doctor.getGender());

        if(doctor.getPatients() == null) {
            doctor.setPatients(new ArrayList<>());
        } else {
            List<PatientDto> patientDtos = doctor.getPatients()
                    .stream()
                    .map(patientMapper::toPatientDto)
                    .toList();
            doctorDto.setPatientDtos(patientDtos);
        }

        return doctorDto;
    }

    public PatientDoctorDto toPatientDoctorDto(Doctor doctor) {
        PatientDoctorDto patientDoctorDto = new PatientDoctorDto();

        patientDoctorDto.setName(doctor.getName());
        patientDoctorDto.setGender(doctor.getGender());
        patientDoctorDto.setSpecialization(doctor.getSpecialization());
        patientDoctorDto.setEmail(doctor.getEmail());
        patientDoctorDto.setUsername(doctor.getUserName());
        patientDoctorDto.setAddress(doctor.getAddress());
        patientDoctorDto.setPhoneNumber(doctor.getPhoneNumber());
        patientDoctorDto.setAvailable(doctor.getPatients().size() < 4);

        return patientDoctorDto;
    }

    public Doctor toEntity(DoctorDto doctorDto) {
        Doctor doctor = new Doctor();

        doctor.setUserName(doctorDto.getUsername());
        doctor.setEmail(doctorDto.getEmail());
        doctor.setName(doctorDto.getName());
        doctor.setAddress(doctorDto.getAddress());
        doctor.setGender(doctorDto.getGender());
        doctor.setSpecialization(doctorDto.getSpecialization());
        doctor.setPhoneNumber(doctorDto.getPhoneNumber());
        doctor.setPassword(doctorDto.getPassword());
        doctor.setRoles(doctorDto.getRoles() == null ? "DOCTOR" : doctorDto.getRoles());

        return doctor;
    }

    /*
    // this will be used when id is not handling and DoctorDto is given by user
    public Doctor toEntityAfterCreation(DoctorDto doctorDto) {
        if(doctorDto.getId() == null) {
            // TODO: controller advice
            // TODO: custom exception
            // TODO: status code: bad request
            // TODO: change the error message too
            throw new RuntimeException("Error converting dto to dao");
        }

        Doctor doctorFromDb = doctorDaoService.getDoctorById(doctorDto.getId());

        if(doctorFromDb == null) {
            // TODO: controller advice
            // TODO: custom exception
            // TODO: status code: bad request
            throw new RuntimeException("entity not found");
        }

        Doctor doctor = new Doctor();

        doctor.setId(doctorFromDb.getId());
        doctor.setName(doctorFromDb.getName());
        doctor.setGender(doctorFromDb.getGender());
        doctor.setSpecialization(doctorFromDb.getSpecialization());
        doctor.setPatients(doctorFromDb.getPatients());

        return doctor;
    }
     */

}
