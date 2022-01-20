package com.roman.mappers;

import com.roman.dto.EmployeeBriefInfoDto;
import com.roman.dto.EmployeeDto;
import com.roman.dto.EmployeeFullInfoDto;
import com.roman.entity.Employee;
import javax.annotation.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-07-25T20:04:41+0400",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 1.8.0_291 (Oracle Corporation)"
)
@Component
public class EmployeeMapperImpl implements EmployeeMapper {

    @Override
    public EmployeeFullInfoDto toDto(Employee employee) {
        if ( employee == null ) {
            return null;
        }

        EmployeeFullInfoDto employeeFullInfoDto = new EmployeeFullInfoDto();

        employeeFullInfoDto.setDepartmentId( getDepartmentId( employee ) );
        employeeFullInfoDto.setId( employee.getId() );
        employeeFullInfoDto.setLastName( employee.getLastName() );
        employeeFullInfoDto.setFirstName( employee.getFirstName() );
        employeeFullInfoDto.setPatronymic( employee.getPatronymic() );
        employeeFullInfoDto.setGender( employee.getGender() );
        employeeFullInfoDto.setBirthday( employee.getBirthday() );
        employeeFullInfoDto.setPhone( employee.getPhone() );
        employeeFullInfoDto.setEmail( employee.getEmail() );
        employeeFullInfoDto.setDateOfAdmission( employee.getDateOfAdmission() );
        employeeFullInfoDto.setDateOfDismissal( employee.getDateOfDismissal() );
        employeeFullInfoDto.setPosition( employee.getPosition() );
        employeeFullInfoDto.setSalary( employee.getSalary() );
        employeeFullInfoDto.setHeadOfDepartment( employee.isHeadOfDepartment() );

        return employeeFullInfoDto;
    }

    @Override
    public EmployeeBriefInfoDto toBriefDto(Employee employee) {
        if ( employee == null ) {
            return null;
        }

        EmployeeBriefInfoDto employeeBriefInfoDto = new EmployeeBriefInfoDto();

        employeeBriefInfoDto.setId( employee.getId() );
        employeeBriefInfoDto.setLastName( employee.getLastName() );
        employeeBriefInfoDto.setFirstName( employee.getFirstName() );
        employeeBriefInfoDto.setPosition( employee.getPosition() );

        return employeeBriefInfoDto;
    }

    @Override
    public Employee toEntity(EmployeeDto employeeDto) {
        if ( employeeDto == null ) {
            return null;
        }

        Employee employee = new Employee();

        employee.setId( employeeDto.getId() );
        employee.setLastName( employeeDto.getLastName() );
        employee.setFirstName( employeeDto.getFirstName() );
        employee.setPatronymic( employeeDto.getPatronymic() );
        employee.setGender( employeeDto.getGender() );
        employee.setBirthday( employeeDto.getBirthday() );
        employee.setPhone( employeeDto.getPhone() );
        employee.setEmail( employeeDto.getEmail() );
        employee.setDateOfAdmission( employeeDto.getDateOfAdmission() );
        employee.setDateOfDismissal( employeeDto.getDateOfDismissal() );
        employee.setPosition( employeeDto.getPosition() );
        employee.setSalary( employeeDto.getSalary() );
        employee.setHeadOfDepartment( employeeDto.isHeadOfDepartment() );

        employee.setDepartmentId( employeeDto.getDepartmentId() );

        return employee;
    }
}
