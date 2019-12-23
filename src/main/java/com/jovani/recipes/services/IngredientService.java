package com.jovani.recipes.services;

import com.jovani.recipes.commands.IngredientCommand;

public interface IngredientService {
    public IngredientCommand findByRecipeIdAndIngredientID(Long recipeId, Long ingredientId);
}