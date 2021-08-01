package com.hoang.backend_learning.config;

import com.google.common.collect.Lists;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            // Thiết lập các server dùng để test api
            .servers(Lists.newArrayList(
                new Server().url("http://localhost:8080")
            ))
            //Info
            .info(new Info().title("ZANE - MANAGEMENT")
                .description("Designed by ZANE")
                .version("1.0.0"));
    }
}
