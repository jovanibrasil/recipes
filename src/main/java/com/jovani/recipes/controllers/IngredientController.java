package com.jovani.recipes.controllers;

import com.jovani.recipes.services.IngredientService;
import com.jovani.recipes.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    @GetMapping("/recipe/{id}/ingredients")
    public String listIngredients(@PathVariable String id, Model model){
        log.debug("Getting ingredient list for recipe id: " + id);
        model.addAttribute("recipe",
                recipeService.findIngredientCommandById(Long.valueOf(id)));
        return "recipe/ingredients/list";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}")
    public String viewIngredient(@PathVariable Long recipeId, @PathVariable Long ingredientId, Model model){
        log.debug("Getting ingredient {} for recipe {}", ingredientId, recipeId);
        model.addAttribute("ingredient",
                ingredientService.findByRecipeIdAndIngredientID(recipeId, ingredientId));
        return "recipe/ingredients/viewingredient";
    }


}
