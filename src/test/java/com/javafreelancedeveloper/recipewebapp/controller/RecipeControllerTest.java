package com.javafreelancedeveloper.recipewebapp.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import com.javafreelancedeveloper.recipewebapp.domain.Recipe;
import com.javafreelancedeveloper.recipewebapp.service.RecipeService;

public class RecipeControllerTest {

	private RecipeController recipeController;
	@Mock
	private RecipeService recipeService;
	@Mock
	private Model model;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		recipeController = new RecipeController(recipeService);
	}

	@Test
	public void testShow() throws Exception {

		// Given
		Long recipeId = 1L;
		Recipe recipe = new Recipe();
		recipe.setId(recipeId);

		MockMvc mockmvc = MockMvcBuilders.standaloneSetup(recipeController).build();

		when(recipeService.findById(recipeId)).thenReturn(recipe);

		mockmvc.perform(get("/recipe/show/" + recipeId.toString())).andExpect(status().isOk()).andExpect(view().name("recipe/show")).andExpect(model().attributeExists("recipe"));

		String result = recipeController.show(recipeId.toString(), model);
		assertEquals("recipe/show", result);
		verify(recipeService, times(2)).findById(recipeId);

	}

}
