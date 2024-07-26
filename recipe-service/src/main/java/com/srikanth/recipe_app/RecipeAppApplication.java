package com.srikanth.recipe_app;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info = @Info(title = "Recipe Management Service", version = "1.0"))
@SecurityScheme(type = SecuritySchemeType.APIKEY, name = "X-API-KEY", in = SecuritySchemeIn.HEADER)
@SpringBootApplication
public class RecipeAppApplication {
	public static void main(String[] args) {
		SpringApplication.run(RecipeAppApplication.class, args);
	}
}
