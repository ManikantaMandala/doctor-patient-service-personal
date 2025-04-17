package org.hcltech.doctor_patient_appointment.mapper;

import org.hcltech.doctor_patient_appointment.dtos.admin.AdminDto;
import org.hcltech.doctor_patient_appointment.dtos.authentication.AuthenticationRequestDto;
import org.hcltech.doctor_patient_appointment.models.Admin;
import org.hcltech.doctor_patient_appointment.models.Users;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;

@Service
public class AdminMapper {

    public AdminDto toDto(Admin createdAdmin) {
        AdminDto dto = new AdminDto();

        dto.setId(createdAdmin.getId());
        dto.setUserName(createdAdmin.getUser().getUserName());
        dto.setEmail(createdAdmin.getUser().getEmail());
        dto.setRoles(createdAdmin.getUser().getRoles());

        return dto;
    }

    public Admin toEntity(@Valid AuthenticationRequestDto adminDto) {
        Admin admin = new Admin();
        Users users = new Users();

        users.setUserName(adminDto.getUsername());
        users.setPassword(adminDto.getPassword());
        users.setRoles(adminDto.getRoles());
        users.setEmail(adminDto.getEmail());

        admin.setUser(users);

        return admin;
    }
}
