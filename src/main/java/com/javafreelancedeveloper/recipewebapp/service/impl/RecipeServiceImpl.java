package com.javafreelancedeveloper.recipewebapp.service.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javafreelancedeveloper.recipewebapp.command.RecipeCommand;
import com.javafreelancedeveloper.recipewebapp.converter.RecipeCommandToRecipeConverter;
import com.javafreelancedeveloper.recipewebapp.converter.RecipeToRecipeCommandConverter;
import com.javafreelancedeveloper.recipewebapp.domain.Recipe;
import com.javafreelancedeveloper.recipewebapp.exception.NotFoundException;
import com.javafreelancedeveloper.recipewebapp.repository.RecipeRepository;
import com.javafreelancedeveloper.recipewebapp.service.RecipeService;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {

	private RecipeRepository recipeRepository;
	private final RecipeCommandToRecipeConverter recipeCommandToRecipeConverter;
	private final RecipeToRecipeCommandConverter recipeToRecipeCommandConverter;

	@Autowired
	public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipeConverter recipeCommandToRecipeConverter, RecipeToRecipeCommandConverter recipeToRecipeCommandConverter) {
		super();
		this.recipeRepository = recipeRepository;
		this.recipeCommandToRecipeConverter = recipeCommandToRecipeConverter;
		this.recipeToRecipeCommandConverter = recipeToRecipeCommandConverter;
	}

	@Override
	@Transactional
	public Set<RecipeCommand> listRecipes() {
		log.debug("Requesting recipe list...");
		Set<RecipeCommand> recipeCommands = new HashSet<RecipeCommand>();
		Iterable<Recipe> recipes = recipeRepository.findAll();
		recipes.forEach(recipe -> recipeCommands.add(recipeToRecipeCommandConverter.convert(recipe)));
		return recipeCommands;
	}

	@Override
	@Transactional
	public RecipeCommand getRecipe(Long id) {
		Optional<Recipe> recipe = recipeRepository.findById(id);
		if (!recipe.isPresent()) {
			throw new NotFoundException("Recipe Not Found For ID [" + id + "].");
		}
		RecipeCommand recipeCommand = recipeToRecipeCommandConverter.convert(recipe.get());
		return recipeCommand;
	}

	@Override
	@Transactional
	public RecipeCommand saveRecipe(RecipeCommand recipeCommand) {
		/*if(recipeCommand.getId()!=null) {
			Recipe staleRecipe = recipeRepository.findById(recipeCommand.getId()).get();
			Set<Category> staleCategories = staleRecipe.getCategories();
		}*/
		Recipe detachedRecipe = recipeCommandToRecipeConverter.convert(recipeCommand);
		Recipe savedRecipe = recipeRepository.save(detachedRecipe);
		RecipeCommand result = recipeToRecipeCommandConverter.convert(savedRecipe);
		return result;
	}
	
	@Override
	@Transactional
	public void deleteRecipe(Long recipeId) {
		recipeRepository.deleteById(recipeId);
	}

}
