package com.javafreelancedeveloper.recipewebapp.controller;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.javafreelancedeveloper.recipewebapp.command.IngredientCommand;
import com.javafreelancedeveloper.recipewebapp.command.RecipeCommand;
import com.javafreelancedeveloper.recipewebapp.service.IngredientService;
import com.javafreelancedeveloper.recipewebapp.service.RecipeService;
import com.javafreelancedeveloper.recipewebapp.service.UnitOfMeasureService;

public class IngredientControllerTest {

	@Mock
	private IngredientService ingredientService;
	@Mock
	private UnitOfMeasureService unitOfMeasureService;
	@Mock
	private RecipeService recipeService;
	private IngredientController controller;
	private MockMvc mockMvc;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		controller = new IngredientController(recipeService, ingredientService, unitOfMeasureService);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
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

	@Test
	public void testUpdateIngredientForm() throws Exception {
		// given
		IngredientCommand ingredientCommand = new IngredientCommand();

		// when
		when(ingredientService.findIngredient(anyLong(), anyLong())).thenReturn(ingredientCommand);
		when(unitOfMeasureService.listUnitOfMeasures()).thenReturn(new HashSet<>());

		// then
		mockMvc.perform(get("/recipe/1/ingredient/2/update")).andExpect(status().isOk()).andExpect(view().name("recipe/ingredient/ingredientform")).andExpect(model().attributeExists("ingredient")).andExpect(model().attributeExists("uomList"));
	}

	@Test
	public void testSaveOrUpdate() throws Exception {
		// given
		IngredientCommand command = new IngredientCommand();
		command.setId(3L);
		command.setRecipeId(2L);

		// when
		when(ingredientService.saveIngredient(any())).thenReturn(command);

		// then
		mockMvc.perform(post("/recipe/2/ingredient").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("id", "").param("description", "some string")).andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/recipe/2/ingredient/3/show"));

	}

	@Test
	public void testNewIngredientForm() throws Exception {
		// given
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId(1L);

		// when
		when(recipeService.getRecipe(anyLong())).thenReturn(recipeCommand);
		when(unitOfMeasureService.listUnitOfMeasures()).thenReturn(new HashSet<>());

		// then
		mockMvc.perform(get("/recipe/1/ingredient/new")).andExpect(status().isOk()).andExpect(view().name("recipe/ingredient/ingredientform")).andExpect(model().attributeExists("ingredient")).andExpect(model().attributeExists("uomList"));

		verify(recipeService, times(1)).getRecipe(anyLong());

	}

	@Test
	public void testDeleteIngredient() throws Exception {

		// then
		mockMvc.perform(get("/recipe/2/ingredient/3/delete")).andExpect(status().is3xxRedirection()).andExpect(view().name("redirect:/recipe/2/ingredients"));

		verify(ingredientService, times(1)).deleteIngredient(anyLong(), anyLong());

	}

}
