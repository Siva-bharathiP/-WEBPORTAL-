package com.EXCELDATAINMONGODB.EXCELDATADB;

import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Optional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


    public String deleteEmployeeRow(String id, int rowIndex) {
        try {
            Optional<Employee> optionalEmployee = employeeRepository.findById(id);
            if (optionalEmployee.isPresent()) {
                Employee employee = optionalEmployee.get();
                List<Map<String, Object>> excelData = employee.getExcelData();

                if (rowIndex >= 0 && rowIndex < excelData.size()) {
                    excelData.remove(rowIndex);
                    employeeRepository.save(employee);
                    return "Row Deleted Successfully";
                } else {
                    return "Invalid Row Index";
                }
            } else {
                return "Employee not found";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "Failed to delete row: " + e.getMessage();
        }
    }


    public String updateEmployee(@RequestBody Employeedto emp) {
        try {
            // Check if id is null
            if (emp.getId() == null) {
                throw new IllegalArgumentException("Employee id cannot be null");
            }

            Employee existingEmployee = employeeRepository.findById(emp.getId())
                    .orElseThrow(() -> new RuntimeException("Employee not found"));

            existingEmployee.setFirstName(emp.getFirstName());
            existingEmployee.setLastName(emp.getLastName());
            existingEmployee.setEmail(emp.getEmail());
            existingEmployee.setPassword(emp.getPassword());
            // Clear the existing excelData list
            existingEmployee.getExcelData().clear();

            // Add the new excelData from the received data
            for (Map<String, Object> entry : emp.getExcelData()) {
                existingEmployee.getExcelData().add(new HashMap<>(entry));
            }
            employeeRepository.save(existingEmployee);

            return "Employee Updated Successfully";
        } catch (Exception e) {
            e.printStackTrace();  // Print the exception details for debugging
            return "Failed to update employee: " + e.getMessage();
        }
    }

}