package com.roman.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EmployeeBriefInfoDto {

    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "lastName")
    private String lastName;

    @JsonProperty(value = "firstName")
    private String firstName;

    @JsonProperty(value = "position")
    private String position;

}