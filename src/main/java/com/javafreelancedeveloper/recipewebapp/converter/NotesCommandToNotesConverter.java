package com.javafreelancedeveloper.recipewebapp.converter;

import lombok.Synchronized;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.javafreelancedeveloper.recipewebapp.command.NotesCommand;
import com.javafreelancedeveloper.recipewebapp.domain.Notes;

@Component
public class NotesCommandToNotesConverter implements Converter<NotesCommand, Notes> {

	@Synchronized
	@Nullable
	@Override
	public Notes convert(NotesCommand source) {
		if(source == null) {
            return null;
        }

        final Notes notes = new Notes();
        notes.setId(source.getId());
        notes.setRecipeNotes(source.getRecipeNotes());
        return notes;
	}

}
