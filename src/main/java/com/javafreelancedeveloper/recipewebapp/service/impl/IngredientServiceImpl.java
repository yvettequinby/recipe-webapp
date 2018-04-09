package com.javafreelancedeveloper.recipewebapp.service.impl;

import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javafreelancedeveloper.recipewebapp.command.IngredientCommand;
import com.javafreelancedeveloper.recipewebapp.converter.IngredientToIngredientCommandConverter;
import com.javafreelancedeveloper.recipewebapp.domain.Recipe;
import com.javafreelancedeveloper.recipewebapp.repository.RecipeRepository;
import com.javafreelancedeveloper.recipewebapp.service.IngredientService;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

	private final IngredientToIngredientCommandConverter ingredientToIngredientCommandConverter;
	private final RecipeRepository recipeRepository;

	public IngredientServiceImpl(IngredientToIngredientCommandConverter ingredientToIngredientCommandConverter, RecipeRepository recipeRepository) {
		this.ingredientToIngredientCommandConverter = ingredientToIngredientCommandConverter;
		this.recipeRepository = recipeRepository;
	}

	@Override
	@Transactional
	public IngredientCommand findIngredient(Long recipeId, Long ingredientId) {

		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
		if (!recipeOptional.isPresent()) {
			// todo impl error handling
			log.error("Recipe ID not found. ID: " + recipeId);
		}
		Recipe recipe = recipeOptional.get();
		Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream().filter(ingredient -> ingredient.getId().equals(ingredientId)).map(ingredient -> ingredientToIngredientCommandConverter.convert(ingredient)).findFirst();
		if (!ingredientCommandOptional.isPresent()) {
			// todo impl error handling
			log.error("Ingredient ID not found: " + ingredientId);
		}
		return ingredientCommandOptional.get();
	}

}
