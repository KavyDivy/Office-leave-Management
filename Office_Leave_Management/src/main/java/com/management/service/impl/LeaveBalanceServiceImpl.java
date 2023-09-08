package com.management.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management.entity.LeaveBalance;
import com.management.entity.LeaveRequest;
import com.management.repository.LeaveBalanceRepository;
import com.management.repository.LeaveRequestRepository;
import com.management.service.LeaveBalanceService;
import java.util.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;

@Service
public class LeaveBalanceServiceImpl implements LeaveBalanceService {

    private final LeaveBalanceRepository leaveBalanceRepository;
    

//    @Autowired
//    public LeaveBalanceServiceImpl(LeaveBalanceRepository leaveBalanceRepository) {
//        this.leaveBalanceRepository = leaveBalanceRepository;
//    }
    
    private LeaveRequestRepository leaveRequestRepository;
    @Autowired
    public LeaveBalanceServiceImpl(
            LeaveBalanceRepository leaveBalanceRepository,
            LeaveRequestRepository leaveRequestRepository) {
        this.leaveBalanceRepository = leaveBalanceRepository;
        this.leaveRequestRepository = leaveRequestRepository;
    }
//
//    @Override
//    public LeaveBalance getEmployeeLeaveBalance(Long employeeId, String leaveType) {
//        return leaveBalanceRepository.findByEmployeeEmployeeIdAndLeaveType(employeeId, leaveType);
//    }
//
    @Override
    public void updateLeaveBalance(Long employeeId, String leaveType, int newBalance) {
        LeaveBalance leaveBalance = leaveBalanceRepository.findByEmployeeEmployeeIdAndLeaveType(employeeId, leaveType);
        if (leaveBalance != null) {
            leaveBalance.setBalance(newBalance);
            leaveBalanceRepository.save(leaveBalance);
        }
    }
//    @Override
//    public void deductLeaveBalance(Long employeeId, String leaveType, int deduction) {
//        LeaveBalance leaveBalance = leaveBalanceRepository.findByEmployeeEmployeeIdAndLeaveType(employeeId, leaveType);
//        if (leaveBalance != null) {
//            int currentBalance = leaveBalance.getBalance();
//            int newBalance = currentBalance - deduction;
//            if (newBalance >= 0) {
//                leaveBalance.setBalance(newBalance);
//                leaveBalanceRepository.save(leaveBalance);
//            } else {
//                throw new IllegalArgumentException("Insufficient leave balance");
//            }
//        }
//    }
    @Override
    public LeaveBalance getEmployeeLeaveBalance(Long employeeId, String leaveType) {
        LeaveBalance leaveBalance = leaveBalanceRepository.findByEmployeeEmployeeIdAndLeaveType(employeeId, leaveType);
        if (leaveBalance != null) {
            // Calculate the leave balance based on approved leave requests
            int approvedLeaveDays = calculateApprovedLeaveDays(employeeId, leaveType);
            leaveBalance.setBalance(approvedLeaveDays);
        }
        return leaveBalance;
    }

//    @Override
//    public void deductLeaveBalance(Long employeeId, String leaveType, int deduction) {
//        LeaveBalance leaveBalance = leaveBalanceRepository.findByEmployeeEmployeeIdAndLeaveType(employeeId, leaveType);
//        if (leaveBalance != null) {
//            int currentBalance = leaveBalance.getBalance();
//            int newBalance = currentBalance - deduction;
//            if (newBalance >= 0) {
//                leaveBalance.setBalance(newBalance);
//                leaveBalanceRepository.save(leaveBalance);
//            } else {
//                throw new IllegalArgumentException("Insufficient leave balance");
//            }
//        }
//    }
//    @Override
//    public void deductLeaveBalance(Long employeeId, String leaveType, int deduction) {
//        LeaveBalance leaveBalance = leaveBalanceRepository.findByEmployeeEmployeeIdAndLeaveType(employeeId, leaveType);
//        if (leaveBalance != null) {
//            int currentBalance = leaveBalance.getBalance();
//            if (deduction <= currentBalance) {
//                leaveBalance.setBalance(currentBalance - deduction);
//                leaveBalanceRepository.save(leaveBalance);
//            } else {
//                throw new IllegalArgumentException("Insufficient leave balance");
//            }
//        }
//    }
    @Override
    public void deductLeaveBalance(Long employeeId, String leaveType, int deduction) {
        LeaveBalance leaveBalance = leaveBalanceRepository.findByEmployeeEmployeeIdAndLeaveType(employeeId, leaveType);
        if (leaveBalance != null) {
            int currentBalance = leaveBalance.getBalance();
            int approvedLeaveDays = calculateApprovedLeaveDays(employeeId, leaveType);
            if (deduction <= approvedLeaveDays) {
                leaveBalance.setBalance(currentBalance - deduction);
                leaveBalanceRepository.save(leaveBalance);
            } else {
                throw new IllegalArgumentException("Insufficient leave balance");
            }
        }
    }


  
    private int calculateApprovedLeaveDays(Long employeeId, String leaveType) {
        int approvedLeaveDays = 0;

        
        Iterable<LeaveRequest> leaveRequests = leaveRequestRepository.findByEmployeeEmployeeIdAndLeaveTypeAndStatus(
            employeeId, leaveType, "APPROVED");

        for (LeaveRequest leaveRequest : leaveRequests) {
            
            LocalDate startDate = leaveRequest.getStartDate();
            LocalDate endDate = leaveRequest.getEndDate();

            if (startDate != null && endDate != null) {
                
//                Period period = Period.between(startDate, endDate);
//                int durationInDays = period.getDays()+1; 
//                approvedLeaveDays += durationInDays;
            	int durationInDays = calculateDuration(startDate, endDate);
                approvedLeaveDays += durationInDays;
            }
        }

        return approvedLeaveDays;
    }
    private int calculateDuration(LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            return 0;
        }

        Period period = Period.between(startDate, endDate);
        return period.getDays() + 1;
    }

    
}

