package com.cedsif.restcontroller;

import java.text.MessageFormat;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cedsif.model.Category;
import com.cedsif.model.Employee;
import com.cedsif.repository.EmployeeRepository;
import com.cedsif.service.DataTable;

@RestController
public class EmployeeRestController {

private static Logger logger = LoggerFactory.getLogger(DepartamentRestController.class);
	
	@Autowired
	private EmployeeRepository repository;
	
	@RequestMapping(value = "/employee/api")
	public ResponseEntity<DataTable> listEmployee(
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
	
	
	@RequestMapping(value = "/employee/api/consultant/{project}")
	public ResponseEntity<DataTable> consultantList(
			@RequestParam(value = "draw", defaultValue = "1") Integer draw,
			@RequestParam(value = "start", defaultValue = "0") Integer start,
			@RequestParam(value = "length", defaultValue = "1") Integer length,
			@RequestParam(value = "search[value]", defaultValue = "") String search_value,
			@RequestParam(value = "order[0][column]", defaultValue = "0") Integer order_column,
			@RequestParam(value = "order[0][dir]", defaultValue = "asc") String order,
			@PathVariable Long project
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
		    	Specification<Employee> specification = Specification
		    			.where(projectEqual(project))
		    			.and(categoryEqual(Category.C));
		    	responseData = repository.findAll(specification, pageable);
		    }else {
		    	Specification<Employee> specification = Specification
		    			.where(projectEqual(project))
		    			.and(nameContains(search_value))
		    			.and(categoryEqual(Category.C));
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
	
	@PostMapping("/employee/api/delete/{id}")
	public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
		try {
	    repository.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	  }
	
	private static Specification<Employee> nameContains(String expression) {
	    return (root, query, builder) -> builder.like(root.get("name"), contains(expression));
	}

	private static Specification<Employee> nuitContains(String expression) {
	    return (root, query, builder) -> builder.like(root.get("nuit"), contains(expression));
	}
	
	private static Specification<Employee> categoryEqual(Category category) {
		return (root, query, builder) -> builder.equal(root.get("category"), category);
	}
	
	public static Specification<Employee> projectEqual(Long projectId) {
	    return new Specification<Employee>() {
	        @Override
	        public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
	                Join join = root.join("projects");                   
	                return cb.equal(join.get("id"),projectId);
	        }
	    };
	}


	private static String contains(String expression) {
	    return MessageFormat.format("%{0}%", expression);
	}
	
}
