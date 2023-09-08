package com.management.service.impl;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management.entity.LeaveRequest;
import com.management.repository.LeaveRequestRepository;
import com.management.service.EmployeeService;
import com.management.service.LeaveApprovalService;
import com.management.service.LeaveBalanceService;

@Service
public class LeaveApprovalServiceImpl implements LeaveApprovalService {
    private final LeaveRequestRepository leaveRequestRepository;
    private final LeaveBalanceService leaveBalanceService;  //added now

    @Autowired
    public LeaveApprovalServiceImpl(LeaveRequestRepository leaveRequestRepository, LeaveBalanceService leaveBalanceService) {
        this.leaveRequestRepository = leaveRequestRepository;
        this.leaveBalanceService = leaveBalanceService; //added now
    }
    private int calculateDuration(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            return 0;
        }

        Period period = Period.between(startDate, endDate);
        return period.getDays();
    }

//    @Override
//    public void approveLeaveRequest(Long leaveRequestId) {
//        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveRequestId)
//                .orElseThrow(() -> new IllegalArgumentException("Leave request not found."));
//
//        leaveRequest.setStatus("Approved");
//        leaveRequestRepository.save(leaveRequest);
//        leaveBalanceService.deductLeaveBalance(leaveRequest.getEmployee().getEmployeeId(), leaveRequest.getLeaveType(), 1);
//    }
    @Override
    public void approveLeaveRequest(Long leaveRequestId) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveRequestId)
                .orElseThrow(() -> new IllegalArgumentException("Leave request not found."));

        leaveRequest.setStatus("Approved");
        leaveRequestRepository.save(leaveRequest);

        LocalDate startDate = leaveRequest.getStartDate();
        LocalDate endDate = leaveRequest.getEndDate();

        if (startDate != null && endDate != null) {
            int durationInDays = calculateDuration(startDate, endDate);
            leaveBalanceService.deductLeaveBalance(leaveRequest.getEmployee().getEmployeeId(), leaveRequest.getLeaveType(), durationInDays);
        }
    }

    @Override
    public void rejectLeaveRequest(Long leaveRequestId, String rejectionReason) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveRequestId)
                .orElseThrow(() -> new IllegalArgumentException("Leave request not found."));

        leaveRequest.setStatus("Rejected");
        leaveRequest.setRejectionReason(rejectionReason);
        leaveRequestRepository.save(leaveRequest);
    }
    @Override
    public void approveLeaveRequestAndDeductBalance(Long leaveRequestId) {
    
        approveLeaveRequest(leaveRequestId);

        
        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveRequestId)
                .orElseThrow(() -> new IllegalArgumentException("Leave request not found."));
        
        leaveBalanceService.deductLeaveBalance(leaveRequest.getEmployee().getEmployeeId(), leaveRequest.getLeaveType(), 1);
    }
}
