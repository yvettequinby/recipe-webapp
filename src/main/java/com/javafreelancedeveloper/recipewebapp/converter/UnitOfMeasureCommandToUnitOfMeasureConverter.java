package com.javafreelancedeveloper.recipewebapp.converter;

import lombok.Synchronized;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.javafreelancedeveloper.recipewebapp.command.UnitOfMeasureCommand;
import com.javafreelancedeveloper.recipewebapp.domain.UnitOfMeasure;

@Component
public class UnitOfMeasureCommandToUnitOfMeasureConverter implements Converter<UnitOfMeasureCommand, UnitOfMeasure> {

	@Synchronized
	@Nullable
	@Override
	public UnitOfMeasure convert(UnitOfMeasureCommand source) {
		UnitOfMeasure result = null;
		if (source != null) {
			result = new UnitOfMeasure();
			result.setDescription(source.getDescription());
			result.setId(source.getId());

		}
		return result;
	}

}
