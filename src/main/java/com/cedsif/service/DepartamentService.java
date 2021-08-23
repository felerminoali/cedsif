package com.cedsif.service;

import java.util.List;
import com.cedsif.model.Departament;


public interface DepartamentService {
	public List<Departament> getAllDepartaments();
	public Departament getDepartamentById(long id);
	public Departament save(Departament departament);
	
}	
