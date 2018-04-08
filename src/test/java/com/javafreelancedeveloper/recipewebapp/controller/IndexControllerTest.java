package com.javafreelancedeveloper.recipewebapp.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import com.javafreelancedeveloper.recipewebapp.domain.Recipe;
import com.javafreelancedeveloper.recipewebapp.service.RecipeService;

public class IndexControllerTest {

	private IndexController indexController;
	@Mock
	private RecipeService recipeService;
	@Mock
	private Model model;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		indexController = new IndexController(recipeService);
	}

	@Test
	public void testMockMvc() throws Exception {
		MockMvc mockmvc = MockMvcBuilders.standaloneSetup(indexController).build();
		mockmvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("index"));
	}

	@Test
	public void testDisplayIndexPage() {

		// Given
		Recipe recipe1 = new Recipe();
		recipe1.setId(1L);
		Recipe recipe2 = new Recipe();
		recipe2.setId(2L);
		Set<Recipe> recipes = new HashSet<Recipe>();
		recipes.add(recipe1);
		recipes.add(recipe2);

		when(recipeService.listRecipes()).thenReturn(recipes);

		ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

		String result = indexController.displayIndexPage(model);
		assertEquals("index", result);
		verify(recipeService, times(1)).listRecipes();
		verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());

		Set<Recipe> recipeCaptured = argumentCaptor.getValue();
		assertEquals(recipes, recipeCaptured);
	}

}
