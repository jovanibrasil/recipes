package com.jovani.recipes.services;

import com.jovani.recipes.domain.Recipe;
import com.jovani.recipes.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public void saveImageFile(String id, MultipartFile image) {

        try {
            Optional<Recipe> optionalRecipe = this.recipeRepository.findById(id);

            if(!optionalRecipe.isPresent()){
                throw  new RuntimeException("Recipe not found");
            }

            Byte[] byteObject = new Byte[image.getBytes().length];

            int i = 0;
            for (byte b : image.getBytes()) byteObject[i++] = b;

            Recipe recipe = optionalRecipe.get();
            recipe.setImage(byteObject);
            recipeRepository.save(recipe);

        } catch (Exception e) {
            log.error("An error occurred.");
        }

    }

}
