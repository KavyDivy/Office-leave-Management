package com.management;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.management.entity.LeaveBalance;
import com.management.repository.LeaveBalanceRepository;
import com.management.service.impl.LeaveBalanceServiceImpl;

public class ReportingTest {

    @InjectMocks
    private LeaveBalanceServiceImpl leaveBalanceService;

    @Mock
    private LeaveBalanceRepository leaveBalanceRepository;

    @BeforeEach
    public void setUp() {
        // Initialize Mockito annotations
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetEmployeeLeaveBalance() {
        // Create a sample employee ID and leave type
        Long employeeId = 62L;
        String leaveType = "Sick Leave";

        // Create a sample LeaveBalance object
        LeaveBalance sampleLeaveBalance = new LeaveBalance();
        sampleLeaveBalance.setLeaveBalanceId(employeeId);
        sampleLeaveBalance.setLeaveType(leaveType);
        sampleLeaveBalance.setBalance(1);

        // Mock the behavior of the leaveBalanceRepository
        when(leaveBalanceRepository.findByEmployeeEmployeeIdAndLeaveType(employeeId, leaveType))
            .thenReturn(sampleLeaveBalance);

        // Call the getEmployeeLeaveBalance method
        LeaveBalance result = leaveBalanceService.getEmployeeLeaveBalance(employeeId, leaveType);

        // Verify that the method returns the expected LeaveBalance object
        assertNotNull(result);
        assertEquals(sampleLeaveBalance, result);
    }

    @Test
    public void testDeductLeaveBalance() {
        // Create a sample employee ID, leave type, and deduction
        Long employeeId = 62L;
        String leaveType = "Sick Leave";
        int deduction = 1;

        // Create a sample LeaveBalance object with an initial balance
        LeaveBalance sampleLeaveBalance = new LeaveBalance();
        sampleLeaveBalance.setLeaveBalanceId(employeeId);
        sampleLeaveBalance.setLeaveType(leaveType);
        sampleLeaveBalance.setBalance(1);

        // Mock the behavior of the leaveBalanceRepository
        when(leaveBalanceRepository.findByEmployeeEmployeeIdAndLeaveType(employeeId, leaveType))
            .thenReturn(sampleLeaveBalance);

        // Call the deductLeaveBalance method
        leaveBalanceService.deductLeaveBalance(employeeId, leaveType, deduction);

        // Verify that the LeaveBalance object's balance is updated correctly
        assertEquals(0, sampleLeaveBalance.getBalance());
        
        // Verify that save method of leaveBalanceRepository is called
        verify(leaveBalanceRepository).save(sampleLeaveBalance);
    }

    @Test
    public void testDeductLeaveBalanceInsufficientBalance() {
        // Create a sample employee ID, leave type, and deduction
        Long employeeId = 62L;
        String leaveType = "Sick Leave";
        int deduction = 12;

        // Create a sample LeaveBalance object with an initial balance
        LeaveBalance sampleLeaveBalance = new LeaveBalance();
        sampleLeaveBalance.setLeaveBalanceId(employeeId);
        sampleLeaveBalance.setLeaveType(leaveType);
        sampleLeaveBalance.setBalance(10);

        // Mock the behavior of the leaveBalanceRepository
        when(leaveBalanceRepository.findByEmployeeEmployeeIdAndLeaveType(employeeId, leaveType))
            .thenReturn(sampleLeaveBalance);

        // Call the deductLeaveBalance method with an insufficient balance
        assertThrows(IllegalArgumentException.class,
            () -> leaveBalanceService.deductLeaveBalance(employeeId, leaveType, deduction));

        // Verify that the LeaveBalance object's balance is not updated
        assertEquals(10, sampleLeaveBalance.getBalance());
        
        // Verify that save method of leaveBalanceRepository is not called
        verify(leaveBalanceRepository, never()).save(sampleLeaveBalance);
    }
}
