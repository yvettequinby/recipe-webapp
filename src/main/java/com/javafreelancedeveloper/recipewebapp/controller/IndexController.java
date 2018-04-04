package com.javafreelancedeveloper.recipewebapp.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.javafreelancedeveloper.recipewebapp.domain.Category;
import com.javafreelancedeveloper.recipewebapp.domain.UnitOfMeasure;
import com.javafreelancedeveloper.recipewebapp.repository.CategoryRepository;
import com.javafreelancedeveloper.recipewebapp.repository.UnitOfMeasureRepository;

@Controller
public class IndexController {
	
	private CategoryRepository categoryRepository;
	private UnitOfMeasureRepository unitOfMeasureRepository;
	
	@Autowired
	public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
		super();
		this.categoryRepository = categoryRepository;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
	}

	@RequestMapping({"", "/", "/index"})
	public String displayIndexPage() {
		Optional<Category> category = categoryRepository.findByDescription("Vegan");
		Optional<UnitOfMeasure> unitOfMeasure = unitOfMeasureRepository.findByDescription("Cup");
		System.out.println("Category ID: " + category.get().getId());
		System.out.println("UnitOfMeasure ID: " + unitOfMeasure.get().getId());
		return "index";
	}

}
