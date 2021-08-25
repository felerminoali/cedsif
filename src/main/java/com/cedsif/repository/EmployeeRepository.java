package com.cedsif.repository;

import com.cedsif.model.Employee;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	<T> Page<T> findAll(Specification<T> spec, Pageable pageable);
	<T> List<T> findAll(Specification<T> spec);

}
