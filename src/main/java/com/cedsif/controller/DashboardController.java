package com.cedsif.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cedsif.service.DepartamentService;



@Controller
public class DashboardController {

	@Autowired
	private DepartamentService departamentService;
	
	
    @GetMapping("/")
    public String index(Model model) {
    	model.addAttribute("departaments", departamentService.getAllDepartaments());
        return "dashboard/index";
    }

}
