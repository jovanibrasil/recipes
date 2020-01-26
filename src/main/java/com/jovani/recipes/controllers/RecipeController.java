package com.jovani.recipes.controllers;

import com.jovani.recipes.commands.RecipeCommand;
import com.jovani.recipes.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Slf4j
@Controller
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{id}")
    public String showRecipeById(@PathVariable String id, Model model){
        log.debug("Getting show recipe page");
        model.addAttribute("recipe", this.recipeService.findById(id).block());
        return "recipe/show";
    }

    @GetMapping("/recipe/{id}/update")
    public String editRecipeById(@PathVariable String id, Model model){
        model.addAttribute("recipe", this.recipeService.findRecipeCommand(id).block());
        return "recipe/recipeform";
    }

    @GetMapping("/recipe/{id}/delete")
    public String deleteRecipeById(@PathVariable String id){
        log.debug("Deleting post with id = {}", id);
        this.recipeService.deleteById(id);
        return "redirect:/";
    }

    @GetMapping("/recipe/new")
    public String createRecipe(Model model){
        model.addAttribute("recipe", new RecipeCommand());
        return "recipe/recipeform";
    }

    @PostMapping("/recipe")
    public String updateRecipe(@Valid @ModelAttribute("recipe") RecipeCommand recipeCommand, BindingResult bindingResult){

        if(bindingResult.hasErrors()) return "recipe/recipeform";

        RecipeCommand savedRecipeCommand = this.recipeService
                .saveRecipeCommand(recipeCommand).block();
        return "redirect:/recipe/" + savedRecipeCommand.getId();
    }

}
