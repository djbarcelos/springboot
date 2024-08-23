
package com.example.springboot.dtos;

import java.util.*;
import java.math.BigDecimal;
import java.util.stream.Collectors;

import com.example.springboot.models.EmployeeModel;

public class EmployeeDTO {
    private UUID id;
    private String cpf;
    private String name;
    private Date hireDate;
    private String workFunction;
    private BigDecimal salary;
    private String managerId;
    private List<EmployeeDTO> subordinates = new ArrayList<>();

    // Getters e Setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public String getWorkFunction() {
        return workFunction;
    }

    public void setWorkFunction(String workFunction) {
        this.workFunction = workFunction;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }

    public List<EmployeeDTO> getSubordinates() {
        return subordinates;
    }

    public void setSubordinates(List<EmployeeDTO> subordinates) {
        this.subordinates = subordinates;
    }

    // Método estático para converter de EmployeeModel para EmployeeDTO
    public static EmployeeDTO fromModel(EmployeeModel model) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(model.getId());
        dto.setCpf(model.getCpf());
        dto.setName(model.getName());
        dto.setHireDate(model.getHireDate());
        dto.setWorkFunction(model.getWorkFunction());
        dto.setSalary(model.getSalary());
        dto.setManagerId(model.getManagerId() != null ? model.getManagerId().getId().toString() : null);
        dto.setSubordinates(model.getSubordinates().stream()
                .map(EmployeeDTO::fromModel)
                .collect(Collectors.toList()));
        return dto;
    }
}
