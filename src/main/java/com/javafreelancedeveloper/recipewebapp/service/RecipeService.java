package com.javafreelancedeveloper.recipewebapp.service;

import java.util.Set;

import com.javafreelancedeveloper.recipewebapp.domain.Recipe;

public interface RecipeService {

	Set<Recipe> listRecipes();

	Recipe findById(Long id);

}
