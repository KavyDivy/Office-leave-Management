package com.management.service;

import java.util.List;

import com.management.entity.Employee;
import com.management.entity.LeaveBalance;
import com.management.entity.LeaveRequest;

public interface EmployeeService {
	void applyForLeave(Long employeeId, LeaveRequest leaveRequest);

    

    List<LeaveRequest> getEmployeeLeaveRequests(Long employeeId);

    void approveLeaveRequest(Long leaveRequestId);

    void rejectLeaveRequest(Long leaveRequestId, String rejectionReason);
    List<Employee> getAllEmployeesFromRepository();
    void addEmployee(Employee employee);
    
        void deleteEmployee(Long employeeId);
        void updateEmployee(Long employeeId, Employee updatedEmployee);
//        List<LeaveRequest> findByEmployee_EmployeeId(Long employeeId);
        LeaveBalance getEmployeeLeaveBalance(Long employeeId,String leaveType);

        
        void updateLeaveBalance(Long employeeId, String leaveType, int newBalance);


}


