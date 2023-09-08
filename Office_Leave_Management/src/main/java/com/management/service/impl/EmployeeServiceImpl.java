package com.management.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.management.entity.Employee;
import com.management.entity.LeaveBalance;
import com.management.entity.LeaveRequest;
import com.management.repository.EmployeeRepository;
import com.management.repository.LeaveBalanceRepository;
import com.management.repository.LeaveRequestRepository;
import com.management.service.EmployeeService;

import java.util.Arrays;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final LeaveRequestRepository leaveRequestRepository;
    private final LeaveBalanceRepository leaveBalanceRepository;

    private static final int DEFAULT_LEAVE_BALANCE = 4; 

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               LeaveRequestRepository leaveRequestRepository,
                               LeaveBalanceRepository leaveBalanceRepository) {
        this.employeeRepository = employeeRepository;
        this.leaveRequestRepository = leaveRequestRepository;
        this.leaveBalanceRepository = leaveBalanceRepository;
    }
    @Override
    public void applyForLeave(Long employeeId, LeaveRequest leaveRequest) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with ID: " + employeeId));

        
        leaveRequest.setEmployee(employee);

        
        leaveRequestRepository.save(leaveRequest);
    }

    @Override
    public List<LeaveRequest> getEmployeeLeaveRequests(Long employeeId) {
        return leaveRequestRepository.findByEmployee_EmployeeId(employeeId);
    }

    @Override
    public void approveLeaveRequest(Long leaveRequestId) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveRequestId)
                .orElseThrow(() -> new IllegalArgumentException("Leave request not found."));

        
        leaveRequest.setStatus("Approved");
        leaveRequestRepository.save(leaveRequest);
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
    public List<Employee> getAllEmployeesFromRepository() {
        return employeeRepository.findAll();
    }

    @Override
    public void addEmployee(Employee employee) {
        
        Employee savedEmployee = employeeRepository.save(employee);

        
        initializeLeaveBalances(savedEmployee);
        
    }


    @Override
    public void deleteEmployee(Long employeeId) {
        employeeRepository.deleteById(employeeId);
    }

    @Override
    public void updateEmployee(Long employeeId, Employee updatedEmployee) {
        Employee existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found."));

        existingEmployee.setName(updatedEmployee.getName());
        existingEmployee.setEmail(updatedEmployee.getEmail());
        existingEmployee.setPhoneNumber(updatedEmployee.getPhoneNumber());
        existingEmployee.setDepartment(updatedEmployee.getDepartment());

        employeeRepository.save(existingEmployee);
    }

    @Override
    public LeaveBalance getEmployeeLeaveBalance(Long employeeId, String leaveType) {
        
        return leaveBalanceRepository.findByEmployeeEmployeeIdAndLeaveType(employeeId, leaveType);
    }

    @Override
    public void updateLeaveBalance(Long employeeId, String leaveType, int newBalance) {
        LeaveBalance leaveBalance = leaveBalanceRepository.findByEmployeeEmployeeIdAndLeaveType(employeeId, leaveType);
        if (leaveBalance != null) {
            leaveBalance.setBalance(newBalance);
            leaveBalanceRepository.save(leaveBalance);
        } else {
           
            throw new IllegalArgumentException("Leave balance not found for employee and leave type.");
        }
    }
    public void initializeLeaveBalances(Employee employee) {
      
        List<String> leaveTypes = getLeaveTypes();

        
        for (String leaveType : leaveTypes) {
            LeaveBalance leaveBalance = new LeaveBalance(employee, leaveType, DEFAULT_LEAVE_BALANCE);
            leaveBalanceRepository.save(leaveBalance);
        }
    }


    private List<String> getLeaveTypes() {
    
        return Arrays.asList("Annual Leave", "Sick Leave", "Unpaid Leave");
    }
}

