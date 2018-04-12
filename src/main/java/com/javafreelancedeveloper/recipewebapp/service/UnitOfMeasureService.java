package com.javafreelancedeveloper.recipewebapp.service;

import java.util.Set;

import com.javafreelancedeveloper.recipewebapp.command.UnitOfMeasureCommand;

public interface UnitOfMeasureService {

	Set<UnitOfMeasureCommand> listUnitOfMeasures();

}
