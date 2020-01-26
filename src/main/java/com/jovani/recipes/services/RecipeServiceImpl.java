package com.jovani.recipes.services;

import com.jovani.recipes.commands.RecipeCommand;
import com.jovani.recipes.converters.RecipeCommandToRecipe;
import com.jovani.recipes.converters.RecipeToRecipeCommand;
import com.jovani.recipes.domain.Recipe;
import com.jovani.recipes.exceptions.NotFoundException;
import com.jovani.recipes.repositories.reactive.RecipeReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeReactiveRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipeConverter;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    @Override
    public Flux<Recipe> getRecipes() {
        return recipeRepository.findAll();
    }

    @Override
    public Mono<Recipe> findById(String id) {
        return this.recipeRepository.findById(id).switchIfEmpty(Mono.error(new NotFoundException()));
    }

    @Override
    public Mono<RecipeCommand> findRecipeCommand(String id){
        return this.findById(id).map(recipeToRecipeCommand::convert);
    }

    @Override
    public Mono<RecipeCommand> saveRecipeCommand(RecipeCommand recipeCommand) {
        Recipe detachedRecipe = this.recipeCommandToRecipeConverter.convert(recipeCommand);
        return this.recipeRepository.save(detachedRecipe).map(recipeToRecipeCommand::convert);
    }

    @Override
    public Mono<Void> deleteById(String id) {
        this.recipeRepository.deleteById(id);
        return Mono.empty();
    }

    @Override
    public Mono<RecipeCommand> findIngredientCommandById(String id) {
        return this.findById(id).map(recipeToRecipeCommand::convert);
    }
}
