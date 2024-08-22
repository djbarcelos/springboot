package com.example.springboot.controllers;

import java.util.*;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.springboot.dtos.EmployeeRecordDto;
import com.example.springboot.models.EmployeeModel;
import com.example.springboot.repositories.EmployeeRepository;

import jakarta.validation.Valid;

@RestController
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/employees")
    public ResponseEntity<?> getAllEmployees(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection) {

        if ((page == null || size == null) && year == null) {
            // No pagination, returns all records
            return ResponseEntity.status(HttpStatus.OK).body(employeeRepository.findAll());
        }

        if ((page == null || size == null) && year != null) {
            List<EmployeeModel> employees = employeeRepository.findByYearOfHire(year);
            return ResponseEntity.status(HttpStatus.OK).body(employees);
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));

        if ((page != null || size != null) && year != null) {
            Page<EmployeeModel> employees = employeeRepository.findByYearOfHirePerPage(year, pageable);
            return ResponseEntity.status(HttpStatus.OK).body(employees);
        }

        Page<EmployeeModel> employees = employeeRepository.findAll(pageable);
        return ResponseEntity.status(HttpStatus.OK).body(employees);
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Object> getOneEmployee(@PathVariable(value = "id") UUID id) {
        Optional<EmployeeModel> employee = employeeRepository.findById(id);

        if (employee.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(employee.get());
    }

    @PostMapping("/employees")
    public ResponseEntity<EmployeeModel> saveEmployee(@RequestBody @Valid EmployeeRecordDto employeeRecordDto) {
        var employeeModel = new EmployeeModel();
        BeanUtils.copyProperties(employeeRecordDto, employeeModel);

        if (employeeRecordDto.managerId() != null) {
            EmployeeModel manager = employeeRepository.findById(employeeRecordDto.managerId())
                    .orElseThrow(() -> new RuntimeException("Manager not found"));
            employeeModel.setManagerId(manager);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(employeeRepository.save(employeeModel));
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Object> updateEmployee(@PathVariable(value = "id") UUID id,
            @RequestBody @Valid EmployeeRecordDto employeeRecordDto) {

        Optional<EmployeeModel> employee = employeeRepository.findById(id);
        if (employee.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }

        var employeeModel = employee.get();
        BeanUtils.copyProperties(employeeRecordDto, employeeModel);

        if (employeeRecordDto.managerId() != null) {
            EmployeeModel manager = employeeRepository.findById(employeeRecordDto.managerId())
                    .orElseThrow(() -> new RuntimeException("Manager not found"));
            employeeModel.setManagerId(manager);
        }

        return ResponseEntity.status(HttpStatus.OK).body(employeeRepository.save(employeeModel));
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable(value = "id") UUID id) {
        Optional<EmployeeModel> employee = employeeRepository.findById(id);

        if (employee.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
        }

        List<EmployeeModel> employeesReferencingManager = employeeRepository.findByManagerId(id);

        if (!employeesReferencingManager.isEmpty()) {
            for (EmployeeModel emp : employeesReferencingManager) {
                emp.setManagerId(null);
                employeeRepository.save(emp);
            }
        }

        employeeRepository.delete(employee.get());
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully.");
    }

}
