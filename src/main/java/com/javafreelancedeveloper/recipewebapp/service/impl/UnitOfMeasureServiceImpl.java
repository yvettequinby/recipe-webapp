package com.javafreelancedeveloper.recipewebapp.service.impl;

import java.util.HashSet;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javafreelancedeveloper.recipewebapp.command.UnitOfMeasureCommand;
import com.javafreelancedeveloper.recipewebapp.converter.UnitOfMeasureToUnitOfMeasureCommandConverter;
import com.javafreelancedeveloper.recipewebapp.domain.UnitOfMeasure;
import com.javafreelancedeveloper.recipewebapp.repository.UnitOfMeasureRepository;
import com.javafreelancedeveloper.recipewebapp.service.UnitOfMeasureService;

@Slf4j
@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

	private UnitOfMeasureRepository unitOfMeasureRepository;
	private UnitOfMeasureToUnitOfMeasureCommandConverter unitOfMeasureToUnitOfMeasureCommandConverter;

	@Autowired
	public UnitOfMeasureServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository, UnitOfMeasureToUnitOfMeasureCommandConverter unitOfMeasureToUnitOfMeasureCommandConverter) {
		this.unitOfMeasureRepository = unitOfMeasureRepository;
		this.unitOfMeasureToUnitOfMeasureCommandConverter = unitOfMeasureToUnitOfMeasureCommandConverter;
	}

	@Override
	@Transactional
	public Set<UnitOfMeasureCommand> listUnitOfMeasures() {
		log.debug("Requesting uom list...");
		Set<UnitOfMeasureCommand> uomCommands = new HashSet<UnitOfMeasureCommand>();
		Iterable<UnitOfMeasure> uoms = unitOfMeasureRepository.findAll();
		uoms.forEach(uom -> uomCommands.add(unitOfMeasureToUnitOfMeasureCommandConverter.convert(uom)));
		return uomCommands;
	}
}
