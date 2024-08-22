package com.example.springboot.models;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "tb_employees")
public class EmployeeModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false, length = 14)
    private String cpf;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(name = "hire_date", nullable = false)
    private Date hireDate;

    @Column(name = "work_function", nullable = false, length = 100)
    private String workFunction;

    @Column(nullable = false)
    private BigDecimal salary;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    @JsonBackReference
    private EmployeeModel managerId;

    @OneToMany(mappedBy = "managerId")
    @JsonManagedReference
    private List<EmployeeModel> subordinates;

    // Getters and Setters

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

    public EmployeeModel getManagerId() {
        return managerId;
    }

    public void setManagerId(EmployeeModel managerId) {
        this.managerId = managerId;
    }

    public List<EmployeeModel> getSubordinates() {
        return subordinates;
    }

    public void setSubordinates(List<EmployeeModel> subordinates) {
        this.subordinates = subordinates;
    }
}
