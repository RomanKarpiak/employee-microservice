package com.roman.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@ToString
@Table(name = "departments_snapshot")
@Entity
public class DepartmentDtoSnapshot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "snapshot_id")
    private Long snapshotId;

    @Column(name = "department_id")
    private Long departmentId;

    @Column(name = "department_name")
    private String departmentName;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "main_department_id")
    private Long mainDepartment;


}
