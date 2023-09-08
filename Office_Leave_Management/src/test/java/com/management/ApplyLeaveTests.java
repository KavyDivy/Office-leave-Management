package com.management;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.management.entity.Employee;
import com.management.entity.LeaveRequest;
import com.management.repository.EmployeeRepository;
import com.management.repository.LeaveRequestRepository;
import com.management.service.impl.EmployeeServiceImpl;

public class ApplyLeaveTests {

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private LeaveRequestRepository leaveRequestRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testApplyForLeave() {
        
        Long employeeId = 1L;
        Employee employee = new Employee();
        employee.setEmployeeId(employeeId);

        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setEmployee(employee);

       
        when(employeeRepository.findById(employeeId)).thenReturn(java.util.Optional.of(employee));

        
        employeeService.applyForLeave(employeeId, leaveRequest);

      
        verify(leaveRequestRepository, times(1)).save(leaveRequest);
    }
}
