package com.roman.resource;

import dto.employee.EmployeeBriefInfoDto;
import dto.employee.EmployeeDto;
import dto.employee.EmployeeFullInfoDto;
import dto.employee.OnCreate;
import dto.employee.OnUpdate;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@RequestMapping(value = "/employees")
public interface EmployeeController {

    @GetMapping
    @ApiOperation(value = "Return all employees")
    List<EmployeeFullInfoDto> findAll();

    @PostMapping
    @ApiOperation(value = "Create a new employee")
    EmployeeFullInfoDto create(@Validated(value = OnCreate.class) @RequestBody EmployeeDto employee);

    @PutMapping
    @ApiOperation(value = "Update an employee")
    EmployeeFullInfoDto update(@Validated(value = OnUpdate.class) @RequestBody EmployeeDto employeeDTO);

    @GetMapping(value = "/{employeeId}")
    @ApiOperation(value = "Return an employee by id")
    EmployeeFullInfoDto findById(@PathVariable Long employeeId);

    @DeleteMapping(value = "/{employeeId}")
    @ApiOperation(value = "Dismissal an employee by id")
    void dismissal(@PathVariable Long employeeId);

    @PutMapping(value = "/{employeeId}/transfer/{departmentId}")
    @ApiOperation(value = "Transfer an employee to another department")
    EmployeeFullInfoDto transferEmployee(@PathVariable Long employeeId, @PathVariable Long departmentId);

    @PutMapping(value = "/{sourceDepartmentId}/transfer-all/{targetDepartmentId}")
    @ApiOperation(value = "Transfer all employees from one department  to another department")
    void transferAllEmployees(@PathVariable Long sourceDepartmentId, @PathVariable Long targetDepartmentId);

    @GetMapping(value = "/{employeeId}/head")
    @ApiOperation(value = "Getting information about the employee's head")
    EmployeeFullInfoDto findHeadOfEmployee(@PathVariable Long employeeId);

    @GetMapping(value = "/find-by-email")
    @ApiOperation(value = "Find employee by email")
    EmployeeFullInfoDto findHeadOfEmployee(@RequestParam String email);

    @GetMapping(value = "/filter")
    @ApiOperation(value = "Return a List of heads if isHead=true\n" +
            "\n" +
            "and a List of employees who are not heads if isHead=false")
    List<EmployeeBriefInfoDto> getEmployeesListByParam(@RequestParam boolean isHead);

    @GetMapping(value = "/department/{departmentId}")
    @ApiOperation(value = "Returns a list of department employees")
    List<EmployeeFullInfoDto> findByDepartmentId(@PathVariable Long departmentId);


}
