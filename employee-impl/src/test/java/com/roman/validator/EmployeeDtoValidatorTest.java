package com.roman.validator;

import com.roman.entity.Employee;
import com.roman.exceptions.EmployeeValidationException;
import com.roman.exceptions.TwoHeadOfDepartmentException;
import com.roman.repo.EmployeeRepo;
import dto.employee.EmployeeDto;
import dto.employee.enums.Gender;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = EmployeeDtoValidator.class)
public class EmployeeDtoValidatorTest {

    @Autowired
    private EmployeeDtoValidator validator;

    @MockBean
    private EmployeeRepo employeeRepo;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private List<EmployeeDto> employeeList;


    @Test
    public void testMockCreation() {

        assertNotNull(employeeRepo);
        assertNotNull(validator);
    }

    @Before
    public void setUp() {
        employeeList = new ArrayList<>();
        EmployeeDto employee1 = new EmployeeDto();
        employee1.setId(1L);
        employee1.setFirstName("Александр");
        employee1.setLastName("Бородин");
        employee1.setPatronymic("Порфирьевич");
        employee1.setGender(Gender.MALE);
        employee1.setBirthday(LocalDate.of(1996, 11, 1));
        employee1.setPhone("8-555-555-5554");
        employee1.setPosition("President");
        employee1.setDepartmentId(1L);
        employee1.setDateOfAdmission(LocalDate.of(2013, 11, 1));
        employee1.setDateOfDismissal(null);
        employee1.setEmail("borodin@gmail.com");
        employee1.setHeadOfDepartment(true);
        employee1.setSalary(1000000L);

        EmployeeDto employee2 = new EmployeeDto();
        employee2.setId(2L);
        employee2.setFirstName("Григорий");
        employee2.setLastName("Кауфманн");
        employee2.setPatronymic("Николаевич");
        employee2.setGender(Gender.MALE);
        employee2.setBirthday(LocalDate.of(1994, 11, 1));
        employee2.setPhone("8-222-555-5554");
        employee2.setPosition("Development");
        employee2.setDepartmentId(1L);
        employee2.setDateOfAdmission(LocalDate.of(2017, 11, 1));
        employee2.setDateOfDismissal(null);
        employee2.setEmail("kaufmann@gmail.com");
        employee2.setHeadOfDepartment(false);
        employee2.setSalary(1000L);

        EmployeeDto employee3 = new EmployeeDto();
        employee3.setId(3L);
        employee3.setFirstName("Мария");
        employee3.setLastName("Солнечная");
        employee3.setPatronymic("Сергеевна");
        employee3.setGender(Gender.FEMALE);
        employee3.setBirthday(LocalDate.of(1987, 11, 1));
        employee3.setPhone("8-111-454-5554");
        employee3.setPosition("Development");
        employee3.setDepartmentId(1L);
        employee3.setDateOfAdmission(LocalDate.of(2014, 8, 1));
        employee3.setDateOfDismissal(null);
        employee3.setEmail("sun@gmail.com");
        employee3.setHeadOfDepartment(false);
        employee3.setSalary(2000L);

        employeeList.add(employee1);
        employeeList.add(employee2);
        employeeList.add(employee3);

        Assert.assertEquals(3, employeeList.size());
    }

    @Test
    public void whenSalaryOfHeadLessThanSalaryOfRegularEmployeeShouldReturnEmployeeValidationException() {
        EmployeeDto employee = employeeList.get(0);

        when(employeeRepo.findByDepartmentIdAndIsHeadOfDepartmentTrue(employee.getDepartmentId())).thenReturn(null);
        when(employeeRepo.getMaxSalaryOfEmployeesOfDepartment(employee.getDepartmentId())).thenReturn(200000000L);
        assertTrue(employee.isHeadOfDepartment());
        assertTrue((employee.getSalary() - employeeRepo.getMaxSalaryOfEmployeesOfDepartment(employee.getDepartmentId()) ) < 0);

        thrown.expect(EmployeeValidationException.class);
        thrown.expectMessage("The salary of the head cannot be less than the salary of the employee");

        validator.validate(employee);
    }

    @Test
    public void whenSalaryOfRegularEmployeeGreaterThanSalaryOfHeadShouldReturnEmployeeValidationException() {
        EmployeeDto employee = employeeList.get(2);

        when(employeeRepo.findByDepartmentIdAndIsHeadOfDepartmentTrue(employee.getDepartmentId())).thenReturn(new Employee());
        when(employeeRepo.getSalaryOfHeadOfDepartment(employee.getDepartmentId())).thenReturn(900L);

        assertNotNull(employeeRepo.findByDepartmentIdAndIsHeadOfDepartmentTrue(employee.getDepartmentId()));
        assertFalse(employee.isHeadOfDepartment());
        assertTrue((employeeRepo.getSalaryOfHeadOfDepartment(employee.getDepartmentId()) - employee.getSalary() ) < 0);

        thrown.expect(EmployeeValidationException.class);
        thrown.expectMessage("The salary of an employee may not exceed the salary of the head");

        validator.validate(employee);
    }

    @Test
    public void whenTwoHeadOfDepartmentShouldReturnTwoHeadOfDepartmentException(){
        EmployeeDto employee = employeeList.get(2);
        employee.setHeadOfDepartment(true);
        employee.setEmail("emp@gmail.com");

        Employee headOfDepartment = new Employee();
        headOfDepartment.setEmail("head@gmail.com");
        headOfDepartment.setHeadOfDepartment(true);

        when(employeeRepo.findByDepartmentIdAndIsHeadOfDepartmentTrue(employee.getDepartmentId())).thenReturn(headOfDepartment);

        assertNotNull(employeeRepo.findByDepartmentIdAndIsHeadOfDepartmentTrue(employee.getDepartmentId()));
        assertTrue(headOfDepartment.isHeadOfDepartment());
        assertTrue(employee.isHeadOfDepartment());

        thrown.expect(TwoHeadOfDepartmentException.class);
        thrown.expectMessage("There can't be two head in a department");

        validator.validate(employee);

    }

    @Test
    public void whenDismissalDateComesBeforeAdmissionDateShouldReturnEmployeeValidationException(){
        EmployeeDto employee = employeeList.get(2);
        employee.setDateOfDismissal(LocalDate.of(2013,11,11));

        thrown.expect(EmployeeValidationException.class);
        thrown.expectMessage("Dismissal date must be greater than admission date");

        validator.validate(employee);

    }

    @Test
    public void whenBComesBeforeAdmissionDateShouldReturnEmployeeValidationException(){
        EmployeeDto employee = employeeList.get(2);
        employee.setDateOfAdmission(LocalDate.of(1939,11,11));

        thrown.expect(EmployeeValidationException.class);
        thrown.expectMessage("Admission date must be more than date of birthday");

        validator.validate(employee);

    }

}