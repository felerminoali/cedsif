package com.cedsif.restcontroller;

import java.text.MessageFormat;
import java.util.Optional;

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
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cedsif.model.Departament;
import com.cedsif.repository.DepartamentRepository;
import com.cedsif.service.DataTable;

@RestController
public class DepartamentRestController {

	private static Logger logger = LoggerFactory.getLogger(DepartamentRestController.class);
	
	@Autowired
	private DepartamentRepository repository;
	
	@RequestMapping(value = "/departament/api", method = RequestMethod.GET)
	public ResponseEntity<DataTable> listDepartaments(
			@RequestParam(value = "draw", defaultValue = "1") Integer draw,
			@RequestParam(value = "start", defaultValue = "0") Integer start,
			@RequestParam(value = "length", defaultValue = "1") Integer length,
			@RequestParam(value = "search[value]", defaultValue = "") String search_value,
			@RequestParam(value = "order[0][column]", defaultValue = "0") Integer order_column,
			@RequestParam(value = "order[0][dir]", defaultValue = "asc") String order,
			@RequestParam(value = "departament", required = false) Integer departament
	) {
		try {
	    int page = start / length; //Calculate page number

	    Direction direction = order.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
	    Pageable pageable = PageRequest.of(
	            page,
	            length,
	            Sort.by(direction, "name")
	    ) ;
	    
	    Page<Departament> responseData = null;
	    
	    if (search_value.equals("") || search_value == null) {
	    	responseData = repository.findAll(pageable);
	    }else {
	    	Specification<Departament> specification = Specification
	    		    .where(nameContains(search_value))
	    		    .or(codeContains(search_value));
	    	responseData = repository.findAll(specification, pageable);
	    }
	    
	    DataTable<Departament> dataTable = new DataTable<Departament>();

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
	
	@GetMapping(value = "/departament/api/{id}")
	public ResponseEntity<Departament> getDepatament(@PathVariable("id") Long id) {
		try {
		
			Optional<Departament> departament = repository.findById(id);
			return ResponseEntity.ok(departament.get());
		} catch (Exception e) {
			return new ResponseEntity<Departament>(HttpStatus.BAD_REQUEST);
		}

	}

	
	@PostMapping(value = "/departament/api/delete/{id}")
	public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
		try {
	    repository.deleteById(id);
		return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
	  }

	
	
	private static Specification<Departament> nameContains(String expression) {
	    return (root, query, builder) -> builder.like(root.get("name"), contains(expression));
	}

	private static Specification<Departament> codeContains(String expression) {
	    return (root, query, builder) -> builder.like(root.get("code"), contains(expression));
	}

	private static String contains(String expression) {
	    return MessageFormat.format("%{0}%", expression);
	}
	
}
