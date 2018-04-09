package com.javafreelancedeveloper.recipewebapp.converter;

import lombok.Synchronized;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.javafreelancedeveloper.recipewebapp.command.IngredientCommand;
import com.javafreelancedeveloper.recipewebapp.domain.Ingredient;

@Component
public class IngredientToIngredientCommandConverter implements Converter<Ingredient, IngredientCommand> {

	private final UnitOfMeasureToUnitOfMeasureCommandConverter uomConverter;

	public IngredientToIngredientCommandConverter(UnitOfMeasureToUnitOfMeasureCommandConverter uomConverter) {
		this.uomConverter = uomConverter;
	}

	@Synchronized
	@Nullable
	@Override
	public IngredientCommand convert(Ingredient ingredient) {
		if (ingredient == null) {
			return null;
		}

		IngredientCommand ingredientCommand = new IngredientCommand();
		ingredientCommand.setId(ingredient.getId());
		ingredientCommand.setAmount(ingredient.getAmount());
		ingredientCommand.setDescription(ingredient.getDescription());
		ingredientCommand.setUom(uomConverter.convert(ingredient.getUom()));
		if(ingredient.getRecipe()!=null) {
			ingredientCommand.setRecipeId(ingredient.getRecipe().getId());
		}
		return ingredientCommand;
	}
}
