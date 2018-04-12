package com.javafreelancedeveloper.recipewebapp.service.impl;

import java.io.IOException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.javafreelancedeveloper.recipewebapp.domain.Recipe;
import com.javafreelancedeveloper.recipewebapp.repository.RecipeRepository;
import com.javafreelancedeveloper.recipewebapp.service.ImageService;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

	private final RecipeRepository recipeRepository;

	@Autowired
	public ImageServiceImpl(RecipeRepository recipeService) {
		this.recipeRepository = recipeService;
	}

	@Override
	@Transactional
	public void saveImageFile(Long recipeId, MultipartFile file) {
		log.debug("Received a file");
		try {
			Recipe recipe = recipeRepository.findById(recipeId).get();
			Byte[] byteObjects = new Byte[file.getBytes().length];
			int i = 0;
			for (byte b : file.getBytes()) {
				byteObjects[i++] = b;
			}
			recipe.setImage(byteObjects);
			recipeRepository.save(recipe);
		} catch (IOException e) {
			// TODO handle better
			log.error("Error occurred", e);
			e.printStackTrace();
		}
	}

}
