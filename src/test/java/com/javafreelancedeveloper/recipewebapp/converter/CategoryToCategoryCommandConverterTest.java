package com.javafreelancedeveloper.recipewebapp.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import com.javafreelancedeveloper.recipewebapp.command.CategoryCommand;
import com.javafreelancedeveloper.recipewebapp.domain.Category;

public class CategoryToCategoryCommandConverterTest {

	public static final Long ID_VALUE = new Long(1L);
	public static final String DESCRIPTION = "descript";
	private CategoryToCategoryCommandConverter convter;

	@Before
	public void setUp() throws Exception {
		convter = new CategoryToCategoryCommandConverter();
	}

	@Test
	public void testNullObject() throws Exception {
		assertNull(convter.convert(null));
	}

	@Test
	public void testEmptyObject() throws Exception {
		assertNotNull(convter.convert(new Category()));
	}

	@Test
	public void convert() throws Exception {
		// given
		Category category = new Category();
		category.setId(ID_VALUE);
		category.setDescription(DESCRIPTION);

		// when
		CategoryCommand categoryCommand = convter.convert(category);

		// then
		assertEquals(ID_VALUE, categoryCommand.getId());
		assertEquals(DESCRIPTION, categoryCommand.getDescription());

	}

}