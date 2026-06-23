package com.mkcl.moviebooking.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI movieBookingAPI() {

        return new OpenAPI()
                .info(
                        new Info()
                                .title("Movie Booking API")
                                .version("1.0")
                                .description(
                                        "Movie Booking System APIs"
                                )
                );
    }
}