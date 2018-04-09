package com.javafreelancedeveloper.recipewebapp.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.javafreelancedeveloper.recipewebapp.command.RecipeCommand;
import com.javafreelancedeveloper.recipewebapp.converter.RecipeCommandToRecipeConverter;
import com.javafreelancedeveloper.recipewebapp.converter.RecipeToRecipeCommandConverter;
import com.javafreelancedeveloper.recipewebapp.domain.Recipe;
import com.javafreelancedeveloper.recipewebapp.repository.RecipeRepository;

public class RecipeServiceImplTest {

	private RecipeServiceImpl recipeServiceImpl;
	@Mock
	private RecipeRepository recipeRepository;
	@Mock
	private RecipeCommandToRecipeConverter recipeCommandToRecipeConverter;
	@Mock
	private RecipeToRecipeCommandConverter recipeToRecipeCommandConverter;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		recipeServiceImpl = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipeConverter, recipeToRecipeCommandConverter);
	}

	@Test
	public void testListRecipes() {

		RecipeCommand recipeCommand = new RecipeCommand();
		Recipe recipe = new Recipe();
		Set<Recipe> recipes = new HashSet<Recipe>();
		recipes.add(recipe);

		when(recipeRepository.findAll()).thenReturn(recipes);
		when(recipeToRecipeCommandConverter.convert(recipe)).thenReturn(recipeCommand);

		Set<RecipeCommand> result = recipeServiceImpl.listRecipes();
		assertEquals(1, result.size());
		verify(recipeRepository, times(1)).findAll();
	}

	@Test
	public void testDeleteRecipe() {
		Long recipeId = 3L;
		recipeServiceImpl.deleteRecipe(recipeId);
		verify(recipeRepository, times(1)).deleteById(recipeId);
	}

}
