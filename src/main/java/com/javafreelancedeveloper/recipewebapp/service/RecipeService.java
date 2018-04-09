package com.javafreelancedeveloper.recipewebapp.service;

import java.util.Set;

import com.javafreelancedeveloper.recipewebapp.command.RecipeCommand;

public interface RecipeService {

	Set<RecipeCommand> listRecipes();

	RecipeCommand findById(Long id);

	RecipeCommand saveRecipe(RecipeCommand recipeCommand);

}
