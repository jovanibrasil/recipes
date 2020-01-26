package com.jovani.recipes.services;

import com.jovani.recipes.domain.Recipe;
import com.jovani.recipes.repositories.reactive.RecipeReactiveRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeReactiveRepository recipeRepository;

    @Override
    public Mono<Void> saveImageFile(String id, MultipartFile image) {

        Mono<Recipe> recipeMono = this.recipeRepository.findById(id)
            .switchIfEmpty(Mono.error(new RuntimeException("Recipe not found")))
            .map(recipe -> {
                try {
                    Byte[] byteObject = new Byte[image.getBytes().length];
                    int i = 0;
                    for (byte b : image.getBytes()) byteObject[i++] = Byte.valueOf(b);
                    recipe.setImage(byteObject);
                    return recipe;
                } catch (Exception e){
                    log.error("An error occurred when saving the image. {}", e.getMessage());
                    throw new RuntimeException();
                }
            });

        this.recipeRepository.save(recipeMono.block()).block();
        return Mono.empty();
    }

}
