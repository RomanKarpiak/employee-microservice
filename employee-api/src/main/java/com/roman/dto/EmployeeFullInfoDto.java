package com.roman.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.roman.enums.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EmployeeFullInfoDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty(value = "lastName")
    private String lastName;

    @JsonProperty(value = "firstName")
    private String firstName;

    @JsonProperty(value = "patronymic")
    private String patronymic;

    @JsonProperty(value = "gender")
    private Gender gender;

    @JsonProperty(value = "birthday")
    private LocalDate birthday;

    @JsonProperty(value = "phone")
    private String phone;

    @JsonProperty(value = "email")
    private String email;

    @JsonProperty(value = "dateOfAdmission")
    private LocalDate dateOfAdmission;

    @JsonProperty(value = "dateOfDismissal")
    private LocalDate dateOfDismissal;

    @JsonProperty(value = "position")
    private String position;

    @JsonProperty(value = "salary")
    private Long salary;

    @JsonProperty(value = "isHeadOfDepartment")
    private boolean isHeadOfDepartment;

    @JsonProperty(value = "departmentId")
    private Long departmentId;
}
