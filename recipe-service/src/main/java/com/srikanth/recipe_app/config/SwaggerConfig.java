package com.srikanth.recipe_app.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Recipe Management Service")
                        .description("description")
                        .version("1.0")
                        .license(new License()
                                .name("license")
                                .url("url")
                        )
                )
                .components(new Components()
                        .addSecuritySchemes("RecipeSecurityScheme", securityScheme("X-API-KEY"))
                )
                .addSecurityItem(new SecurityRequirement()
                        .addList("RecipeSecurityScheme")
                );
    }

    private SecurityScheme securityScheme(String name) {
        return new io.swagger.v3.oas.models.security.SecurityScheme()
                .type(io.swagger.v3.oas.models.security.SecurityScheme.Type.APIKEY)
                .in(io.swagger.v3.oas.models.security.SecurityScheme.In.HEADER)
                .name(name);
    }
}
