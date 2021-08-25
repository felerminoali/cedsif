package com.cedsif.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cedsif.model.Authorities;
import com.cedsif.model.Category;
import com.cedsif.model.Employee;
import com.cedsif.model.Users;
import com.cedsif.repository.EmployeeRepository;
import com.cedsif.repository.UsersRepository;

@Controller
@RequestMapping("users")
public class UserController {

	private static Logger logger = LoggerFactory.getLogger(EmployeeController.class);
	@Autowired
	private UsersRepository repository;

	@Autowired
	UserDetailsManager userDetailsService;
	
	@Autowired
	private EmployeeRepository employeeRepository;

	
	@PostMapping(value = "/save")
	public String save(Users users, final RedirectAttributes ra) {
		
		Employee employee = employeeRepository.findById(users.getEmployee().getId()).get();
		if (!repository.findByUsername(users.getUsername()).isPresent()) {
			
			Users user = repository.save(users);
			
			List<Authorities> authorities = new ArrayList<>();
			
			if(employee.getCategory() == Category.A) {
				authorities.add(new  Authorities(users.getUsername(), "ADMIN", user));
			}else if(employee.getCategory() == Category.G) {
				authorities.add(new  Authorities(users.getUsername(), "MANAGER", user));
			}else if(employee.getCategory() == Category.C) {
				authorities.add(new  Authorities(users.getUsername(), "CONSULTANT", user));
			}
			
			PasswordEncoder encoder = new BCryptPasswordEncoder();
			
			user.setEmployee(employee);
			user.setAuthorities(authorities);
			user.setPassword(encoder.encode(users.getPassword()));
			user.setEnabled(true);
			
			repository.save(user);
			
			ra.addFlashAttribute("successFlash", "Usuaário foi salvo com sucesso.");
			
		}else {
			ra.addFlashAttribute("errorFlash", "Usuário já existe.");
		}
		
		
		return "redirect:/employee/" + users.getEmployee().getId() + "/details";
	}
}
