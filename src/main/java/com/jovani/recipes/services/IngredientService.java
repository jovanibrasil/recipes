package com.jovani.recipes.services;

import com.jovani.recipes.commands.IngredientCommand;

public interface IngredientService {
    IngredientCommand findById(String ingredientId);
    void deleteById(String ingredientId);
    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);
}
