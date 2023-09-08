package com.management;



import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;

import com.management.controller.EmployeeController;
import com.management.entity.Employee;
import com.management.entity.LeaveBalance;
import com.management.repository.EmployeeRepository;
import com.management.repository.LeaveBalanceRepository;
import com.management.repository.LeaveRequestRepository;
import com.management.service.EmployeeService;
import com.management.service.impl.EmployeeServiceImpl;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

//@RunWith(MockitoJUnitRunner.class)
public class Office_Leave_ManagementTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private LeaveRequestRepository leaveRequestRepository;

    @Mock
    private LeaveBalanceRepository leaveBalanceRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void testGetAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        Employee e1 = new Employee(1L, "John Doe", "john@example.com", "1234567890", "IT");
        employees.add(e1);
        Employee e2 = new Employee(2L, "Jane Smith", "jane@example.com", "9876543210", "HR");
        employees.add(e2);

        when(employeeRepository.findAll()).thenReturn(employees);

        List<Employee> result = employeeService.getAllEmployeesFromRepository();

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals("John Doe", result.get(0).getName());
        Assertions.assertEquals("Jane Smith", result.get(1).getName());
    }

    @Test
    public void testAddEmployee() {
        Employee employee = new Employee();
        when(employeeRepository.save(any())).thenReturn(employee);

       
        employeeService.addEmployee(employee);

        
        verify(employeeRepository, times(1)).save(employee);

        
    }
    @InjectMocks
    private EmployeeController employeeController;

    @Test
    public void testUpdateEmployee() {
       
        Employee updatedEmployee = new Employee();
        updatedEmployee.setEmployeeId(1L);
        updatedEmployee.setName("Updated Name");
        updatedEmployee.setEmail("updated@example.com");
        updatedEmployee.setPhoneNumber("1234567890");
        updatedEmployee.setDepartment("IT");
        
        EmployeeService employeeService = mock(EmployeeService.class);
        doNothing().when(employeeService).updateEmployee(Mockito.anyLong(), Mockito.any(Employee.class));

       
        EmployeeController employeeController = new EmployeeController(employeeService);

     
        ResponseEntity<String> responseEntity = employeeController.updateEmployee(1L, updatedEmployee);

        
        verify(employeeService).updateEmployee(Mockito.anyLong(), Mockito.any(Employee.class));

        
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }




    @Test
    public void testUpdateLeaveBalance() {
        LeaveBalance leaveBalance = new LeaveBalance();
        when(leaveBalanceRepository.findByEmployeeEmployeeIdAndLeaveType(anyLong(), anyString()))
                .thenReturn(leaveBalance);

        employeeService.updateLeaveBalance(1L, "Annual Leave", 5);

        verify(leaveBalanceRepository, times(1)).findByEmployeeEmployeeIdAndLeaveType(anyLong(), anyString());
        verify(leaveBalanceRepository, times(1)).save(leaveBalance);
    }
//    @Test
//    void testDeleteEmployee() throws Exception {
//        Long employeeId = 1L;
//
//        doNothing().when(employeeService).deleteEmployee(employeeId);
//
//        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build(); // Declare mockMvc here
//
//        mockMvc.perform(delete("/api/employees/{employeeId}", employeeId))
//                .andExpect(status().isOk())
//                .andExpect(content().string("Employee deleted successfully."));
//    }
    }
    
//    @Test
//    public void testCreateEmployee() {
//        Employee newEmployee = new Employee();
//        newEmployee.setName("John Doe");
//        newEmployee.setEmail("john@example.com");
//        newEmployee.setPhoneNumber("1234567890");
//        newEmployee.setDepartment("IT");
//
//        when(employeeRepository.save(any())).thenAnswer(new Answer<Employee>() {
//            @Override
//            public Employee answer(InvocationOnMock invocation) throws Throwable {
//                Employee addedEmployee = invocation.getArgument(0);
//                initializeLeaveBalances(addedEmployee); // Assuming this method is called here
//                return addedEmployee;
//            }
//        });
//
//        Employee createdEmployee = employeeService.addEmployee(newEmployee);
//
//        Assertions.assertEquals("John Doe", createdEmployee.getName());
//        Assertions.assertEquals("john@example.com", createdEmployee.getEmail());
//        Assertions.assertEquals("1234567890", createdEmployee.getPhoneNumber());
//        Assertions.assertEquals("IT", createdEmployee.getDepartment());
//
//        verify(employeeRepository, times(1)).save(any());
//    }
//
//    @Test
//    public void testUpdateEmployee() {
//        Employee existingEmployee = new Employee();
//        existingEmployee.setEmployeeId(1L);
//        existingEmployee.setName("Jane Smith");
//        existingEmployee.setEmail("jane@example.com");
//        existingEmployee.setPhoneNumber("9876543210");
//        existingEmployee.setDepartment("HR");
//
//        when(employeeRepository.findById(1L)).thenReturn(Optional.of(existingEmployee));
//        when(employeeRepository.save(any())).thenReturn(existingEmployee);
//
//        Employee updatedEmployee = new Employee();
//        updatedEmployee.setName("Updated Name");
//        updatedEmployee.setEmail("updated@example.com");
//        updatedEmployee.setPhoneNumber("5555555555");
//        updatedEmployee.setDepartment("Updated Department");
//
//        Employee result = employeeService.updateEmployee(1L, updatedEmployee);
//
//        Assertions.assertEquals("Updated Name", result.getName());
//        Assertions.assertEquals("updated@example.com", result.getEmail());
//        Assertions.assertEquals("5555555555", result.getPhoneNumber());
//        Assertions.assertEquals("Updated Department", result.getDepartment());
//
//        verify(employeeRepository, times(1)).findById(1L);
//        verify(employeeRepository, times(1)).save(any());
//    }
//}
//
//
//
//
//
//
