package com.jovani.recipes.services;

import com.jovani.recipes.commands.IngredientCommand;
import com.jovani.recipes.converters.IngredientCommandToIngredient;
import com.jovani.recipes.converters.IngredientToIngredientCommand;
import com.jovani.recipes.domain.Ingredient;
import com.jovani.recipes.domain.Recipe;
import com.jovani.recipes.repositories.IngredientRepository;
import com.jovani.recipes.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

    @Transactional
    @Override
    public IngredientCommand findById(Long ingredientId) {
        Optional<Ingredient> optionalIngredient = this.ingredientRepository.findById(ingredientId);
        if(!optionalIngredient.isPresent()) throw new RuntimeException("Ingredient not found");
        return ingredientToIngredientCommand.convert(optionalIngredient.get());
    }

    @Override
    public void deleteById(Long ingredientId) {
        Optional<Ingredient> optionalIngredient = this.ingredientRepository.findById(ingredientId);
        if(!optionalIngredient.isPresent()) throw new RuntimeException("Ingredient not found");
        this.ingredientRepository.delete(optionalIngredient.get());
    }

    @Transactional
    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {

        Optional<Recipe> optionalRecipe = this.recipeRepository
                .findById(ingredientCommand.getRecipeId());

        if(!optionalRecipe.isPresent()) throw new RuntimeException("Invalid recipe id");

        Ingredient ingredient = this.ingredientCommandToIngredient.convert(ingredientCommand);
        ingredient.setRecipe(optionalRecipe.get());

        Ingredient savedIngredient = this.ingredientRepository.save(ingredient);
        return this.ingredientToIngredientCommand.convert(savedIngredient);
    }

}
