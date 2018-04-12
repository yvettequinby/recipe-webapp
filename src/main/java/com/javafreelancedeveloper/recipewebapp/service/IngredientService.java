package com.javafreelancedeveloper.recipewebapp.service;

import com.javafreelancedeveloper.recipewebapp.command.IngredientCommand;

public interface IngredientService {

	IngredientCommand findIngredient(Long recipeId, Long ingredientId);

	IngredientCommand saveIngredient(IngredientCommand command);

	void deleteIngredient(Long recipeId, Long ingredientId);

}
