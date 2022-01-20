package com.roman.resource;

import com.roman.dto.EmployeeBriefInfoDto;
import com.roman.dto.EmployeeDto;
import com.roman.dto.EmployeeFullInfoDto;
import com.roman.entity.Employee;
import com.roman.mappers.EmployeeMapper;
import com.roman.service.EmployeeService;
import com.roman.validator.EmployeeDtoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
public class EmployeeControllerImpl implements EmployeeController {

    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;
    private final EmployeeDtoValidator validator;

    @Override
    public EmployeeFullInfoDto create(EmployeeDto employeeDto) {
        validator.validate(employeeDto);
        Employee employee = employeeService.create(employeeMapper.toEntity(employeeDto));
        return employeeMapper.toDto(employee);
    }

    @Override
    public EmployeeFullInfoDto update(EmployeeDto employeeDto) {
        validator.validate(employeeDto);
        Employee employee = employeeMapper.toEntity(employeeDto);
        employeeService.update(employee);
        return employeeMapper.toDto(employee);
    }

    @Override
    public EmployeeFullInfoDto findById(Long employeeId) {
        Employee employee = employeeService.findById(employeeId);
        return employeeMapper.toDto(employee);
    }

    @Override
    public void dismissal(Long employeeId) {
        employeeService.dismissal(employeeId);
    }


    @Override
    public EmployeeFullInfoDto transferEmployee(Long employeeId, Long departmentId) {
        Employee employee = employeeService.findById(employeeId);
        employeeService.transferEmployee(employeeId, departmentId);
        return employeeMapper.toDto(employee);
    }

    @Override
    public void transferAllEmployees(Long sourceDepartmentId, Long targetDepartmentId) {
        employeeService.transferAllEmployee(sourceDepartmentId, targetDepartmentId);
    }

    @Override
    public EmployeeFullInfoDto findHeadOfEmployee(Long employeeId) {
        Employee employee = employeeService.getEmployeesHead(employeeId);
        return employeeMapper.toDto(employee);
    }

    @Override
    public EmployeeFullInfoDto findHeadOfEmployee(String email) {
        Employee employee = employeeService.findByEmail(email);
        return employeeMapper.toDto(employee);
    }

    @Override
    public List<EmployeeBriefInfoDto> getEmployeesListByParam(boolean isHead) {
        List<Employee> employeeList = employeeService.getEmployeeListByParam(isHead);
        return employeeList.stream()
                .map(employeeMapper::toBriefDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeFullInfoDto> findByDepartmentId(Long departmentId) {
        List<Employee> employeeList = employeeService.getEmployeeListByDepartmentId(departmentId);
        return employeeList.stream()
                .map(employeeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeFullInfoDto> findAll() {
        List<Employee> employees = employeeService.findAll();
        return employees.stream()
                .map(employeeMapper::toDto)
                .collect(Collectors.toList());
    }
}
