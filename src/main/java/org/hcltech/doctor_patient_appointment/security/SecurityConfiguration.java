package org.hcltech.doctor_patient_appointment.security;

import org.hcltech.doctor_patient_appointment.filters.JwtAuthRequestFilter;
import org.hcltech.doctor_patient_appointment.services.authentication.JpaUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	private static final String[] SWAGGER_WHITE_LIST = {
			"/swagger-ui.html",
			"/swagger-ui/index.html",
			"/swagger-ui/**",
			"/swagger-resources/**",
			"/v3/api-docs/**",
			"/webjars/**"
	};

	private static final String[] H2_CONSOLE_WHITE_LIST = {
			"/h2-console/**"
	};

	private static final String[] AUTHENTICATION_WHITE_LIST = {
			"/api/v1/auth/doctor/**",
			"/api/v1/auth/doctor",
			"/api/v1/auth/patient/**",
			"/api/v1/auth/patient",
	};

	private static final String[] PATIENT_WHITE_LIST = {
			"/api/v1/patients/**",
			"/api/v1/doctors/patient/**",
	};

	private static final String[] DOCTOR_WHITE_LIST = {
			"/api/v1/doctors/**",
			"/api/v1/doctors",
			"/api/v1/doctors/patients/**",
	};

	@Autowired
	private JwtAuthRequestFilter jwtAuthRequestFilter;
	@Autowired
	private JpaUserDetailsService jpaUserDetailsService;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.csrf(csrf -> csrf.disable())
				.cors(cors -> cors.disable())
				.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))
				.httpBasic(Customizer.withDefaults())
				.formLogin(Customizer.withDefaults())
				.httpBasic(AbstractHttpConfigurer::disable)
				.formLogin(AbstractHttpConfigurer::disable)
				.logout(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(authorize -> {
					authorize
							.requestMatchers(SWAGGER_WHITE_LIST).permitAll()
							.requestMatchers("/h2-console/**").permitAll()
							.requestMatchers(AUTHENTICATION_WHITE_LIST).hasAuthority("ROLE_ADMIN")
							.anyRequest().authenticated();
				}).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider(jpaUserDetailsService))
				.addFilterBefore(jwtAuthRequestFilter, AuthorizationFilter.class);
		return http.build();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider(JpaUserDetailsService jpaUserDetailsService) {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(jpaUserDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder());

		return authenticationProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
}
