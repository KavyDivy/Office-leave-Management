package com.management.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.management.entity.LeaveBalance;
import com.management.entity.LeaveRequest;
import com.management.service.EmployeeService;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "http://localhost:3001")
public class ReportingController {

    private final EmployeeService employeeService;

    @Autowired
    public ReportingController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    

    @GetMapping("/leave-balances/{employeeId}")
    public ResponseEntity<LeaveBalance> getLeaveBalances(@PathVariable Long employeeId,
                                                         @RequestParam String leaveType) {
        LeaveBalance leaveBalance = employeeService.getEmployeeLeaveBalance(employeeId, leaveType);
        if (leaveBalance == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(leaveBalance);
    }
    @PutMapping("/leave-balances/{employeeId}")
    public ResponseEntity<Void> updateLeaveBalance(@PathVariable Long employeeId,
                                                   @RequestParam String leaveType,
                                                   @RequestParam int newBalance) {
        employeeService.updateLeaveBalance(employeeId, leaveType, newBalance);
        return ResponseEntity.noContent().build();
    }
//    


    @GetMapping("/leave-history/{employeeId}")
    public ResponseEntity<List<LeaveRequest>> getLeaveHistory(@PathVariable Long employeeId) {
        List<LeaveRequest> leaveHistory = employeeService.getEmployeeLeaveRequests(employeeId);
        return ResponseEntity.ok(leaveHistory);
    }
}
