package com.jovani.recipes.controllers;

import com.jovani.recipes.commands.RecipeCommand;
import com.jovani.recipes.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("/recipe/{id}")
    public String showRecipeById(@PathVariable String id, Model model){
        log.debug("Getting show recipe page");
        model.addAttribute("recipe", this.recipeService.findById(Long.valueOf(id)));
        return "recipe/show";
    }

    @RequestMapping("/recipe/{id}/update")
    public String editRecipeById(@PathVariable String id, Model model){
        RecipeCommand recipeCommand = this.recipeService.findRecipeCommand(Long.valueOf(id));
        model.addAttribute("recipe", recipeCommand);
        return "recipe/recipeform";
    }

    @RequestMapping("/recipe/{id}/delete")
    public String deleteRecipeById(@PathVariable String id){
        log.debug("Deleting post with id = {}", id);
        this.recipeService.deleteById(Long.valueOf(id));
        return "redirect:/";
    }

    @RequestMapping("/recipe/new")
    public String createRecipe(Model model){
        model.addAttribute("recipe", new RecipeCommand());
        return "recipe/recipeform";
    }

    @PostMapping("/recipe")
    public String updateRecipe(@ModelAttribute RecipeCommand recipeCommand){
        RecipeCommand savedRecipeCommand = this.recipeService
                .saveRecipeCommand(recipeCommand);
        return "redirect:/recipe/" + savedRecipeCommand.getId();
    }

}
