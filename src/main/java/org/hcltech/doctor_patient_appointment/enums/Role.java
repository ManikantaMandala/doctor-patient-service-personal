package org.hcltech.doctor_patient_appointment.enums;

public enum Role {
    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_DOCTOR("ROLE_DOCTOR"),
    ROLE_PATIENT("ROLE_PATIENT");

    private final String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
