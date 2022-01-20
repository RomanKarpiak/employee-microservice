package com.roman.resource.feign.fallback;

import com.roman.resource.feign.DepartmentsFeignClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DepartmentsFallbackFactory implements FallbackFactory<DepartmentsFeignClient> {
    @Override
    public DepartmentsFeignClient create(Throwable throwable) {
        return new DepartmentsFeignClientFallback(throwable);
    }
}
