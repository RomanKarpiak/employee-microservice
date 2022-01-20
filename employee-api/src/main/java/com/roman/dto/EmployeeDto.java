package com.roman.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.roman.enums.Gender;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class EmployeeDto {

    @Null(groups = OnCreate.class, message = "There should not be an id")
    @NotNull(groups = OnUpdate.class, message = "Add an id")
    @JsonProperty("id")
    private Long id;


    @NotBlank(message = "Please enter the LastName", groups = {OnCreate.class, OnUpdate.class})
    @Size(min = 2, max = 30, message = "The Last name should be between 2 and 30 characters", groups = {OnCreate.class, OnUpdate.class})
    @Pattern(regexp = "[А-Яа-яЁё-]*", message = "You can use the following characters: [А-Яа-яЁё-]*", groups = {OnCreate.class, OnUpdate.class})
    @JsonProperty("lastName")
    private String lastName;

    @NotBlank(message = "Please enter the FirstName", groups = {OnCreate.class, OnUpdate.class})
    @Size(min = 2, max = 30, message = "First name should be between 2 and 30 characters", groups = {OnCreate.class, OnUpdate.class})
    @Pattern(regexp = "[А-Яа-яЁё-]*", message = "You can use the following characters: [А-Яа-яЁё-]*", groups = {OnCreate.class, OnUpdate.class})
    @JsonProperty("firstName")
    private String firstName;


    @Pattern(regexp = "[А-Яа-яЁё-]*", message = "You can use the following characters: [А-Яа-яЁё-]*", groups = {OnCreate.class, OnUpdate.class})
    @Size(min = 2, message = "Patronymic should be between 2 and 30 characters", groups = {OnCreate.class, OnUpdate.class})
    @JsonProperty("patronymic")
    private String patronymic;

    @JsonProperty(value = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;


    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("birthday")
    private LocalDate birthday;


    @Pattern(regexp = "^\\+?[78][-(]?\\d{3}\\)?[ -]?\\d{3}[ -]?\\d{2}[ -]?\\d{2}$", message = "The Phone number is not valid", groups = {OnCreate.class, OnUpdate.class})
    @NotBlank(groups = {OnCreate.class, OnUpdate.class})
    @JsonProperty("phone")
    private String phone;

    @NotBlank(groups = {OnCreate.class, OnUpdate.class})
    @Email(message = "Email should be valid", regexp = "^[A-Za-z0-9+_.-]+@(.+)$", groups = {OnCreate.class, OnUpdate.class})
    @JsonProperty("email")
    private String email;


    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("dateOfAdmission")
    private LocalDate dateOfAdmission;


    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonProperty("dateOfDismissal")
    private LocalDate dateOfDismissal;

    @NotBlank(groups = {OnCreate.class, OnUpdate.class})
    @JsonProperty("position")
    @Size(min = 2, message = "Position should be between 2 and 30 characters", groups = {OnCreate.class, OnUpdate.class})
    private String position;

    @JsonProperty("salary")
    @Positive(groups = {OnCreate.class, OnUpdate.class})
    private Long salary;

    @NotNull(groups = {OnCreate.class, OnUpdate.class})
    @JsonProperty("isHeadOfDepartment")
    private boolean isHeadOfDepartment;

    @JsonProperty("departmentId")
    @Positive(groups = {OnCreate.class, OnUpdate.class})
    private Long departmentId;
}
