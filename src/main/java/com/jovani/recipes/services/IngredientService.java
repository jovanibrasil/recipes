package com.jovani.recipes.services;

import com.jovani.recipes.commands.IngredientCommand;
import reactor.core.publisher.Mono;

public interface IngredientService {
    Mono<IngredientCommand> findById(String ingredientId);
    Mono<Void> deleteById(String ingredientId);
    Mono<IngredientCommand> saveIngredientCommand(IngredientCommand ingredientCommand);
}
