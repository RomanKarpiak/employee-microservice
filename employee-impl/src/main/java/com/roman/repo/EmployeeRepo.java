package com.roman.repo;

import com.roman.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String email);

    @Query("SELECT max(e.salary) FROM Employee e WHERE e.departmentId = :departmentId AND e.isHeadOfDepartment = 'false'")
    Long getMaxSalaryOfEmployeesOfDepartment(@Param("departmentId") Long departmentId);

    @Query("SELECT e.salary FROM Employee e WHERE e.departmentId = :departmentId AND e.isHeadOfDepartment = 'true'")
    Long getSalaryOfHeadOfDepartment(@Param("departmentId") Long departmentId);


    List<Employee> findByIsHeadOfDepartment(boolean isHeadOfDepartment);

    @Query("SELECT e FROM Employee e WHERE e.departmentId = :departmentId AND e.isHeadOfDepartment = 'true'")
    Employee findByDepartmentIdAndIsHeadOfDepartmentTrue(@Param("departmentId") Long departmentId);


    List<Employee> findByDepartmentId(Long departmentId);

}
