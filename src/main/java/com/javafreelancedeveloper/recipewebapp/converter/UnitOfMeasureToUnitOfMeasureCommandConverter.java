package com.javafreelancedeveloper.recipewebapp.converter;

import lombok.Synchronized;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.javafreelancedeveloper.recipewebapp.command.UnitOfMeasureCommand;
import com.javafreelancedeveloper.recipewebapp.domain.UnitOfMeasure;

@Component
public class UnitOfMeasureToUnitOfMeasureCommandConverter implements Converter<UnitOfMeasure, UnitOfMeasureCommand> {

	@Synchronized
	@Nullable
	@Override
	public UnitOfMeasureCommand convert(UnitOfMeasure unitOfMeasure) {

		if (unitOfMeasure != null) {
			final UnitOfMeasureCommand uomc = new UnitOfMeasureCommand();
			uomc.setId(unitOfMeasure.getId());
			uomc.setDescription(unitOfMeasure.getDescription());
			return uomc;
		}
		return null;
	}
}
