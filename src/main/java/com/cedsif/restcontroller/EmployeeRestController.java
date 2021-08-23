package com.cedsif.restcontroller;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cedsif.model.Employee;
import com.cedsif.repository.EmployeeRepository;
import com.cedsif.service.DataTable;

@RestController
public class EmployeeRestController {

private static Logger logger = LoggerFactory.getLogger(DepartamentRestController.class);
	
	@Autowired
	private EmployeeRepository repository;
	
	@RequestMapping(value = "/employee/api")
	public ResponseEntity<DataTable> listDepartaments(
			@RequestParam(value = "draw", defaultValue = "1") Integer draw,
			@RequestParam(value = "start", defaultValue = "0") Integer start,
			@RequestParam(value = "length", defaultValue = "1") Integer length,
			@RequestParam(value = "search[value]", defaultValue = "") String search_value,
			@RequestParam(value = "order[0][column]", defaultValue = "0") Integer order_column,
			@RequestParam(value = "order[0][dir]", defaultValue = "asc") String order,
			@RequestParam(value = "employee", required = false) Long employee
	) {
		try {
	    int page = start / length; //Calculate page number

	    Direction direction = order.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
	    Pageable pageable = PageRequest.of(
	            page,
	            length,
	            Sort.by(direction, "name")
	    ) ;
	    
	    Page<Employee> responseData = null;
	    
	    if (search_value.equals("") || search_value == null) {
	    	responseData = repository.findAll(pageable);
	    }else {
	    	Specification<Employee> specification = Specification
	    		    .where(nameContains(search_value))
	    		    .or(nuitContains(search_value));
	    	responseData = repository.findAll(specification, pageable);
	    }
	    
	    DataTable<Employee> dataTable = new DataTable<Employee>();

	    dataTable.setData(responseData.getContent());
	    dataTable.setRecordsTotal(responseData.getTotalElements());
	    dataTable.setRecordsFiltered(responseData.getTotalElements());

	    dataTable.setDraw(draw);
	    dataTable.setStart(start);

	    return ResponseEntity.ok(dataTable);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
      }
		
		
	}
	
	private static Specification<Employee> nameContains(String expression) {
	    return (root, query, builder) -> builder.like(root.get("name"), contains(expression));
	}

	private static Specification<Employee> nuitContains(String expression) {
	    return (root, query, builder) -> builder.like(root.get("nuit"), contains(expression));
	}

	private static String contains(String expression) {
	    return MessageFormat.format("%{0}%", expression);
	}
	
}
