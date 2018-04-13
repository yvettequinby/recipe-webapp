package com.javafreelancedeveloper.recipewebapp.controller;

import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.javafreelancedeveloper.recipewebapp.command.RecipeCommand;
import com.javafreelancedeveloper.recipewebapp.exception.NotFoundException;
import com.javafreelancedeveloper.recipewebapp.service.RecipeService;

@Slf4j
@Controller
public class RecipeController {
	private static final String RECIPE_RECIPEFORM_URL = "recipe/recipeform";
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
		return RECIPE_RECIPEFORM_URL;
	}

	@GetMapping
	@RequestMapping("/recipe/{id}/update")
	public String updateRecipe(@PathVariable String id, Model model) {
		log.debug("Displaying recipe update page");
		RecipeCommand recipeCommand = recipeService.getRecipe(new Long(id));
		model.addAttribute("recipe", recipeCommand);
		return RECIPE_RECIPEFORM_URL;
	}

	@PostMapping("recipe")
	public String saveOrUpdate(@Valid @ModelAttribute("recipe") RecipeCommand command,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			bindingResult.getAllErrors().forEach(objectError -> {
				log.debug(objectError.toString());
			});
			return RECIPE_RECIPEFORM_URL;
		}
		RecipeCommand savedCommand = recipeService.saveRecipe(command);
		return "redirect:/recipe/" + savedCommand.getId() + "/show";
	}

	@GetMapping
	@RequestMapping("/recipe/{id}/delete")
	public String deleteRecipe(@PathVariable String id, Model model) {
		log.debug("Deleting recipe");
		recipeService.deleteRecipe(new Long(id));
		return "redirect:/";
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public ModelAndView handleNotFound(Exception exception) {
		log.error("Handling NotFoundException.", exception);
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("exception", exception);
		modelAndView.addObject("errorTitle", "404 Not Found");
		modelAndView.setViewName("404error");
		return modelAndView;
	}
}
