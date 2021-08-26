package com.cedsif.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cedsif.model.Adminstrator;
import com.cedsif.model.Category;
import com.cedsif.model.Consultant;
import com.cedsif.model.Employee;
import com.cedsif.model.Manager;
import com.cedsif.model.Profile;
import com.cedsif.model.Users;
import com.cedsif.repository.EmployeeRepository;
import com.cedsif.repository.ProfileRepository;
import com.cedsif.repository.UsersRepository;

@Controller

@RequestMapping("employee")
public class EmployeeController {

	private static Logger logger = LoggerFactory.getLogger(EmployeeController.class);

	@Autowired
	private EmployeeRepository repository;

	@Autowired
	private ProfileRepository profileRepository;

	@Autowired
	private UsersRepository userRepository;

	@GetMapping
	public String index(Model model) {
		return "redirect:/employee/all";
	}

	@GetMapping("/all")
	public String list(Model model) {
		return "/employee/list";
	}

	@GetMapping("/consutant-area")
	public String list(@AuthenticationPrincipal final UserDetails userDetails, Model model) {

		String username = userDetails.getUsername();
		Optional<Users> user = userRepository.findByUsername(username);
		
		if (user.isPresent()) {
			if(user.get().getEmployee() != null ) {
				return "redirect:/employee/"+user.get().getEmployee().getId()+"/details";
			}
		} 
		
		return "redirect:/employee/all";
	}

	@GetMapping("{id}/details")
	public String details(@PathVariable Long id, Model model, final RedirectAttributes ra) {
		Optional<Employee> employee = repository.findById(id);

		if (employee.isPresent()) {
			model.addAttribute("employee", employee.get());
			Users users = new Users();
			model.addAttribute("users", users);
			return "/employee/details";
		}
		return "redirect:/employee/all";
	}

	// ----------------Consutant -----------------
	@GetMapping("/add-consultant")
	public String addConsultant(Model model) {
		consultantModel(model, new Employee());
		return "/employee/add_consultant";
	}

	public String editConsultant(Employee employee, Model model, final RedirectAttributes ra) {
		consultantModel(model, employee);
		return "/employee/add_consultant";
	}

	@PostMapping(value = "/save-consultant")
	public String save(Employee employee, final RedirectAttributes ra) {

		try {

			if (employee.getId() != null) {
				Consultant c = employee.getConsultant();
				c.setId(employee.getId());
				c.setEmployee(employee);
				employee.setConsultant(c);

				Employee save = repository.save(employee);

			} else {

				Employee e = copyEmployeeData(employee);
				Employee save = repository.save(e);

				Consultant c = new Consultant();
				c.setId(save.getId());
				c.setAddress(employee.getConsultant().getAddress());
				c.setProfile(employee.getConsultant().getProfile());
				c.setEmployee(save);
				save.setConsultant(c);
				save = repository.save(save);

			}

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		ra.addFlashAttribute("successFlash", "Funcionario foi salvo com sucesso.");
		return "redirect:/employee";
	}
	// --------------- End Consultant -----------------

	// --------------- Admin -----------------
	@GetMapping("/add-admin")
	public String addAdministrator(Model model) {
		adminModel(model, new Employee());
		return "/employee/add_admin";
	}

	public String editAdministrator(Employee employee, Model model, final RedirectAttributes ra) {
		adminModel(model, employee);
		return "/employee/add_admin";
	}

	@PostMapping(value = "/save-admin")
	public String saveAdmin(Employee employee, final RedirectAttributes ra) {

		try {

			if (employee.getId() != null) {
				Adminstrator temp = employee.getAdminstrator();
				temp.setId(employee.getId());
				temp.setEmployee(employee);
				employee.setAdminstrator(temp);

				Employee save = repository.save(employee);
			} else {

				Employee e = copyEmployeeData(employee);

				Employee save = repository.save(e);

				Adminstrator temp = new Adminstrator();
				temp.setId(save.getId());
				temp.setShift(employee.getAdminstrator().getShift());
				temp.setEmployee(save);
				save.setAdminstrator(temp);
				save = repository.save(save);
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		ra.addFlashAttribute("successFlash", "Funcionario foi salvo com sucesso.");
		return "redirect:/employee";
	}

	// --------------- End Admin -----------------

	// --------------- Manager -----------------

	@GetMapping("/add-manager")
	public String addManager(Model model) {
		managerModel(model, new Employee());
		return "/employee/add_manager";

	}

	public String editManager(Employee employee, Model model, final RedirectAttributes ra) {
		managerModel(model, employee);
		return "/employee/add_manager";
	}

	@PostMapping(value = "/save-manager")
	public String saveManager(Employee employee, final RedirectAttributes ra) {

		try {
			if (employee.getId() != null) {
				Manager temp = employee.getManager();
				temp.setId(employee.getId());
				temp.setEmployee(employee);
				employee.setManager(temp);
				repository.save(employee);
			} else {

				Employee e = copyEmployeeData(employee);

				Employee save = repository.save(e);

				Manager temp = new Manager();
				temp.setId(save.getId());
				temp.setInstitution(save.getManager().getInstitution());
				temp.setCourse(employee.getManager().getCourse());
				temp.setEmployee(save);
				save.setManager(temp);
				repository.save(save);
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		ra.addFlashAttribute("successFlash", "Funcionario foi salvo com sucesso.");
		return "redirect:/employee";
	}

	// --------------- End Manager -----------------
	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Long id, Model model, final RedirectAttributes ra) {
		Optional<Employee> employee = repository.findById(id);

		String page = "redirect:/employee";
		if (employee.isPresent()) {

			if (employee.get().getCategory() == Category.C) {
				page = editConsultant(employee.get(), model, ra);
			} else if (employee.get().getCategory() == Category.A) {
				page = editAdministrator(employee.get(), model, ra);
			} else if (employee.get().getCategory() == Category.G) {
				page = editManager(employee.get(), model, ra);
			}

		} else {
			ra.addFlashAttribute("successFlash", "Funcionario não existe.");
			return page;
		}

		return page;
	}

	private void consultantModel(Model model, Employee employee) {
		model.addAttribute("employee", employee);
		List<Profile> profiles = profileRepository.findAll();
		model.addAttribute("profiles", profiles);
		model.addAttribute("category", Category.C);
	}

	private void adminModel(Model model, Employee employee) {
		model.addAttribute("employee", employee);
		model.addAttribute("category", Category.A);
	}

	private void managerModel(Model model, Employee employee) {
		model.addAttribute("employee", employee);
		model.addAttribute("category", Category.G);
	}

	private Employee copyEmployeeData(Employee employee) {
		Employee e = new Employee();
		e.setName(employee.getName());
		e.setNuit(employee.getNuit());
		e.setSex(employee.getSex());
		e.setCategory(employee.getCategory());
		return e;
	}

}
