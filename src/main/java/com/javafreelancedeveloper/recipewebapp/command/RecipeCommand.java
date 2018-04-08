package com.javafreelancedeveloper.recipewebapp.command;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.javafreelancedeveloper.recipewebapp.domain.Difficulty;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {

	private Long id;
	private String description;
	private Integer prepTime;
	private Integer cookTime;
	private Integer servings;
	private String source;
	private String url;
	private String directions;
	private Difficulty difficulty;
	private Byte[] image;
	private NotesCommand notes;
	private Set<IngredientCommand> ingredients = new HashSet<IngredientCommand>();
	private Set<CategoryCommand> categories = new HashSet<CategoryCommand>();

}
