package com.management.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.management.entity.Employee;
import com.management.repository.EmployeeRepository;

import jakarta.annotation.PostConstruct;

@Component
public class DataLoader {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public DataLoader(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @PostConstruct
    public void loadData() {
//        Employee employee1 = new Employee();
//        employee1.setName("John Doe");
//        employee1.setEmail("john.doe@example.com");
//        employee1.setPhoneNumber("1234567890");
//        employee1.setDepartment("IT");
//        employeeRepository.save(employee1);
//
//        Employee employee2 = new Employee();
//        employee2.setName("Jane Smith");
//        employee2.setEmail("jane.smith@example.com");
//        employee2.setPhoneNumber("9876543210");
//        employee2.setDepartment("HR");
//        employeeRepository.save(employee2);
//        
//        Employee employee3 = new Employee();
//        employee2.setName("Muzafir");
//        employee2.setEmail("muz.anaf@example.com");
//        employee2.setPhoneNumber("9876543210");
//        employee2.setDepartment("Production");
//        employeeRepository.save(employee3);
//        
//        Employee employee4 = new Employee();
//        employee2.setName("Anaswer");
//        employee2.setEmail("Anu.@gmail.com");
//        employee2.setPhoneNumber("9876587210");
//        employee2.setDepartment("Finance");
//        employeeRepository.save(employee3);

        
    }
}
