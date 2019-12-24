package com.jovani.recipes.services;

import com.jovani.recipes.commands.IngredientCommand;

public interface IngredientService {
    IngredientCommand findById(Long ingredientId);
    void deleteById(Long ingredientId);
    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);
}
