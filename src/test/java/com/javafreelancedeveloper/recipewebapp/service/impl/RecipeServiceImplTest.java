package com.javafreelancedeveloper.recipewebapp.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.javafreelancedeveloper.recipewebapp.domain.Recipe;
import com.javafreelancedeveloper.recipewebapp.repository.RecipeRepository;

public class RecipeServiceImplTest {

	private RecipeServiceImpl recipeServiceImpl;
	@Mock
	private RecipeRepository recipeRepository;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		recipeServiceImpl = new RecipeServiceImpl(recipeRepository);
	}

	@Test
	public void testListRecipes() {

		Recipe recipe = new Recipe();
		Set<Recipe> recipes = new HashSet<Recipe>();
		recipes.add(recipe);

		when(recipeRepository.findAll()).thenReturn(recipes);

		Set<Recipe> result = recipeServiceImpl.listRecipes();
		assertEquals(1, result.size());
		verify(recipeRepository, times(1)).findAll();
	}

}
