package com.jovani.recipes.controllers;

import com.jovani.recipes.services.RecipeService;
import org.springframework.stereotype.Controller;

@Controller
public class IngredientController {

    private final RecipeService recipeService;

    public IngredientController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }



}
