package com.cedsif.controller;

import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cedsif.model.Category;
import com.cedsif.model.Employee;
import com.cedsif.model.Project;
import com.cedsif.repository.DepartamentRepository;
import com.cedsif.repository.EmployeeRepository;
import com.cedsif.repository.ProjectRepository;

@Controller
@RequestMapping("project")
public class ProjectController {

	@Autowired
	private ProjectRepository repository;
	
	@Autowired
	private DepartamentRepository departamentRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@GetMapping
	public String index(Model model) {
		return "redirect:/project/all";
	}

	@GetMapping("/all")
	public String list(Model model) {
		return "/project/list";
	}

	@GetMapping("/add")
	public String add(Model model) {
		model.addAttribute("project", new Project());
		model.addAttribute("departaments", departamentRepository.findAll());
		return "/project/add";

	}

	
	@GetMapping("/consultant/add/{id}")
	public String add(@PathVariable Long id, Model model) {
		Project project = repository.getById(id);
		model.addAttribute("project", project);
		
		Specification<Employee> specification = Specification
				.where(categoryEqual(Category.C));
    			//.and(projectEqual(id));
    		    
    	List<Employee> employees= employeeRepository.findAll(specification);
		
		
		model.addAttribute("employees", employees);
		return "/project/add-proj-consultant";

	}
	
	
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Long id, Model model) {
		Optional<Project> project = repository.findById(id);
		
		if(project.isPresent()) {
			model.addAttribute("project", project);
			model.addAttribute("departaments", departamentRepository.findAll());
			return "/project/add";
		}
		return "redirect:/project/all";
	}
	
	
	@GetMapping("{id}/details")
	public String details(@PathVariable Long id, Model model, final RedirectAttributes ra) {
		Optional<Project> project = repository.findById(id);
		
		if(project.isPresent()) {
			model.addAttribute("project",project.get());
			//model.addAttribute("users",new Project());
			return "/project/details";
		}
		return "redirect:/project/all";
	}

	@PostMapping(value = "/save")
	public String save(Project project, final RedirectAttributes ra) {
		Project save = repository.save(project);
		ra.addFlashAttribute("successFlash", "Projecto foi salvo com sucesso.");
		return "redirect:/project";
	}
	
	
	public static Specification<Employee> projectEqual(Long projectId) {
	    return new Specification<Employee>() {
	        @Override
	        public Predicate toPredicate(Root<Employee> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
	                Join join = root.join("projects");                   
	                return cb.notEqual(join.get("id"),projectId);
	        }
	    };
	}
	
	private static Specification<Employee> categoryEqual(Category category) {
		return (root, query, builder) -> builder.equal(root.get("category"), category);
	}
	
	
	
	
}
