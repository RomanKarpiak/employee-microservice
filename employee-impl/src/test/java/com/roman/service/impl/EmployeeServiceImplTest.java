package com.roman.service.impl;

import com.roman.entity.DepartmentDtoSnapshot;
import com.roman.entity.Employee;
import com.roman.exceptions.DepartmentNotFoundException;
import com.roman.exceptions.EmployeeAlreadyExistsException;
import com.roman.exceptions.EmployeeNotFoundException;
import com.roman.exceptions.TwoHeadOfDepartmentException;
import com.roman.mappers.DepartmentDtoSnapshotMapper;
import com.roman.repo.DepartmentDtoSnapshotRepo;
import com.roman.repo.EmployeeRepo;
import com.roman.resource.feign.DepartmentsFeignClient;
import com.roman.resource.feign.fallback.exception.FallbackException;
import com.roman.service.EmployeeServiceImpl;
import dto.department.DepartmentDto;
import dto.employee.enums.Gender;
import org.apache.commons.collections.CollectionUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = EmployeeServiceImpl.class)
public class EmployeeServiceImplTest {

    @Autowired
    private EmployeeServiceImpl employeeService;

    @MockBean
    private EmployeeRepo employeeRepo;

    @MockBean
    private DepartmentsFeignClient feignClient;

    @MockBean
    private DepartmentDtoSnapshotRepo dtoSnapshotRepo;

    @MockBean
    private DepartmentDtoSnapshotMapper mapper;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private List<Employee> employeeList;


    @Test
    public void testMockCreation() {
        assertNotNull(employeeRepo);
        assertNotNull(employeeService);
        assertNotNull(feignClient);
        assertNotNull(dtoSnapshotRepo);
        assertNotNull(mapper);

    }

    @Before
    public void setUp() {
        employeeList = new ArrayList<>();
        Employee employee1 = new Employee();
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

        Employee employee2 = new Employee();
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

        Employee employee3 = new Employee();
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

        employeeList.add(employee1);
        employeeList.add(employee2);
        employeeList.add(employee3);

        Assert.assertEquals(3, employeeList.size());
    }

    @Test
    public void whenCreateEmployeeShouldReturnEmployee() {
        Employee employee = employeeList.get(1);

        Mockito.when(employeeRepo.findByEmail(employee.getEmail())).thenReturn(Optional.empty());
        Mockito.when(employeeRepo.save(employee)).thenReturn(employee);
        Employee created = employeeService.create(employee);

        Assert.assertEquals(employee, created);

        verify(employeeRepo, times(1)).findByEmail(employee.getEmail());
        verify(employeeRepo, times(1)).save(employee);

    }

    @Test(expected = EmployeeAlreadyExistsException.class)
    public void whenEmployeeExistsShouldReturnEmployeeAlreadyExistsException() {
        Employee employee = employeeList.get(1);

        Mockito.when(employeeRepo.findByEmail(employee.getEmail())).thenReturn(Optional.of(employee));
        employeeService.create(employee);

    }

    @Test
    public void shouldReturnAllEmployees() {
        Mockito.when(employeeRepo.findAll()).thenReturn(employeeList);
        List<Employee> employees = employeeService.findAll();
        assertEquals(3, employees.size());
        verify(employeeRepo, times(1)).findAll();
    }

    @Test
    public void whenGivenIdShouldReturnEmployeeIfFound() {
        Mockito.when(employeeRepo.findById(1L)).thenReturn(Optional.of(employeeList.get(0)));
        employeeService.findById(1L);
        verify(employeeRepo, times(1)).findById(1L);
    }

    @Test(expected = EmployeeNotFoundException.class)
    public void whenEmployeeDoesntExistShouldThrowEmployeeNotFoundException() {
        Mockito.when(employeeRepo.findById(5L)).thenThrow(new EmployeeNotFoundException(5L));
        employeeService.findById(5L);
    }

    @Test
    public void whenGivenIdShouldSetDepartmentIdNullAndDismissalDateEqualsLocalDateNow() {
        Employee employee = employeeList.get(2);
        Mockito.when(employeeRepo.findById(3L)).thenReturn(Optional.of(employeeList.get(2)));
        Mockito.when(employeeRepo.save(employee)).thenReturn(employee);

        employeeService.dismissal(3L);

        Assert.assertNull(employee.getDepartmentId());
        Assert.assertEquals(LocalDate.now(), employee.getDateOfDismissal());

        verify(employeeRepo, times(1)).findById(3L);
        verify(employeeRepo, times(1)).save(employee);

    }

    @Test
    public void whenGivenEmployeeIdAndDepartmentIdThenChangeDepartmentIdOfOneEmployee() {
        Employee employee = employeeList.get(1);
        Employee headEmployee = null;
        Assert.assertEquals(1L, (long) employee.getDepartmentId());

        Mockito.when(employeeRepo.findById(employee.getId())).thenReturn(Optional.of(employee));
        Mockito.when(dtoSnapshotRepo.findByDepartmentId(5L)).thenReturn(new DepartmentDtoSnapshot());
        Mockito.when(employeeRepo.findByDepartmentIdAndIsHeadOfDepartmentTrue(5L)).thenReturn(headEmployee);
        Mockito.when(employeeRepo.save(employee)).thenReturn(employee);


        employeeService.transferEmployee(employee.getId(), 5L);
        assertNull(employeeRepo.findByDepartmentIdAndIsHeadOfDepartmentTrue(5L));
        assertEquals(5L, (long) employee.getDepartmentId());
        verify(feignClient, times(0)).getDepartmentById(5L);
    }

    @Test
    public void whenDepartmentDoesntExistInSnapshotThenCallFeignClientAndIfNotNullSaveInBD() {
        Employee employee = employeeList.get(1);
        Employee headEmployee = null;
        Assert.assertEquals(1L, (long) employee.getDepartmentId());
        DepartmentDto departmentDto = new DepartmentDto();
        DepartmentDtoSnapshot snapshot = new DepartmentDtoSnapshot();

        Mockito.when(employeeRepo.findById(employee.getId())).thenReturn(Optional.of(employee));
        Mockito.when(dtoSnapshotRepo.findByDepartmentId(5L)).thenReturn(null);
        Mockito.when(feignClient.getDepartmentById(5L)).thenReturn(departmentDto);
        Mockito.when(mapper.toDtoSnapshot(departmentDto)).thenReturn(snapshot);
        Mockito.when(dtoSnapshotRepo.save(snapshot)).thenReturn(snapshot);
        Mockito.when(employeeRepo.findByDepartmentIdAndIsHeadOfDepartmentTrue(5L)).thenReturn(headEmployee);
        Mockito.when(employeeRepo.save(employee)).thenReturn(employee);


        employeeService.transferEmployee(employee.getId(), 5L);
        assertNull(employeeRepo.findByDepartmentIdAndIsHeadOfDepartmentTrue(5L));
        assertEquals(5L, (long) employee.getDepartmentId());
        verify(feignClient, times(1)).getDepartmentById(5L);
        verify(dtoSnapshotRepo, times(1)).findByDepartmentId(5L);
        verify(mapper, times(1)).toDtoSnapshot(departmentDto);
        verify(dtoSnapshotRepo, times(1)).save(snapshot);
    }

    @Test(expected = DepartmentNotFoundException.class)
    public void whenDepartmentDoesntExistShouldReturnDepartmentNotFoundException() {
        Employee employee = employeeList.get(1);

        Mockito.when(employeeRepo.findById(employee.getId())).thenReturn(Optional.of(employee));
        Mockito.when(dtoSnapshotRepo.findByDepartmentId(5L)).thenReturn(null);
        Mockito.when(feignClient.getDepartmentById(5L)).thenReturn(null);

        employeeService.transferEmployee(employee.getId(), 5L);

    }

    @Test(expected = FallbackException.class)
    public void returnFallbackMethodWhenDepartmentsServiceIsNotAvailable() {
        Employee employee = employeeList.get(1);

        Mockito.when(employeeRepo.findById(employee.getId())).thenReturn(Optional.of(employee));
        Mockito.when(feignClient.getDepartmentById(5L)).thenThrow(FallbackException.class);

        employeeService.transferEmployee(employee.getId(), 5L);

    }

    @Test
    public void whenHeadDepartmentExistsThenReturnTwoHeadOfDepartmentException() {
        Employee employee = employeeList.get(1);
        employee.setHeadOfDepartment(true);

        Employee headEmployee = new Employee();
        headEmployee.setDepartmentId(5L);
        headEmployee.setHeadOfDepartment(true);

        Mockito.when(feignClient.getDepartmentById(5L)).thenReturn(new DepartmentDto());
        Mockito.when(employeeRepo.findById(employee.getId())).thenReturn(Optional.of(employee));
        Mockito.when(employeeRepo.findByDepartmentIdAndIsHeadOfDepartmentTrue(5L)).thenReturn(headEmployee);
        Mockito.when(employeeRepo.save(employee)).thenReturn(employee);

        assertNotNull(employeeRepo.findByDepartmentIdAndIsHeadOfDepartmentTrue(5L));
        assertTrue(employee.isHeadOfDepartment());

        thrown.expect(TwoHeadOfDepartmentException.class);
        thrown.expectMessage("There can't be two head in a department");

        employeeService.transferEmployee(employee.getId(), 5L);
    }

    @Test
    public void whenGivenEmployeeIdAndDepartmentIdThenChangeDepartmentIdOfAllEmployees() {
        List<Employee> transferred;
        employeeList = mock(List.class);
        Mockito.when(feignClient.getDepartmentById(1L)).thenReturn(new DepartmentDto());
        Mockito.when(feignClient.getDepartmentById(3L)).thenReturn(new DepartmentDto());
        when(employeeRepo.findByDepartmentId(any(Long.class))).thenReturn(employeeList);
        doNothing().when(employeeList).forEach(employee -> employeeService.transferEmployee(any(Long.class), 3L));
        employeeList.forEach(employee -> employee.setDepartmentId(3L));
        transferred = employeeList;

        employeeService.transferAllEmployee(1L, 3L);
        List<Employee> result = transferred.stream()
                .filter(employee -> employee.getDepartmentId() == 3L)
                .collect(Collectors.toList());
        assertEquals(transferred.size(), result.size());

        verify(employeeRepo, times(1)).findByDepartmentId(any(Long.class));

    }

    @Test
    public void whenGivenIdShouldReturnEmployeesHead() {
        Employee employee = employeeList.get(2);
        Employee head = employeeList.get(0);
        Assert.assertTrue(head.isHeadOfDepartment());

        Mockito.when(employeeRepo.findById(3L)).thenReturn(Optional.of(employee));
        when(employeeRepo.findByDepartmentIdAndIsHeadOfDepartmentTrue(employee.getDepartmentId())).thenReturn(head);

        employeeService.getEmployeesHead(3L);

        verify(employeeRepo, times(1)).findById(3L);
        verify(employeeRepo, times(1)).findByDepartmentIdAndIsHeadOfDepartmentTrue(employee.getDepartmentId());
    }

    @Test
    public void whenGivenEmailShouldReturnEmployee() {
        Employee employee = employeeList.get(1);

        Mockito.when(employeeRepo.findByEmail("kaufmann@gmail.com")).thenReturn(Optional.of(employee));
        Assert.assertNotNull(employee);
        Employee result = employeeService.findByEmail("kaufmann@gmail.com");
        Assert.assertEquals(employee, result);
        verify(employeeRepo, times(1)).findByEmail("kaufmann@gmail.com");

    }

    @Test(expected = EmployeeNotFoundException.class)
    public void whenEmployeeNullShouldReturnEmployeeNotFoundException() {

        Mockito.when(employeeRepo.findByEmail("employee@gmail.com")).thenThrow(EmployeeNotFoundException.class);

//        thrown.expect(EmployeeNotFoundException.class);
//        thrown.expectMessage("Employee with employee@gmail.com not found");

        employeeService.findByEmail("employee@gmail.com");
    }


    @Test
    public void whenParamIsFalseShouldReturnListRegularEmployees() {
        boolean parameter = false;
        List<Employee> regularEmployees = employeeList.stream()
                .filter(employee -> employee.isHeadOfDepartment() == parameter)
                .collect(Collectors.toList());

        when(employeeRepo.findByIsHeadOfDepartment(parameter)).thenReturn(regularEmployees);
        List<Employee> resultList = employeeService.getEmployeeListByParam(parameter);

        assertTrue(CollectionUtils.isEqualCollection(regularEmployees, resultList));
        verify(employeeRepo, times(1)).findByIsHeadOfDepartment(parameter);

    }

    @Test
    public void whenParamIsTrueShouldReturnListOfHead() {
        boolean parameter = true;
        List<Employee> heads = employeeList.stream()
                .filter(employee -> employee.isHeadOfDepartment() == parameter)
                .collect(Collectors.toList());

        when(employeeRepo.findByIsHeadOfDepartment(parameter)).thenReturn(heads);
        List<Employee> resultList = employeeService.getEmployeeListByParam(parameter);

        assertTrue(CollectionUtils.isEqualCollection(heads, resultList));
        verify(employeeRepo, times(1)).findByIsHeadOfDepartment(parameter);

    }

    @Test
    public void getEmployeeListByDepartmentId() {
        Mockito.when(employeeRepo.findByDepartmentId(1L)).thenReturn(employeeList);
        List<Employee> employees = employeeRepo.findByDepartmentId(1L);
        Assert.assertEquals(3, employees.size());
    }

    @AfterClass
    public static void tearDown() {
        System.out.println("Tests finished");
    }
}