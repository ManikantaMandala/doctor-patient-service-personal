package org.hcltech.doctor_patient_appointment.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.hcltech.doctor_patient_appointment.services.authentication.JpaUserDetailsService;
import org.hcltech.doctor_patient_appointment.utils.JwtUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthRequestFilter extends OncePerRequestFilter {

    private final JpaUserDetailsService jpaUserDetailsService;

    private final JwtUtil jwtUtil;

    public JwtAuthRequestFilter(JpaUserDetailsService jpaUserDetailsService, JwtUtil jwtUtil) {
        this.jpaUserDetailsService = jpaUserDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        final String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            // for which request (optional)

            // manage the token
            String jwt = authorizationHeader.substring(7);
            String username = jwtUtil.extractUsernameFromToken(jwt);

            if(username != null && SecurityContextHolder.getContext().getAuthentication() != null) {
                UserDetails userDetails = this.jpaUserDetailsService.loadUserByUsername(username);

                if(jwtUtil.validateToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext()
                            .setAuthentication(authenticationToken);
                }
            }

        }
        // pass to other filters
        filterChain.doFilter(request, response);

    }
}
