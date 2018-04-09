package com.javafreelancedeveloper.recipewebapp.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.javafreelancedeveloper.recipewebapp.command.RecipeCommand;
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

	@GetMapping
	@RequestMapping("/recipe/{id}/show")
	public String show(@PathVariable String id, Model model) {
		log.debug("Displaying recipe show page");
		RecipeCommand recipe = recipeService.getRecipe(new Long(id));
		model.addAttribute("recipe", recipe);
		return "recipe/show";
	}

	@GetMapping
	@RequestMapping("/recipe/new")
	public String newRecipe(Model model) {
		log.debug("Displaying recipe new page");
		model.addAttribute("recipe", new RecipeCommand());
		return "recipe/recipeform";
	}
	
	@GetMapping
	@RequestMapping("/recipe/{id}/update")
	public String updateRecipe(@PathVariable String id, Model model) {
		log.debug("Displaying recipe update page");
		RecipeCommand recipeCommand = recipeService.getRecipe(new Long(id));
		model.addAttribute("recipe", recipeCommand);
		return "recipe/recipeform";
	}

	@PostMapping
	@RequestMapping("/recipe")
	public String saveRecipe(@ModelAttribute RecipeCommand recipeCommand, Model model) {
		log.debug("Saving recipe");
		RecipeCommand savedRecipe = recipeService.saveRecipe(recipeCommand);
		return "redirect:/recipe/" + savedRecipe.getId() + "/show";
	}
	
	@GetMapping
	@RequestMapping("/recipe/{id}/delete")
	public String deleteRecipe(@PathVariable String id, Model model) {
		log.debug("Deleting recipe");
		recipeService.deleteRecipe(new Long(id));
		return "redirect:/";
	}

}
