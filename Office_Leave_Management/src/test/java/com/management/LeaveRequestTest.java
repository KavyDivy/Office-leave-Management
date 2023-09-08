package com.management;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.management.entity.Employee;
import com.management.entity.LeaveRequest;
import com.management.repository.LeaveRequestRepository;
import com.management.service.LeaveBalanceService;
import com.management.service.impl.LeaveApprovalServiceImpl;

@SpringBootTest
public class LeaveRequestTest {

    @InjectMocks
    private LeaveApprovalServiceImpl leaveApprovalService;

    @Mock
    private LeaveRequestRepository leaveRequestRepository;

    @Mock
    private LeaveBalanceService leaveBalanceService;

    private LeaveRequest leaveRequest;

    @BeforeEach
    public void setUp() {
    	 Employee sampleEmployee = new Employee();
    	    sampleEmployee.setEmployeeId(62L);
        leaveRequest = new LeaveRequest();
        leaveRequest.setLeaveRequestId(10L);
        leaveRequest.setEmployee( sampleEmployee);
        leaveRequest.setLeaveType("Unpaid Leave");
    }

    @Test
    public void testApproveLeaveRequest() {
        // Mock the behavior of leaveRequestRepository
        when(leaveRequestRepository.findById(leaveRequest.getLeaveRequestId())).thenReturn(java.util.Optional.of(leaveRequest));

        // Call the approveLeaveRequest method
        leaveApprovalService.approveLeaveRequest(leaveRequest.getLeaveRequestId());

        // Verify that the status of the leave request is updated to "Approved"
//        verify(leaveRequest).setStatus("Approved");

        // Verify that the leaveRequestRepository.save method is called
        verify(leaveRequestRepository).save(leaveRequest);

        // Verify that the leaveBalanceService.deductLeaveBalance method is called with the correct parameters
        verify(leaveBalanceService).deductLeaveBalance(leaveRequest.getEmployee().getEmployeeId(), leaveRequest.getLeaveType(), 1);
    }

    @Test
    public void testRejectLeaveRequest() {
        // Mock the behavior of leaveRequestRepository
        when(leaveRequestRepository.findById(leaveRequest.getLeaveRequestId())).thenReturn(java.util.Optional.of(leaveRequest));

        // Call the rejectLeaveRequest method
        leaveApprovalService.rejectLeaveRequest(leaveRequest.getLeaveRequestId(), "Rejection Reason");

//        // Verify that the status of the leave request is updated to "Rejected"
//        verify(leaveRequest).setStatus("Rejected");
//
//        // Verify that the rejectionReason is set correctly
//        verify(leaveRequest).setRejectionReason("Rejection Reason");

        // Verify that the leaveRequestRepository.save method is called
        verify(leaveRequestRepository).save(leaveRequest);
    }

    @Test
    public void testApproveLeaveRequestAndDeductBalance() {
        // Mock the behavior of leaveRequestRepository
        when(leaveRequestRepository.findById(leaveRequest.getLeaveRequestId())).thenReturn(java.util.Optional.of(leaveRequest));

        // Call the approveLeaveRequestAndDeductBalance method
        leaveApprovalService.approveLeaveRequestAndDeductBalance(leaveRequest.getLeaveRequestId());

        // Verify that the status of the leave request is updated to "Approved"
//        verify(leaveRequest).setStatus("Approved");

        // Verify that the leaveRequestRepository.save method is called
        verify(leaveRequestRepository).save(leaveRequest);

        // Verify that the leaveBalanceService.deductLeaveBalance method is called with the correct parameters
        verify(leaveBalanceService, times(2)).deductLeaveBalance(leaveRequest.getEmployee().getEmployeeId(), leaveRequest.getLeaveType(), 1);
    }
}
