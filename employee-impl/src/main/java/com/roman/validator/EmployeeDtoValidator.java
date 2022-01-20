package com.roman.validator;

import com.roman.dto.EmployeeDto;
import com.roman.entity.Employee;
import com.roman.exceptions.EmployeeValidationException;
import com.roman.exceptions.TwoHeadOfDepartmentException;
import com.roman.repo.EmployeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class EmployeeDtoValidator {


    private final EmployeeRepo employeeRepo;

    @Autowired
    public EmployeeDtoValidator(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }

    public void validate(Object target) {

        EmployeeDto employee = (EmployeeDto) target;
        Employee headOfDepartment = employeeRepo.findByDepartmentIdAndIsHeadOfDepartmentTrue(employee.getDepartmentId());
        if ((headOfDepartment == null && employee.isHeadOfDepartment()) && (employee.getSalary() - getMaxSalaryOfEmployeesOfDepartment(employee) < 0)) {
            throw new EmployeeValidationException("The salary of the head cannot be less than the salary of the employee");
        } else if ((headOfDepartment != null && !employee.isHeadOfDepartment()) && (getSalaryOfHeadOfDepartment(employee) - employee.getSalary() < 0)) {
            throw new EmployeeValidationException("The salary of an employee may not exceed the salary of the head");
        }
        if (headOfDepartment != null && !headOfDepartment.getEmail().equals(employee.getEmail()) && employee.isHeadOfDepartment()) {
            throw new TwoHeadOfDepartmentException();
        }
        if (!dismissalDateValidation(employee)) {
            throw new EmployeeValidationException("Dismissal date must be greater than admission date");
        }
        if (!admissionDateValidation(employee)) {
            throw new EmployeeValidationException("Admission date must be more than date of birthday");
        }

    }

    private boolean dismissalDateValidation(EmployeeDto employee) {
        LocalDate admissionDate = employee.getDateOfAdmission();
        LocalDate dismissalDate = employee.getDateOfDismissal();
        if (dismissalDate == null) {
            return true;
        }
        return dismissalDate.isAfter(admissionDate)
                && (dismissalDate.isBefore(LocalDate.now()) || dismissalDate.equals(LocalDate.now()));
    }

    private boolean admissionDateValidation(EmployeeDto employee) {
        LocalDate admissionDate = employee.getDateOfAdmission();
        LocalDate birthday = employee.getBirthday();
        return admissionDate.isAfter(birthday)
                && (admissionDate.isBefore(LocalDate.now()) || admissionDate.equals(LocalDate.now()));
    }


    private Long getSalaryOfHeadOfDepartment(EmployeeDto employee) {
        return employeeRepo.getSalaryOfHeadOfDepartment(employee.getDepartmentId());
    }

    private Long getMaxSalaryOfEmployeesOfDepartment(EmployeeDto employee) {
        return employeeRepo.getMaxSalaryOfEmployeesOfDepartment(employee.getDepartmentId());

    }
}
