package com.jovani.recipes.services;

import com.jovani.recipes.commands.IngredientCommand;
import com.jovani.recipes.converters.IngredientToIngredientCommand;
import com.jovani.recipes.domain.Recipe;
import com.jovani.recipes.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IngredientServiceImpl implements  IngredientService {

    private IngredientToIngredientCommand ingredientToIngredientCommand;
    private RecipeRepository recipeRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand, RecipeRepository recipeRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.recipeRepository = recipeRepository;
    }

    @Override
    public IngredientCommand findByRecipeIdAndIngredientID(Long recipeId, Long ingredientId) {

        Optional<Recipe> recipeOptional = this.recipeRepository.findById(recipeId);

        if(!recipeOptional.isPresent()){
            throw new RuntimeException("Recipe not found");
        }

        Optional<IngredientCommand> ingredientCommand = recipeOptional.get()
                .getIngredients()
                .stream()
                .filter(i -> i.getId().equals(ingredientId))
                .map(i -> ingredientToIngredientCommand.convert(i))
                .findFirst();

        if(!ingredientCommand.isPresent()){
            throw  new RuntimeException("Ingredient not found");
        }

        return ingredientCommand.get();
    }
}
