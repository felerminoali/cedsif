package com.cedsif.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cedsif.model.Departament;
import com.cedsif.service.DepartamentService;

@Controller
@RequestMapping("departament")
public class DepartamentController {

	@Autowired
	private DepartamentService departamentService;

	@GetMapping
	public String index(Model model) {
		return "redirect:/departament/all";
	}

	@GetMapping("/all")
	public String list(Model model) {
		return "/departament/list";
	}

	@GetMapping("/add")
	public String add(Model model) {
		model.addAttribute("departament", new Departament());
		return "/departament/add";

	}

	@GetMapping("/edit/{id}")
	public String edit(@PathVariable Integer id, Model model) {
		model.addAttribute("departament", departamentService.getDepartamentById(id));
		return "/departament/add";
	}

	@PostMapping(value = "/save")
	public String save(Departament departament, final RedirectAttributes ra) {
		Departament save = departamentService.save(departament);
		ra.addFlashAttribute("successFlash", "Departamento foi salvo com sucesso.");
		return "redirect:/departament";
	}
}
