package org.hcltech.doctor_patient_appointment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;

@Configuration
public class OpenAPIConfig {

    private static final String SCHEME_NAME = "Authorization";
    private static final String SCHEME = "bearer";
    private static final String BEARER_FORMAT = "JWT";

    @Bean
    public OpenAPI openAPI() {
        OpenAPI openApi = new OpenAPI().components(components()).addSecurityItem(securityRequirement());

        return openApi;
    }

    private SecurityRequirement securityRequirement() {
        SecurityRequirement securityRequirement = new SecurityRequirement().addList(SCHEME_NAME);

        return securityRequirement;
    }

    private Components components() {
        Components components = new Components().addSecuritySchemes(SCHEME_NAME, securitySecheme());

        return components;
    }

    private SecurityScheme securitySecheme() {
        SecurityScheme securityScheme = new SecurityScheme().name(SCHEME_NAME)
                .type(Type.APIKEY)
                .in(In.HEADER)
                .scheme(SCHEME)
                .bearerFormat(BEARER_FORMAT);

        return securityScheme;
    }

}
