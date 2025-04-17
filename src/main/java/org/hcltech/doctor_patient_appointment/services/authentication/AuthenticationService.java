package org.hcltech.doctor_patient_appointment.services.authentication;

import org.hcltech.doctor_patient_appointment.dtos.authentication.AuthenticationRequestDto;
import org.hcltech.doctor_patient_appointment.dtos.authentication.AuthenticationResponseDto;
import org.hcltech.doctor_patient_appointment.utils.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JpaUserDetailsService jpaUserDetailsService;
    private final JwtUtil jwtUtil;

    public AuthenticationService(
            AuthenticationManager authenticationManager,
            JpaUserDetailsService jpaUserDetailsService,
            JwtUtil jwtUtil
            /*DoctorDaoService doctorDaoService,*/) {

        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.jpaUserDetailsService = jpaUserDetailsService;
//        this.doctorDaoService = doctorDaoService;
    }

    public AuthenticationResponseDto login(AuthenticationRequestDto authenticationRequestDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequestDto.getUsername(),
                        authenticationRequestDto.getPassword())
        );

        if (!authentication.isAuthenticated()) {
            throw new UsernameNotFoundException(authenticationRequestDto.getUsername() + " not found");
        }

        UserDetails userDetails = jpaUserDetailsService.loadUserByUsername(
                authenticationRequestDto.getUsername());

        String token = jwtUtil.generateToken(userDetails);
        Date expirationTime = jwtUtil.extractExpirationFromToken(token);

        AuthenticationResponseDto responseDto = new AuthenticationResponseDto();

        responseDto.setExpiresIn(expirationTime);
        responseDto.setAccessToken(token);
        responseDto.setPrinciple(authenticationRequestDto.getUsername());

        return responseDto;
    }
}
