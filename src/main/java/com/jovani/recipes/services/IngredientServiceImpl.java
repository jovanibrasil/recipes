package com.jovani.recipes.services;

import com.jovani.recipes.commands.IngredientCommand;
import com.jovani.recipes.converters.IngredientCommandToIngredient;
import com.jovani.recipes.converters.IngredientToIngredientCommand;
import com.jovani.recipes.domain.Ingredient;
import com.jovani.recipes.repositories.IngredientRepository;
import com.jovani.recipes.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IngredientServiceImpl implements  IngredientService {

    private IngredientToIngredientCommand ingredientToIngredientCommand;
    private IngredientRepository ingredientRepository;
    private IngredientCommandToIngredient ingredientCommandToIngredient;
    private RecipeRepository recipeRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand,
                                 IngredientRepository ingredientRepository,
                                 IngredientCommandToIngredient ingredientCommandToIngredient,
                                 RecipeRepository recipeRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
    }

    @Override
    public IngredientCommand findById(String ingredientId) {
        Optional<Ingredient> optionalIngredient = this.ingredientRepository.findById(ingredientId);
        if(!optionalIngredient.isPresent()) throw new RuntimeException("Ingredient not found");
        return ingredientToIngredientCommand.convert(optionalIngredient.get());
    }

    @Override
    public void deleteById(String ingredientId) {
        Optional<Ingredient> optionalIngredient = this.ingredientRepository.findById(ingredientId);
        if(!optionalIngredient.isPresent()) throw new RuntimeException("Ingredient not found");
        this.ingredientRepository.delete(optionalIngredient.get());
    }

    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {
        Ingredient ingredient = this.ingredientCommandToIngredient.convert(ingredientCommand);
        Ingredient savedIngredient = this.ingredientRepository.save(ingredient);
        return this.ingredientToIngredientCommand.convert(savedIngredient);
    }

}
