package com.roman.exceptions;

public class TwoHeadOfDepartmentException extends RuntimeException {
    public TwoHeadOfDepartmentException() {
        super("There can't be two head in a department");
    }
}