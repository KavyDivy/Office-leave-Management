package com.management.service;

public interface LeaveApprovalService {
    void approveLeaveRequest(Long leaveRequestId);
    void rejectLeaveRequest(Long leaveRequestId, String rejectionReason);
//    void deductLeaveBalance(Long employeeId, String leaveType, int deduction);
    void approveLeaveRequestAndDeductBalance(Long leaveRequestId);
}
