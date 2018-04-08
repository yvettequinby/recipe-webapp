package com.javafreelancedeveloper.recipewebapp.service.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.javafreelancedeveloper.recipewebapp.command.RecipeCommand;
import com.javafreelancedeveloper.recipewebapp.converter.RecipeCommandToRecipeConverter;
import com.javafreelancedeveloper.recipewebapp.converter.RecipeToRecipeCommandConverter;
import com.javafreelancedeveloper.recipewebapp.domain.Recipe;
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
	public Set<Recipe> listRecipes() {
		log.debug("Requesting recipe list...");
		Set<Recipe> recipes = new HashSet<Recipe>();
		recipeRepository.findAll().iterator().forEachRemaining(recipes::add);
		return recipes;
	}

	@Override
	public Recipe findById(Long id) {
		Optional<Recipe> recipe = recipeRepository.findById(id);
		if (!recipe.isPresent()) {
			throw new RuntimeException("Recipe Not Found!");
		}
		return recipe.get();
	}

	@Override
	@Transactional
	public RecipeCommand saveRecipe(RecipeCommand recipeCommand) {
		Recipe detachedRecipe = recipeCommandToRecipeConverter.convert(recipeCommand);
		Recipe savedRecipe = recipeRepository.save(detachedRecipe);
		RecipeCommand result = recipeToRecipeCommandConverter.convert(savedRecipe);
		return result;
	}

}
