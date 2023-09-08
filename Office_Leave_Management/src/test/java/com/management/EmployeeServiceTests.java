package com.management;

import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


import com.management.entity.Employee;
import com.management.entity.LeaveRequest;
import com.management.repository.EmployeeRepository;
import com.management.service.EmployeeService;
import com.management.service.impl.EmployeeServiceImpl;


@SpringBootTest
public class EmployeeServiceTests {
	
//	@InjectMocks
    @MockBean
    private EmployeeService employeeService;

    private Employee employee;

    @BeforeEach
    public void setUp() {
        employee = new Employee();
        employee.setEmployeeId(1L);
        employee.setName("John Doe");
        

       
        doNothing().when(employeeService).deleteEmployee(employee.getEmployeeId());
        doThrow(IllegalArgumentException.class).when(employeeService).deleteEmployee(-1L);
    }

    @Test
    public void testDeleteEmployee_Successful() {
       
        employeeService.deleteEmployee(employee.getEmployeeId());

        verify(employeeService, times(1)).deleteEmployee(employee.getEmployeeId());
    }

    @Test
    public void testDeleteEmployee_InvalidEmployeeId() {
        
        try {
            employeeService.deleteEmployee(-1L);
        } catch (IllegalArgumentException e) {
            
        }

        
        verify(employeeService, times(1)).deleteEmployee(-1L);
    }
    @Mock 
    EmployeeRepository EmployeeRepo;
	


    @Test
    public void testGetLeaveRequests() {

        List<LeaveRequest> leaveRequests = new ArrayList<>();
        LocalDate startDate1 = LocalDate.of(2023, 8, 16);
        LocalDate endDate1 = LocalDate.of(2023, 8, 22);
        LeaveRequest request1 = new LeaveRequest(10L, 62L, startDate1, endDate1, "Unpaid Leave", "Trip", "Approved");
        leaveRequests.add(request1);

        when(employeeService.getEmployeeLeaveRequests(anyLong())).thenReturn(leaveRequests);

        List<LeaveRequest> result = employeeService.getEmployeeLeaveRequests(1L);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("Unpaid Leave", result.get(0).getLeaveType());

        
        LocalDate expectedEndDate = LocalDate.parse("2023-08-22");
        Assertions.assertEquals(expectedEndDate, result.get(0).getEndDate());
    }
    
}







    




