package com.javafreelancedeveloper.recipewebapp.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.javafreelancedeveloper.recipewebapp.command.RecipeCommand;
import com.javafreelancedeveloper.recipewebapp.service.IngredientService;
import com.javafreelancedeveloper.recipewebapp.service.RecipeService;

@Slf4j
@Controller
public class IngredientController {

	private final RecipeService recipeService;
	private final IngredientService ingredientService;

	@Autowired
	public IngredientController(RecipeService recipeService, IngredientService ingredientService) {
		super();
		this.recipeService = recipeService;
		this.ingredientService = ingredientService;
	}

	@GetMapping
	@RequestMapping("/recipe/{id}/ingredients")
	public String show(@PathVariable String id, Model model) {
		log.debug("Displaying recipe ingredient list page");
		RecipeCommand recipe = recipeService.getRecipe(new Long(id));
		model.addAttribute("recipe", recipe);
		return "recipe/ingredient/list";
	}

	@GetMapping
	@RequestMapping("recipe/{recipeId}/ingredient/{id}/show")
	public String showRecipeIngredient(@PathVariable String recipeId, @PathVariable String id, Model model) {
		model.addAttribute("ingredient", ingredientService.findIngredient(Long.valueOf(recipeId), Long.valueOf(id)));
		return "recipe/ingredient/show";
	}

}
