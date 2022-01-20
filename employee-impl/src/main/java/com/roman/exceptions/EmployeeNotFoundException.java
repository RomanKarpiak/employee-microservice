package com.roman.exceptions;

public class EmployeeNotFoundException extends RuntimeException {

    public EmployeeNotFoundException(Long id) {
        super("Employee with " + id + " not found");
    }
    public EmployeeNotFoundException(String email) {
        super("Employee with " + email + " not found");
    }
}