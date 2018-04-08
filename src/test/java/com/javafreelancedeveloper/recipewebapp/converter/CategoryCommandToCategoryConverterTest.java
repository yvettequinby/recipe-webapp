package com.javafreelancedeveloper.recipewebapp.converter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import com.javafreelancedeveloper.recipewebapp.command.CategoryCommand;
import com.javafreelancedeveloper.recipewebapp.domain.Category;

public class CategoryCommandToCategoryConverterTest {

	public static final Long ID_VALUE = new Long(1L);
	public static final String DESCRIPTION = "description";
	private CategoryCommandToCategoryConverter converter;

	@Before
	public void setUp() throws Exception {
		converter = new CategoryCommandToCategoryConverter();
	}

	@Test
	public void testNullObject() throws Exception {
		assertNull(converter.convert(null));
	}

	@Test
	public void testEmptyObject() throws Exception {
		assertNotNull(converter.convert(new CategoryCommand()));
	}

	@Test
	public void convert() throws Exception {
		// given
		CategoryCommand categoryCommand = new CategoryCommand();
		categoryCommand.setId(ID_VALUE);
		categoryCommand.setDescription(DESCRIPTION);

		// when
		Category category = converter.convert(categoryCommand);

		// then
		assertEquals(ID_VALUE, category.getId());
		assertEquals(DESCRIPTION, category.getDescription());
	}

}