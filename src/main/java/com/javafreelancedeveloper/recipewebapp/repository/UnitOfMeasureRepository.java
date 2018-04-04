package com.javafreelancedeveloper.recipewebapp.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.javafreelancedeveloper.recipewebapp.domain.UnitOfMeasure;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {

	Optional<UnitOfMeasure> findByDescription(String description);
	
}
