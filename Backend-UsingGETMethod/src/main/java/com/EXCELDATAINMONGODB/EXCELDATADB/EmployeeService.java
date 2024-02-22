package com.EXCELDATAINMONGODB.EXCELDATADB;

import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Builder
@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public String createEmployee(Payload payload) {
        try {
            Employee emp = Employee.builder()
                    .firstName(payload.getEmp().getFirstName())
                    .lastName(payload.getEmp().getLastName())
                    .email(payload.getEmp().getEmail())
                    .password(payload.getEmp().getPassword())
                    .excelData(payload.getExcelData())
                    .build();

            employeeRepository.save(emp);
        } catch (Exception e) {
            // Handle exception
            return "Failed to create employee";
        }
        return "Employee Created Successfully";
    }

    public List<Employee> getEmployee() {
        try {
            return employeeRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();  // Print the exception details for debugging
            throw new RuntimeException("Failed to retrieve employees", e);
        }
    }


    public String deleteEmployee(String id) {
        try {
            employeeRepository.deleteById(id);
        } catch (Exception e) {
            // Handle exception
        }
        return "Employee Deleted Successfully";
    }

    public String updateEmployee(Employeedto emp) {
        try {
            Employee employee = Employee.builder()
                    .firstName(emp.getFirstName())
                    .lastName(emp.getLastName())
                    .email(emp.getEmail())
                    .password(emp.getPassword())
                    .build();
            employeeRepository.save(employee);
        } catch (Exception e) {
            // Handle exception
        }
        return "Employee Updated Successfully";
    }
}