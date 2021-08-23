package com.cedsif.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cedsif.model.Departament;
import com.cedsif.repository.DepartamentRepository;

@Service
public class DepartamentServiceImpl implements DepartamentService{
	
	@Autowired
	private DepartamentRepository departamentRepository;

	@Override
	public List<Departament> getAllDepartaments() {
		return departamentRepository.findAll();
	}

	@Override
	public Departament getDepartamentById(long id) {
		return departamentRepository.getById(id);
	}

	@Override
	public Departament save(Departament departament) {
		return departamentRepository.save(departament);
	}

	
}
