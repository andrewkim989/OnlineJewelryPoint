package com.andrewkim.javaproject.controllers;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.andrewkim.javaproject.models.Product;
import com.andrewkim.javaproject.services.ProjectService;

@RestController
public class ProjectControllerTwo {
	private final ProjectService projectService;
	
	public ProjectControllerTwo (ProjectService projectService) {
		this.projectService = projectService;
	}
	@RequestMapping("/admin/jewels")
    public String displayJewels (Model model, @RequestParam("selectJewels") String selectJewels) {
		if (selectJewels.equals("All")) {
			List<Product> products = projectService.allProducts();				
			model.addAttribute("products", products);
			return "jewels.jsp";
		}
		else {
			List<Product> products = projectService.findJewels(selectJewels);	
			model.addAttribute("products", products);
			return "jewels.jsp";
		}
	}
}
