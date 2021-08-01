package com.hoang.backend_learning.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hoang.backend_learning.dto.RestResponseDto;
import org.keycloak.adapters.AdapterDeploymentContext;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationEntryPoint;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RestAuthenticationEntryPoint extends KeycloakAuthenticationEntryPoint {
    private final ObjectMapper mapper = new ObjectMapper();

    public RestAuthenticationEntryPoint(AdapterDeploymentContext adapterDeploymentContext) {
        super(adapterDeploymentContext);
    }

    @Override
    protected void commenceUnauthorizedResponse(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RestResponseDto<Object> restResponseDto = new RestResponseDto<>().unauthorized();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_OK);
        response.getOutputStream().print(mapper.writeValueAsString(restResponseDto));
    }
}
