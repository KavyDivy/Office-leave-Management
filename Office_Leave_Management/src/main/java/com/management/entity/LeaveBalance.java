package com.management.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Leave_Balances")
public class LeaveBalance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long leaveBalanceId;
    @ManyToOne
    @JoinColumn(name = "employeeId", referencedColumnName = "employeeId")
    @JsonIgnore
    private Employee employee;
    private String leaveType;
    private int balance;
    
	
    @Override
    public String toString() {
        return "LeaveBalance [leaveBalanceId=" + leaveBalanceId + ", employee=" + employee + ", leaveType=" + leaveType
                + ", balance=" + balance + "]";
    }
	public Long getLeaveBalanceId() {
		return leaveBalanceId;
	}
	public void setLeaveBalanceId(Long leaveBalanceId) {
		this.leaveBalanceId = leaveBalanceId;
	}
	
	public LeaveBalance(Long leaveBalanceId, Employee employee, String leaveType, int balance) {
		super();
		this.leaveBalanceId = leaveBalanceId;
		this.employee = employee;
		this.leaveType = leaveType;
		this.balance = balance;
	}
	public LeaveBalance(Employee employee, String leaveType, int balance) {
        this.employee = employee;
        this.leaveType = leaveType;
        this.balance = balance;
    }
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public String getLeaveType() {
		return leaveType;
	}
	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public LeaveBalance() {
		super();
		// TODO Auto-generated constructor stub
	}
	

   
}

