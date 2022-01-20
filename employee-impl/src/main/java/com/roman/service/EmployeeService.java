package com.roman.service;

import com.roman.entity.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> findAll();

    Employee create(Employee employee);

    void update(Employee employee);

    Employee findById(Long employeeId);

    void dismissal(Long employeeId);

    void transferAllEmployee(Long sourceDepartmentId, Long targetDepartmentId);

    void transferEmployee(Long employeeId, Long departmentId);

    Employee getEmployeesHead(Long employeeId);

    Employee findByEmail(String email);

    List<Employee> getEmployeeListByParam(boolean isHead);

    List<Employee> getEmployeeListByDepartmentId(Long departmentId);
}
