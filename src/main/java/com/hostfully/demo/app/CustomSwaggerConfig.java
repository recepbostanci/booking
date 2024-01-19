package com.hostfully.demo.app;

import io.swagger.v3.oas.models.OpenAPI;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 * activate swagger conditionally
 * </p>
 *
 * @author recepb
 */
@Configuration
@AllArgsConstructor
@ConditionalOnProperty(name = "springdoc.swagger-ui.enabled", havingValue = "true", matchIfMissing = true)
public class CustomSwaggerConfig {

    @Bean
    public OpenAPI api() {
        return new OpenAPI();
    }

}

