package com.jovani.recipes.controllers;

import com.jovani.recipes.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("/recipe/{id}")
    public String getRecipeById(@PathVariable String id, Model model){
        log.debug("Getting show recipe page");
        model.addAttribute("recipe", this.recipeService.findById(Long.valueOf(id)));
        return "recipe/show";
    }

}
