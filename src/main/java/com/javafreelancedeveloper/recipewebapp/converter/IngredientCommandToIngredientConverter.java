package com.javafreelancedeveloper.recipewebapp.converter;

import lombok.Synchronized;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.javafreelancedeveloper.recipewebapp.command.IngredientCommand;
import com.javafreelancedeveloper.recipewebapp.domain.Ingredient;

@Component
public class IngredientCommandToIngredientConverter implements Converter<IngredientCommand, Ingredient> {
	
	private UnitOfMeasureCommandToUnitOfMeasureConverter unitOfMeasureConverter;
	
	public IngredientCommandToIngredientConverter(UnitOfMeasureCommandToUnitOfMeasureConverter unitOfMeasureConverter) {
		this.unitOfMeasureConverter = unitOfMeasureConverter;
	}

	@Synchronized
	@Nullable
	@Override
	public Ingredient convert(IngredientCommand source) {
		if (source == null) {
            return null;
        }

        final Ingredient ingredient = new Ingredient();
        ingredient.setId(source.getId());
        ingredient.setAmount(source.getAmount());
        ingredient.setDescription(source.getDescription());
        ingredient.setUom(unitOfMeasureConverter.convert(source.getUom()));
        return ingredient;
	}

}
