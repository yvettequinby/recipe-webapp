package com.javafreelancedeveloper.recipewebapp.service.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.javafreelancedeveloper.recipewebapp.command.UnitOfMeasureCommand;
import com.javafreelancedeveloper.recipewebapp.converter.UnitOfMeasureToUnitOfMeasureCommandConverter;
import com.javafreelancedeveloper.recipewebapp.domain.UnitOfMeasure;
import com.javafreelancedeveloper.recipewebapp.repository.UnitOfMeasureRepository;
import com.javafreelancedeveloper.recipewebapp.service.UnitOfMeasureService;

public class UnitOfMeasureServiceImplTest {

	private UnitOfMeasureToUnitOfMeasureCommandConverter unitOfMeasureToUnitOfMeasureCommandConverter = new UnitOfMeasureToUnitOfMeasureCommandConverter();
	private UnitOfMeasureService service;

	@Mock
	private UnitOfMeasureRepository unitOfMeasureRepository;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		service = new UnitOfMeasureServiceImpl(unitOfMeasureRepository, unitOfMeasureToUnitOfMeasureCommandConverter);
	}

	@Test
	public void listUnitOfMeasures() throws Exception {
		// given
		Set<UnitOfMeasure> unitOfMeasures = new HashSet<>();
		UnitOfMeasure uom1 = new UnitOfMeasure();
		uom1.setId(1L);
		unitOfMeasures.add(uom1);

		UnitOfMeasure uom2 = new UnitOfMeasure();
		uom2.setId(2L);
		unitOfMeasures.add(uom2);

		when(unitOfMeasureRepository.findAll()).thenReturn(unitOfMeasures);

		// when
		Set<UnitOfMeasureCommand> commands = service.listUnitOfMeasures();

		// then
		assertEquals(2, commands.size());
		verify(unitOfMeasureRepository, times(1)).findAll();
	}

}
