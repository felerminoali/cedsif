package com.cedsif.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("backlog")
public class BacklogController {

	@GetMapping
	public String index(Model model) {
		return "redirect:/backlog/all";
	}

	@GetMapping("/all")
	public String list(Model model) {
		return "/backlog/list";
	}

}
