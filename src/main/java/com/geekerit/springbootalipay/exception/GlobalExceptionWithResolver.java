package com.geekerit.springbootalipay.exception;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.util.Properties;

@Configuration
public class GlobalExceptionWithResolver {

    @Bean
    public SimpleMappingExceptionResolver getResolver(){
        SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();

        Properties mappings = new Properties();
        mappings.put("java.lang.NullPointerException","error");

        resolver.setExceptionMappings(mappings);

        return  resolver;
    }
}
