package com.javafreelancedeveloper.recipewebapp.repository;

import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.javafreelancedeveloper.recipewebapp.domain.UnitOfMeasure;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UnitOfMeasureRepositoryIT {
	
	@Autowired
	private UnitOfMeasureRepository unitOfMeasureRepository;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testFindByDescription() {
		String tsp = "Teaspoon";
		Optional<UnitOfMeasure> uom = unitOfMeasureRepository.findByDescription("Teaspoon");
		assertEquals(tsp, uom.get().getDescription());
	}

}
