package com.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.management.entity.LeaveRequest;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
	@Query("SELECT lr FROM LeaveRequest lr JOIN FETCH lr.employee e WHERE e.employeeId = :employeeId")
	List<LeaveRequest> findByEmployee_EmployeeId(@Param("employeeId") Long employeeId);

//	List<LeaveRequest> findAllByEmployeeId(Long employeeId);
	Iterable<LeaveRequest> findByEmployeeEmployeeIdAndLeaveTypeAndStatus(
	        Long employeeId, String leaveType, String status);
    
}
