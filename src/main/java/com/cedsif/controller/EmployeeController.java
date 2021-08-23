package com.cedsif.controller;

import java.util.List;
import java.util.Optional;

import javax.persistence.MapsId;

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

import com.cedsif.model.Category;
import com.cedsif.model.Consultant;
import com.cedsif.model.Employee;
import com.cedsif.model.Profile;
import com.cedsif.repository.ConsultantRepository;
import com.cedsif.repository.EmployeeRepository;
import com.cedsif.repository.ProfileRepository;
import com.cedsif.restcontroller.DepartamentRestController;

@Controller
@RequestMapping("employee")
public class EmployeeController {

	private static Logger logger = LoggerFactory.getLogger(DepartamentRestController.class);
	
	@Autowired
	private EmployeeRepository repository;

	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private ConsultantRepository consultantRepository;

	@GetMapping
	public String index(Model model) {
		return "redirect:/employee/all";
	}

	@GetMapping("/all")
	public String list(Model model) {
		return "/employee/list";
	}

	@GetMapping("/add-consultant")
	public String addConsultant(Model model) {
		Consultant consultant = new Consultant();
		consultant.setEmployee(new Employee());
		consultantModel(model, consultant);
		return "/employee/add_consultant";
	}

	@GetMapping("/edit-consultant/{id}")
	public String edit(@PathVariable Long id, Model model) {
		Consultant consultant = consultantRepository.findById(id).get();
		consultantModel(model, consultant);
		return "/employee/add_consultant";
	}
	
	private void consultantModel(Model model, Consultant consultant) {
		model.addAttribute("employee", consultant);
		List<Profile> profiles = profileRepository.findAll();
		model.addAttribute("profiles", profiles);
		model.addAttribute("category", Category.CONSULTANT);
	}

	@PostMapping(value = "/save-consultant")
	public String save(Consultant consultant, final RedirectAttributes ra) {
		logger.error(consultant.toString());
		//Consultant save = consultantRepository.save(consultant);
		ra.addFlashAttribute("successFlash", "Funcionario foi salvo com sucesso.");
		return "redirect:/employee";
	}

}
