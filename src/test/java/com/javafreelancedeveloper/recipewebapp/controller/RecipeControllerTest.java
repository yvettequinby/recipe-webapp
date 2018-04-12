package com.javafreelancedeveloper.recipewebapp.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import com.javafreelancedeveloper.recipewebapp.command.RecipeCommand;
import com.javafreelancedeveloper.recipewebapp.exception.NotFoundException;
import com.javafreelancedeveloper.recipewebapp.service.RecipeService;

public class RecipeControllerTest {

	private RecipeController recipeController;
	@Mock
	private RecipeService recipeService;
	@Mock
	private Model model;
	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		recipeController = new RecipeController(recipeService);
		mockMvc = MockMvcBuilders.standaloneSetup(recipeController).setControllerAdvice(new ControllerExceptionHandler()).build();
	}

	@Test
	public void testShow() throws Exception {

		// Given
		Long recipeId = 1L;
		RecipeCommand recipe = new RecipeCommand();
		recipe.setId(recipeId);

		when(recipeService.getRecipe(recipeId)).thenReturn(recipe);

		mockMvc.perform(get("/recipe/" + recipeId.toString() + "/show")).andExpect(status().isOk()).andExpect(view().name("recipe/show")).andExpect(model().attributeExists("recipe"));

		String result = recipeController.show(recipeId.toString(), model);
		assertEquals("recipe/show", result);
		verify(recipeService, times(2)).getRecipe(recipeId);

	}

	@Test
	public void testGetRecipeNotFound() throws Exception {
		when(recipeService.getRecipe(anyLong())).thenThrow(NotFoundException.class);
		mockMvc.perform(get("/recipe/1/show")).andExpect(status().isNotFound()).andExpect(view().name("404error"));
	}
	
	@Test
	public void testGetRecipeNumberFormatException() throws Exception {
		when(recipeService.getRecipe(anyLong())).thenThrow(NotFoundException.class);
		mockMvc.perform(get("/recipe/xxx/show")).andExpect(status().isBadRequest()).andExpect(view().name("400error"));
	}

	@Test
	public void testGetNewRecipeForm() throws Exception {
		mockMvc.perform(get("/recipe/new")).andExpect(status().isOk()).andExpect(view().name("recipe/recipeform")).andExpect(model().attributeExists("recipe"));
	}

	@Test
	public void testPostNewRecipeForm() throws Exception {
		RecipeCommand command = new RecipeCommand();
		command.setId(2L);
		when(recipeService.saveRecipe(any())).thenReturn(command);
		mockMvc.perform(post("/recipe").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("id", "").param("description", "some string")).andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/recipe/2/show"));
	}

	@Test
	public void testGetUpdateView() throws Exception {
		RecipeCommand command = new RecipeCommand();
		command.setId(2L);
		when(recipeService.getRecipe(anyLong())).thenReturn(command);
		mockMvc.perform(get("/recipe/1/update")).andExpect(status().isOk()).andExpect(view().name("recipe/recipeform")).andExpect(model().attributeExists("recipe"));
	}

	@Test
	public void testDeleteAction() throws Exception {
		mockMvc.perform(get("/recipe/1/delete")).andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/"));
		verify(recipeService, times(1)).deleteRecipe(anyLong());
	}

}
