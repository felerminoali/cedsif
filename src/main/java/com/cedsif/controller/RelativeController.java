package com.cedsif.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cedsif.model.Employee;
import com.cedsif.model.Relative;
import com.cedsif.repository.EmployeeRepository;
import com.cedsif.repository.RelativeRepository;

@Controller
@RequestMapping("relative")
public class RelativeController {

	private static Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private RelativeRepository repository;

	@Autowired
	private EmployeeRepository employeeRepository;

	@GetMapping("/add/{id}")
	public String addRelative(@PathVariable Long id, Model model) {

		Optional<Employee> employee = employeeRepository.findById(id);

		if (employee.isPresent()) {
			model.addAttribute("relative", new Relative());
			model.addAttribute("employee", employee.get());
			return "/employee/add_relative";
		}

		return "/employee/details/" + id;
	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Long id, Model model) {
		Relative relative = repository.findById(id).get();
		Optional<Employee> employee = employeeRepository.findById(relative.getEmployee().getId());

		if (employee.isPresent()) {
			model.addAttribute("relative", relative);
			model.addAttribute("employee", employee.get());
			return "/employee/add_relative";
		}
		return "/employee/details/" + relative.getEmployee().getId();
	}

	@PostMapping(value = "/save")
	public String save(Relative relative, final RedirectAttributes ra) {

		Optional<Employee> employee = employeeRepository.findById(relative.getEmployee().getId());

		if (employee.isPresent()) {
			relative.setEmployee(employee.get());
			
			Relative save = repository.save(relative);
			ra.addFlashAttribute("successFlash", "Dependente foi salvo com sucesso.");
			return "redirect:/employee/" + relative.getEmployee().getId() + "/details";
		}

		return "redirect:/employee";
	}
}
