package com.cedsif.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.cedsif.model.Users;

public interface UsersRepository  extends JpaRepository<Users, Long> {
	<T> Page<T> findAll(Specification<T> spec, Pageable pageable);
	Optional<Users> findByUsername(String username);
}
