package org.hcltech.doctor_patient_appointment.services;

import org.hcltech.doctor_patient_appointment.daos.services.AdminDaoService;
import org.hcltech.doctor_patient_appointment.dtos.admin.AdminDto;
import org.hcltech.doctor_patient_appointment.dtos.authentication.AuthenticationRequestDto;
import org.hcltech.doctor_patient_appointment.mapper.AdminMapper;
import org.hcltech.doctor_patient_appointment.models.Admin;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;

@Service
public class AdminService {

    private AdminDaoService adminDaoService;
    private AdminMapper adminMapper;

    public AdminService(AdminDaoService adminDaoService, AdminMapper adminMapper) {
        this.adminDaoService = adminDaoService;
    }

    public AdminDto createAdmin(@Valid AuthenticationRequestDto adminDto) {
        Admin admin = adminMapper.toEntity(adminDto);
        Admin createdAdmin = adminDaoService.createAdmin(admin);
        return adminMapper.toDto(createdAdmin);
    }

}
