package com.javafreelancedeveloper.recipewebapp.service.impl;

import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.javafreelancedeveloper.recipewebapp.command.IngredientCommand;
import com.javafreelancedeveloper.recipewebapp.converter.IngredientCommandToIngredientConverter;
import com.javafreelancedeveloper.recipewebapp.converter.IngredientToIngredientCommandConverter;
import com.javafreelancedeveloper.recipewebapp.domain.Ingredient;
import com.javafreelancedeveloper.recipewebapp.domain.Recipe;
import com.javafreelancedeveloper.recipewebapp.repository.RecipeRepository;
import com.javafreelancedeveloper.recipewebapp.repository.UnitOfMeasureRepository;
import com.javafreelancedeveloper.recipewebapp.service.IngredientService;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

	private final IngredientCommandToIngredientConverter ingredientCommandToIngredientConverter;
	private final IngredientToIngredientCommandConverter ingredientToIngredientCommandConverter;
	private final RecipeRepository recipeRepository;
	private final UnitOfMeasureRepository unitOfMeasureRepository;

	@Autowired
	public IngredientServiceImpl(IngredientToIngredientCommandConverter ingredientToIngredientCommandConverter, RecipeRepository recipeRepository, IngredientCommandToIngredientConverter ingredientCommandToIngredientConverter, UnitOfMeasureRepository unitOfMeasureRepository) {
		this.ingredientToIngredientCommandConverter = ingredientToIngredientCommandConverter;
		this.recipeRepository = recipeRepository;
		this.ingredientCommandToIngredientConverter = ingredientCommandToIngredientConverter;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
	}

	@Override
	@Transactional
	public void deleteIngredient(Long recipeId, Long ingredientId) {
		log.debug("Deleting ingredient: " + recipeId + ":" + ingredientId);

		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

		if (recipeOptional.isPresent()) {
			Recipe recipe = recipeOptional.get();
			log.debug("found recipe");

			Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream().filter(ingredient -> ingredient.getId().equals(ingredientId)).findFirst();

			if (ingredientOptional.isPresent()) {
				log.debug("found Ingredient");
				Ingredient ingredientToDelete = ingredientOptional.get();
				ingredientToDelete.setRecipe(null);
				recipe.getIngredients().remove(ingredientOptional.get());
				recipeRepository.save(recipe);
			}
		} else {
			log.debug("Recipe Id Not found. Id:" + recipeId);
		}
	}

	@Override
	@Transactional
	public IngredientCommand findIngredient(Long recipeId, Long ingredientId) {

		Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);
		if (!recipeOptional.isPresent()) {
			// todo impl error handling
			log.error("Recipe ID not found. ID: " + recipeId);
		}
		Recipe recipe = recipeOptional.get();
		Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream().filter(ingredient -> ingredient.getId().equals(ingredientId)).map(ingredient -> ingredientToIngredientCommandConverter.convert(ingredient)).findFirst();
		if (!ingredientCommandOptional.isPresent()) {
			// todo impl error handling
			log.error("Ingredient ID not found: " + ingredientId);
		}
		return ingredientCommandOptional.get();
	}

	@Override
	@Transactional
	public IngredientCommand saveIngredient(IngredientCommand ingredientCommand) {
		// Retrieve the recipe
		Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());
		if (!recipeOptional.isPresent()) {
			// TODO toss error if not found!
			log.error("Recipe not found for id: " + ingredientCommand.getRecipeId());
			return new IngredientCommand();
		} else {
			Recipe recipe = recipeOptional.get();
			// Look for existing ingredient recipe
			Optional<Ingredient> ingredientOptional = recipe.getIngredients().stream().filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId())).findFirst();
			if (ingredientOptional.isPresent()) {
				// Update existing ingredient record
				Ingredient ingredientFound = ingredientOptional.get();
				ingredientFound.setDescription(ingredientCommand.getDescription());
				ingredientFound.setAmount(ingredientCommand.getAmount());
				ingredientFound.setUom(unitOfMeasureRepository.findById(ingredientCommand.getUom().getId()).orElseThrow(() -> new RuntimeException("UOM NOT FOUND"))); // todo address this
			} else {
				// add new ingredient record
				recipe.addIngredient(ingredientCommandToIngredientConverter.convert(ingredientCommand));
			}
			// Save the recipe
			Recipe savedRecipe = recipeRepository.save(recipe);
			Ingredient savedIngredient = null;
			if (ingredientCommand.getId() == null) {
				// not totally safe... But best guess
				Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream().filter(recipeIngredients -> recipeIngredients.getDescription().equals(ingredientCommand.getDescription())).filter(recipeIngredients -> recipeIngredients.getAmount().equals(ingredientCommand.getAmount())).filter(recipeIngredients -> recipeIngredients.getUom().getId().equals(ingredientCommand.getUom().getId())).findFirst();
				savedIngredient = savedIngredientOptional.get();
			} else {
				Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream().filter(recipeIngredients -> recipeIngredients.getId().equals(ingredientCommand.getId())).findFirst();
				savedIngredient = savedIngredientOptional.get();
			}
			IngredientCommand result = ingredientToIngredientCommandConverter.convert(savedIngredient);
			return result;
		}

	}

}
