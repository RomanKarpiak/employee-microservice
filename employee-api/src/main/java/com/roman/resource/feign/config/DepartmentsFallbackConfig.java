package com.roman.resource.feign.config;

import com.roman.resource.feign.fallback.DepartmentsFallbackFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DepartmentsFallbackConfig {

    @Bean
    public DepartmentsFallbackFactory departmentsFallbackFactory(){
        return new DepartmentsFallbackFactory();
    }

}
