package com.javafreelancedeveloper.recipewebapp.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NotesCommand {
	
	private Long id;
	private RecipeCommand recipe;
	private String recipeNotes;

}
