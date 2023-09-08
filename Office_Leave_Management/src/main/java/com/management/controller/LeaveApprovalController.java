package com.management.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.management.service.EmployeeService;
import com.management.service.LeaveApprovalService;

@RestController
@RequestMapping("/api/leave/approval")
@CrossOrigin(origins = "http://localhost:3001")
public class LeaveApprovalController {
    private final LeaveApprovalService leaveApprovalService;

    @Autowired
    public LeaveApprovalController(LeaveApprovalService leaveApprovalService) {
        this.leaveApprovalService = leaveApprovalService;
    }

    @PostMapping("/{leaveRequestId}/approve")
    public ResponseEntity<String> approveLeave(@PathVariable Long leaveRequestId) {
        try {
            leaveApprovalService.approveLeaveRequest(leaveRequestId);
            return ResponseEntity.ok("Leave request approved.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to approve leave request.");
        }
    }

    @PostMapping("/{leaveRequestId}/reject")
    public ResponseEntity<String> rejectLeave(@PathVariable Long leaveRequestId, @RequestBody Map<String, String> rejectionData) {
        String rejectionReason = rejectionData.get("rejectionReason");
        if (rejectionReason == null || rejectionReason.isEmpty()) {
            return ResponseEntity.badRequest().body("Rejection reason is required.");
        }

        try {
            leaveApprovalService.rejectLeaveRequest(leaveRequestId, rejectionReason);
            return ResponseEntity.ok("Leave request rejected.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to reject leave request.");
        }
    }
    @PostMapping("/{leaveRequestId}/approveAndDeductBalance")
    public ResponseEntity<String> approveLeaveAndDeductBalance(@PathVariable Long leaveRequestId) {
        try {
            leaveApprovalService.approveLeaveRequestAndDeductBalance(leaveRequestId);
            return ResponseEntity.ok("Leave request approved and leave balance deducted.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to approve leave request and deduct leave balance.");
        }
    }
}
