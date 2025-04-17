package org.hcltech.doctor_patient_appointment.daos.services;

import org.hcltech.doctor_patient_appointment.exceptions.AdminIsNullException;
import org.hcltech.doctor_patient_appointment.models.Admin;
import org.hcltech.doctor_patient_appointment.repositories.AdminRepository;
import org.springframework.stereotype.Service;

@Service
public class AdminDaoService {

    private AdminRepository adminRepository;

    public AdminDaoService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public Admin createAdmin(Admin admin) {
        if (admin == null) {
            throw new AdminIsNullException("bad request, admin is null");
        }
        return adminRepository.save(admin);
    }

}
