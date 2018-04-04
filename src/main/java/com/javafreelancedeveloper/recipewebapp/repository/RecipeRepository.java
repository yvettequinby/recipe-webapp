package com.javafreelancedeveloper.recipewebapp.repository;

import org.springframework.data.repository.CrudRepository;

import com.javafreelancedeveloper.recipewebapp.domain.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

}
