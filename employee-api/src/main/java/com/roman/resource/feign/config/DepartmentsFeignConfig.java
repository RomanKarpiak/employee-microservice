package com.roman.resource.feign.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({DepartmentsFallbackConfig.class})
public class DepartmentsFeignConfig {

}
