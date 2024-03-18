package com.EXCELDATAINMONGODB.EXCELDATADB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3002", allowedHeaders = "*")
@RestController
@RequestMapping("api/form")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public String createEmployee(@RequestBody Payload payload) {
        return employeeService.createEmployee(payload);
    }

    @GetMapping("/get")
    public ResponseEntity<List<Employee>> getEmployee() {
        try {
            List<Employee> employees = employeeService.getEmployee();
            return new ResponseEntity<>(employees, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();  // Print the exception details for debugging
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @DeleteMapping("/delete/{id}/{rowIndex}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteEmployeeRow(@PathVariable String id, @PathVariable int rowIndex) {
        return employeeService.deleteEmployeeRow(id, rowIndex);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.CREATED)
    public String updateEmployee(@RequestBody Employeedto emp) {
        System.out.println("Received data: " + emp.toString());

        return employeeService.updateEmployee(emp);
    }
}