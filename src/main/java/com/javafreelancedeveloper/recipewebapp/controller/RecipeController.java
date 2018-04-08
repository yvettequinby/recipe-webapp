package com.javafreelancedeveloper.recipewebapp.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.javafreelancedeveloper.recipewebapp.domain.Recipe;
import com.javafreelancedeveloper.recipewebapp.service.RecipeService;

@Slf4j
@Controller
public class RecipeController {
	
	private final RecipeService recipeService;
	
	@Autowired
	public RecipeController(RecipeService recipeService) {
		super();
		this.recipeService = recipeService;
	}
	
	@RequestMapping("/recipe/show/{id}")
	public String show(@PathVariable String id, Model model) {
		log.debug("Displaying recipe show page");
		Recipe recipe= recipeService.findById(new Long(id));
		model.addAttribute("recipe", recipe);
		return "recipe/show";
	}

}
