package com.javafreelancedeveloper.recipewebapp.converter;

import lombok.Synchronized;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.javafreelancedeveloper.recipewebapp.command.RecipeCommand;
import com.javafreelancedeveloper.recipewebapp.domain.Recipe;

@Component
public class RecipeCommandToRecipeConverter implements Converter<RecipeCommand, Recipe> {

	private CategoryCommandToCategoryConverter categoryConverter;
	private IngredientCommandToIngredientConverter ingredientConverter;
	private NotesCommandToNotesConverter notesConverter;

	@Autowired
	public RecipeCommandToRecipeConverter(CategoryCommandToCategoryConverter categoryConverter, IngredientCommandToIngredientConverter ingredientConverter, NotesCommandToNotesConverter notesConverter) {
		this.categoryConverter = categoryConverter;
		this.ingredientConverter = ingredientConverter;
		this.notesConverter = notesConverter;
	}

	@Synchronized
	@Nullable
	@Override
	public Recipe convert(RecipeCommand source) {
		if (source == null) {
			return null;
		} else {
			final Recipe result = new Recipe();
			if(source.getCategories()!=null && source.getCategories().size()>0) {
				source.getCategories().forEach(category -> result.getCategories().add(categoryConverter.convert(category)));
			}
			
			result.setCookTime(source.getCookTime());
			result.setDescription(source.getDescription());
			result.setDifficulty(source.getDifficulty());
			result.setDirections(source.getDirections());
			result.setId(source.getId());
			result.setImage(source.getImage());
			if(source.getIngredients()!=null && source.getIngredients().size()>0) {
				source.getIngredients().forEach(ingredient -> result.getIngredients().add(ingredientConverter.convert(ingredient)));
			}
			result.setNotes(notesConverter.convert(source.getNotes()));
			result.setPrepTime(source.getPrepTime());
			result.setServings(source.getServings());
			result.setSource(source.getSource());
			result.setUrl(source.getUrl());
			return result;
		}

	}

}
