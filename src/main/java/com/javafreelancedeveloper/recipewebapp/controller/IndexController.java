package com.javafreelancedeveloper.recipewebapp.controller;

import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.javafreelancedeveloper.recipewebapp.domain.Recipe;
import com.javafreelancedeveloper.recipewebapp.service.RecipeService;

@Slf4j
@Controller
public class IndexController {
	
	private final RecipeService recipeService;
	
	@Autowired
	public IndexController(RecipeService recipeService) {
		super();
		this.recipeService = recipeService;
	}

	@RequestMapping({"", "/", "/index"})
	public String displayIndexPage(Model model) {
		log.debug("Displaying idnex page");
		Set<Recipe> recipes = recipeService.listRecipes();
		model.addAttribute("recipes", recipes);
		return "index";
	}

}
