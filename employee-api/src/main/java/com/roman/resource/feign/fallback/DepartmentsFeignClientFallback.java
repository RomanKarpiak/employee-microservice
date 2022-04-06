package com.roman.resource.feign.fallback;

import com.roman.resource.feign.DepartmentsFeignClient;
import com.roman.resource.feign.fallback.exception.FallbackException;
import dto.department.DepartmentDto;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DepartmentsFeignClientFallback implements DepartmentsFeignClient {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Throwable throwable;

    public DepartmentsFeignClientFallback(Throwable throwable) {
        this.throwable = throwable;
    }

    @Override
    public DepartmentDto getDepartmentById(Long departmentId) {
        if (throwable instanceof FeignException && ((FeignException) throwable).status() == 404) {
            logger.error("404 page not found" + departmentId
                    + "error message: " + throwable.getLocalizedMessage());
        } else {
            logger.error("Other error took place: " + throwable.getLocalizedMessage());
        }
        throw new FallbackException("Using fallback method for getDepartmentById method because of exception - " + throwable.getLocalizedMessage());
    }
}
