package com.edusyspro.api.config;

import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EdusysproConfig {
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizer() {
        return builder -> builder.featuresToDisable(
                SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED
        );
    }
}
