package com.example.springboot.repositories;

import java.util.*;
import org.springframework.data.domain.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.example.springboot.models.EmployeeModel;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeModel, UUID> {

    @Query("SELECT e FROM EmployeeModel e WHERE e.managerId.id = :managerId")
    List<EmployeeModel> findByManagerId(@Param("managerId") UUID managerId);

    @Query("SELECT e FROM EmployeeModel e WHERE EXTRACT(YEAR FROM e.hireDate) =:year")
    List<EmployeeModel> findByYearOfHire(@Param("year") int year);

    @Query("SELECT e FROM EmployeeModel e WHERE EXTRACT(YEAR FROM e.hireDate) =:year")
    Page<EmployeeModel> findByYearOfHirePerPage(@Param("year") int year, Pageable pageable);
}
