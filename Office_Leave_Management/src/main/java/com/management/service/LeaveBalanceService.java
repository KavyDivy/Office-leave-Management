package com.management.service;

import com.management.entity.LeaveBalance;

public interface LeaveBalanceService {

    
    LeaveBalance getEmployeeLeaveBalance(Long employeeId, String leaveType);

   
    void updateLeaveBalance(Long employeeId, String leaveType, int newBalance);
//    void deductLeaveBalance(Long employeeId, String leaveType, int deduction);
    void deductLeaveBalance(Long employeeId, String leaveType, int deduction);
    
   
}





