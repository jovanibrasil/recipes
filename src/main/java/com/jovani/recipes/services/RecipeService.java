package com.jovani.recipes.services;

import com.jovani.recipes.commands.RecipeCommand;
import com.jovani.recipes.domain.Recipe;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface RecipeService {
    Flux<Recipe> getRecipes();
    Mono<Recipe> findById(String id);
    Mono<Void> deleteById(String id);
    Mono<RecipeCommand> saveRecipeCommand(RecipeCommand recipeCommand);
    Mono<RecipeCommand> findRecipeCommand(String id);
    Mono<RecipeCommand> findIngredientCommandById(String anyLong);
}
