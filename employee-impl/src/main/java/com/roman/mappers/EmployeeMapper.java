package com.roman.mappers;

import com.roman.entity.Employee;
import dto.employee.EmployeeBriefInfoDto;
import dto.employee.EmployeeDto;
import dto.employee.EmployeeFullInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {


    @Mapping(target = "departmentId", source = "employee", qualifiedByName = "getDepartmentId")
    EmployeeFullInfoDto toDto(Employee employee);

    EmployeeBriefInfoDto toBriefDto(Employee employee);

    @Mapping(target = "departmentId", expression = "java(employeeDto.getDepartmentId())")
    Employee toEntity(EmployeeDto employeeDto);

    @Named("getDepartmentId")
    default Long getDepartmentId(Employee employee) {
        if (employee.getDepartmentId() == null) {
            return null;
        } else {
            return employee.getDepartmentId();
        }

    }
}
