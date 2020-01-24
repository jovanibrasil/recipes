package com.jovani.recipes.services;

import com.jovani.recipes.commands.RecipeCommand;
import com.jovani.recipes.domain.Recipe;

import java.util.List;

public interface RecipeService {
    List<Recipe> getRecipes();
    Recipe findById(String id);
    void deleteById(String id);
    RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);
    public RecipeCommand findRecipeCommand(String id);
    RecipeCommand findIngredientCommandById(String anyLong);
}
