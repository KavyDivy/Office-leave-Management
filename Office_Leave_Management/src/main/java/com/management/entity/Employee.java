package com.management.entity;

import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Employees")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    private String name;
    private String email;
    private String phoneNumber;
    private String department;
	public Long getEmployeeId() {
		return employeeId;
	}
	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL) // Add this line
    private List<LeaveRequest> leaveRequests;

    public List<LeaveRequest> getLeaveRequests() {
		return leaveRequests;
	}
	public void setLeaveRequests(List<LeaveRequest> leaveRequests) {
		this.leaveRequests = leaveRequests;
	}
	public List<LeaveBalance> getLeaveBalances() {
		return leaveBalances;
	}
	public void setLeaveBalances(List<LeaveBalance> leaveBalances) {
		this.leaveBalances = leaveBalances;
	}
	@OneToMany(mappedBy = "employee", cascade = CascadeType.ALL) // Add this line
    private List<LeaveBalance> leaveBalances;
	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public Employee(Long employeeId, String name, String email, String phoneNumber, String department) {
		super();
		this.employeeId = employeeId;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.department = department;
	}
	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", name=" + name + ", email=" + email + ", phoneNumber="
				+ phoneNumber + ", department=" + department + ", leaveRequests=" + leaveRequests + ", leaveBalances="
				+ leaveBalances + "]";
	}
	public Employee() {
		super();
		// TODO Auto-generated constructor stub
	}

  
}

