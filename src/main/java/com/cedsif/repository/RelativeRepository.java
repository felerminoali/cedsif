package com.cedsif.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cedsif.model.Relative;

public interface RelativeRepository extends JpaRepository<Relative, Long> {
	<T> Page<T> findAll(Specification<T> spec, Pageable pageable);
	
}
