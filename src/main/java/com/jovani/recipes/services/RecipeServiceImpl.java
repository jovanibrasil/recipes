package com.jovani.recipes.services;

import com.jovani.recipes.commands.RecipeCommand;
import com.jovani.recipes.converters.RecipeCommandToRecipe;
import com.jovani.recipes.converters.RecipeToRecipeCommand;
import com.jovani.recipes.domain.Recipe;
import com.jovani.recipes.exceptions.NotFoundException;
import com.jovani.recipes.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipeConverter;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeCommandToRecipe recipeCommandToRecipe,
                             RecipeToRecipeCommand recipeToRecipeCommand) {
        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipeConverter = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    public List<Recipe> getRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        recipeRepository.findAll().forEach(recipes::add);
        return recipes;
    }

    @Override
    public Recipe findById(String id) {
        Optional<Recipe> recipeOptional = this.recipeRepository.findById(id);

        if(!recipeOptional.isPresent()){
            throw new NotFoundException("Recipe not found for id: " + id);
        }
        return recipeOptional.get();
    }

    @Override
    public RecipeCommand findRecipeCommand(String id){
        return this.recipeToRecipeCommand.convert(this.findById(id));
    }

    @Override
    public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {
        Recipe detachedRecipe = this.recipeCommandToRecipeConverter.convert(recipeCommand);
        Recipe savedRecipe = this.recipeRepository.save(detachedRecipe);
        return this.recipeToRecipeCommand.convert(savedRecipe);
    }

    @Override
    public void deleteById(String id) {
        this.recipeRepository.deleteById(id);
    }

    @Override
    public RecipeCommand findIngredientCommandById(String id) {
        Recipe recipe = this.findById(id);
        RecipeCommand recipeCommand = this.recipeToRecipeCommand.convert(recipe);
        return recipeCommand;
    }
}
