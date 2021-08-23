package com.cedsif.repository;

import com.cedsif.model.Departament;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartamentRepository extends JpaRepository<Departament, Long> {

	Page<Departament> findByNameContaining(String search_value, Pageable pageable);
	
	<T> Page<T> findAll(Specification<T> spec, Pageable pageable);


}
