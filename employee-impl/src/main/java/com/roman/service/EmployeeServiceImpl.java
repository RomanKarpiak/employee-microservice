package com.roman.service;

import com.roman.entity.Employee;
import com.roman.exceptions.DepartmentNotFoundException;
import com.roman.exceptions.EmployeeAlreadyExistsException;
import com.roman.exceptions.EmployeeNotFoundException;
import com.roman.exceptions.TwoHeadOfDepartmentException;
import com.roman.mappers.DepartmentDtoSnapshotMapper;
import com.roman.repo.DepartmentDtoSnapshotRepo;
import com.roman.repo.EmployeeRepo;
import com.roman.resource.feign.DepartmentsFeignClient;
import dto.department.DepartmentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepo employeeRepo;
    private final DepartmentsFeignClient feignClient;
    private final DepartmentDtoSnapshotRepo dtoSnapshotRepo;
    private final DepartmentDtoSnapshotMapper mapper;

    @Override
    public List<Employee> findAll() {
        return employeeRepo.findAll();
    }

    @Override
    public Employee create(Employee employee) {
        boolean isExist = employeeRepo.findByEmail(employee.getEmail()).isPresent();
        if (isExist) {
            throw new EmployeeAlreadyExistsException();
        } else {
            return employeeRepo.save(employee);
        }
    }

    @Override
    public void update(Employee employee) {
        employeeRepo.save(employee);
    }

    @Override
    public Employee findById(Long employeeId) {
        return employeeRepo.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));
    }

    @Override
    public void dismissal(Long employeeId) {
        Employee employee = findById(employeeId);
        employee.setDepartmentId(null);
        employee.setDateOfDismissal(LocalDate.now());
        employeeRepo.save(employee);
    }

    @Override
    public void transferEmployee(Long employeeId, Long departmentId) {
        Employee employee = findById(employeeId);
        checkExistenceDepartments(departmentId);
        Employee headOfDepartment = employeeRepo.findByDepartmentIdAndIsHeadOfDepartmentTrue(departmentId);
        if (headOfDepartment != null && employee.isHeadOfDepartment()) {
            throw new TwoHeadOfDepartmentException();
        } else {
            employee.setDepartmentId(departmentId);
            employeeRepo.save(employee);
        }
    }

    @Override
    @Transactional
    public void transferAllEmployee(Long sourceDepartmentId, Long targetDepartmentId) {
        checkExistenceDepartments(sourceDepartmentId);
        checkExistenceDepartments(targetDepartmentId);
        List<Employee> employeeList = getEmployeeListByDepartmentId(sourceDepartmentId);
        employeeList.forEach(employee -> transferEmployee(employee.getId(), targetDepartmentId));
    }

    @Override
    public Employee getEmployeesHead(Long employeeId) {
        Employee employee = findById(employeeId);
        return employeeRepo.findByDepartmentIdAndIsHeadOfDepartmentTrue(employee.getDepartmentId());
    }

    @Override
    public Employee findByEmail(String email) {
        return employeeRepo.findByEmail(email).orElseThrow(() -> new EmployeeNotFoundException(email));
    }

    @Override
    public List<Employee> getEmployeeListByParam(boolean isHead) {
        return employeeRepo.findByIsHeadOfDepartment(isHead);
    }

    @Override
    public List<Employee> getEmployeeListByDepartmentId(Long departmentId) {
        return employeeRepo.findByDepartmentId(departmentId);
    }

    private void checkExistenceDepartments(Long departmentId) {
        if (dtoSnapshotRepo.findByDepartmentId(departmentId) == null) {
            DepartmentDto departmentDto = feignClient.getDepartmentById(departmentId);
            if (departmentDto == null) {
                throw new DepartmentNotFoundException(departmentId);
            } else {
                dtoSnapshotRepo.save(mapper.toDtoSnapshot(departmentDto));
            }
        }
    }
}
