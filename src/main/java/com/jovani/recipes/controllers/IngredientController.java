package com.jovani.recipes.controllers;

import com.jovani.recipes.commands.IngredientCommand;
import com.jovani.recipes.commands.RecipeCommand;
import com.jovani.recipes.commands.UnitOfMeasureCommand;
import com.jovani.recipes.services.IngredientService;
import com.jovani.recipes.services.RecipeService;
import com.jovani.recipes.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("/recipe/{id}/ingredients")
    public String listIngredients(@PathVariable String id, Model model){
        log.debug("Getting ingredient list for recipe id: " + id);
        model.addAttribute("recipe",
                this.recipeService.findIngredientCommandById(id));
        return "recipe/ingredients/list";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/show")
    public String viewIngredient(@PathVariable String recipeId,
                                 @PathVariable String ingredientId, Model model){
        log.debug("Getting ingredient {}", ingredientId);
        model.addAttribute("ingredient",
                this.ingredientService.findById(ingredientId));
        return "recipe/ingredients/viewingredient";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/new")
    public String newIngredient(@PathVariable String recipeId, Model model){
        log.debug("Creating new ingredient for recipe wit id={}", recipeId);

        RecipeCommand recipeCommand = this.recipeService.findRecipeCommand(recipeId);

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(recipeId);
        model.addAttribute("ingredient", ingredientCommand);

        // init unit of measure
        ingredientCommand.setUom(new UnitOfMeasureCommand());
        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());

        return "recipe/ingredients/ingredientform";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateIngredient(@PathVariable String recipeId,
                                   @PathVariable String ingredientId, Model model){
        log.debug("Updating ingredient {}", ingredientId);
        model.addAttribute("ingredient",
                this.ingredientService.findById(ingredientId));
        model.addAttribute("uomList", this.unitOfMeasureService.listAllUoms());
        return "recipe/ingredients/ingredientform";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/delete")
    public String deleteIngredient(@PathVariable String recipeId,
                                   @PathVariable String ingredientId, Model model){
        log.debug("Deleting ingredient {}", ingredientId);
        this.ingredientService.deleteById(ingredientId);
        return "redirect:/recipe/" + recipeId + "/ingredients";
    }

    @PostMapping("/recipe/{recipeId}/ingredient")
    public String saveOrUpdate(@ModelAttribute IngredientCommand ingredientCommand){
        log.debug("Saving or updating ingredient for recipe id={}", ingredientCommand.getRecipeId());

        IngredientCommand savedIngredientCommand = this.ingredientService
                            .saveIngredientCommand(ingredientCommand);

        log.debug("saved recipe id:" + savedIngredientCommand.getRecipeId());
        log.debug("saved ingredient id:" + savedIngredientCommand.getId());

        return "redirect:/recipe/" + savedIngredientCommand.getRecipeId() +
                "/ingredient/" + savedIngredientCommand.getId() + "/show";
    }

}
