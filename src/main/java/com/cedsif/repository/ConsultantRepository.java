package com.cedsif.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cedsif.model.Consultant;


public interface ConsultantRepository extends JpaRepository<Consultant, Long> {
	<T> Page<T> findAll(Specification<T> spec, Pageable pageable);
}
