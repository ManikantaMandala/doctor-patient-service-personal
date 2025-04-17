package org.hcltech.doctor_patient_appointment.mapper;

import org.hcltech.doctor_patient_appointment.dtos.admin.AdminDto;
import org.hcltech.doctor_patient_appointment.dtos.authentication.AuthenticationRequestDto;
import org.hcltech.doctor_patient_appointment.models.Admin;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;

@Service
public class AdminMapper {

    public AdminDto toDto(Admin createdAdmin) {
        AdminDto dto = new AdminDto();

        dto.setId(createdAdmin.getId());
        dto.setUserName(createdAdmin.getUserName());
        dto.setEmail(createdAdmin.getEmail());
        dto.setRoles(createdAdmin.getRoles());

        return dto;
    }

    public Admin toEntity(@Valid AuthenticationRequestDto adminDto) {
        Admin admin = new Admin();

        admin.setUserName(adminDto.getUsername());
        admin.setPassword(adminDto.getPassword());
        admin.setRoles(adminDto.getRoles());
        admin.setEmail(adminDto.getEmail());

        return admin;
    }
}
