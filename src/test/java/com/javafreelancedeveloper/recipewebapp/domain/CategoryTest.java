package com.javafreelancedeveloper.recipewebapp.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class CategoryTest {

	private Category category;

	@Before
	public void setUp() throws Exception {
		category = new Category();
	}

	@Test
	public void testGetId() {
		Long idValue = 4L;
		category.setId(idValue);
		assertEquals(idValue, category.getId());
	}

	@Test
	public void testGetDescription() {
		String descriptionValue = "hello";
		category.setDescription(descriptionValue);
		assertEquals(descriptionValue, category.getDescription());
	}

	@Test
	public void testGetRecipes() {
		assertEquals(0, category.getRecipes().size());
	}

}
