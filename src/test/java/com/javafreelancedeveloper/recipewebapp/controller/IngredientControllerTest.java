package com.javafreelancedeveloper.recipewebapp.controller;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.javafreelancedeveloper.recipewebapp.command.IngredientCommand;
import com.javafreelancedeveloper.recipewebapp.command.RecipeCommand;
import com.javafreelancedeveloper.recipewebapp.service.IngredientService;
import com.javafreelancedeveloper.recipewebapp.service.RecipeService;

public class IngredientControllerTest {

	private IngredientController ingredientController;
	@Mock
	private RecipeService recipeService;
	@Mock
	private IngredientService ingredientService;
	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		ingredientController = new IngredientController(recipeService, ingredientService);
		mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
	}

	@Test
	public void testListIngredients() throws Exception {
		// Given
		Long recipeId = 1L;
		RecipeCommand recipe = new RecipeCommand();
		recipe.setId(recipeId);
		when(recipeService.getRecipe(recipeId)).thenReturn(recipe);

		// when
		mockMvc.perform(get("/recipe/" + recipeId.toString() + "/ingredients")).andExpect(status().isOk()).andExpect(view().name("recipe/ingredient/list")).andExpect(model().attributeExists("recipe"));

		// then
		verify(recipeService, times(1)).getRecipe(recipeId);
	}

	@Test
	public void testShowIngredient() throws Exception {
		// given
		IngredientCommand ingredientCommand = new IngredientCommand();

		// when
		when(ingredientService.findIngredient(anyLong(), anyLong())).thenReturn(ingredientCommand);

		// then
		mockMvc.perform(get("/recipe/1/ingredient/2/show")).andExpect(status().isOk()).andExpect(view().name("recipe/ingredient/show")).andExpect(model().attributeExists("ingredient"));
	}

}
