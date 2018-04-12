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

import com.javafreelancedeveloper.recipewebapp.command.IngredientCommand;
import com.javafreelancedeveloper.recipewebapp.command.RecipeCommand;
import com.javafreelancedeveloper.recipewebapp.command.UnitOfMeasureCommand;
import com.javafreelancedeveloper.recipewebapp.service.IngredientService;
import com.javafreelancedeveloper.recipewebapp.service.RecipeService;
import com.javafreelancedeveloper.recipewebapp.service.UnitOfMeasureService;

@Slf4j
@Controller
public class IngredientController {

	private final RecipeService recipeService;
	private final IngredientService ingredientService;
	private final UnitOfMeasureService unitOfMeasureService;

	@Autowired
	public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
		super();
		this.recipeService = recipeService;
		this.ingredientService = ingredientService;
		this.unitOfMeasureService = unitOfMeasureService;
	}

	@GetMapping
	@RequestMapping("/recipe/{id}/ingredients")
	public String listIngredients(@PathVariable String id, Model model) {
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

	@GetMapping
	@RequestMapping("recipe/{recipeId}/ingredient/{id}/update")
	public String updateRecipeIngredient(@PathVariable String recipeId, @PathVariable String id, Model model) {
		model.addAttribute("ingredient", ingredientService.findIngredient(Long.valueOf(recipeId), Long.valueOf(id)));
		model.addAttribute("uomList", unitOfMeasureService.listUnitOfMeasures());
		return "recipe/ingredient/ingredientform";
	}

	@PostMapping("recipe/{recipeId}/ingredient")
	public String saveOrUpdate(@ModelAttribute IngredientCommand command) {
		IngredientCommand savedCommand = ingredientService.saveIngredient(command);
		log.debug("saved receipe id:" + savedCommand.getRecipeId());
		log.debug("saved ingredient id:" + savedCommand.getId());
		return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";
	}

	@GetMapping
	@RequestMapping("recipe/{recipeId}/ingredient/new")
	public String newIngredient(@PathVariable String recipeId, Model model) {
		RecipeCommand recipeCommand = recipeService.getRecipe(Long.valueOf(recipeId));
		// TODO raise exception if recipeCommand is null
		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setRecipeId(recipeCommand.getId());
		ingredientCommand.setUom(new UnitOfMeasureCommand());
		model.addAttribute("ingredient", ingredientCommand);
		model.addAttribute("uomList", unitOfMeasureService.listUnitOfMeasures());
		return "recipe/ingredient/ingredientform";
	}

	@GetMapping
	@RequestMapping("recipe/{recipeId}/ingredient/{id}/delete")
	public String deleteIngredient(@PathVariable String recipeId, @PathVariable String id) {
		log.debug("deleting ingredient id:" + id);
		ingredientService.deleteIngredient(Long.valueOf(recipeId), Long.valueOf(id));
		return "redirect:/recipe/" + recipeId + "/ingredients";
	}

}
