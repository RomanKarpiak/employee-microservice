package com.roman.resource.feign;

import com.roman.resource.feign.fallback.DepartmentsFallbackFactory;
import dto.department.DepartmentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "departments-service", fallbackFactory = DepartmentsFallbackFactory.class)
public interface DepartmentsFeignClient {

    @GetMapping(value = "/departments/feign-client/{departmentId}")
    DepartmentDto getDepartmentById(@PathVariable Long departmentId);
}
