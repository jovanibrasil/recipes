package com.jovani.recipes.services;

import com.jovani.recipes.commands.IngredientCommand;
import com.jovani.recipes.converters.IngredientCommandToIngredient;
import com.jovani.recipes.converters.IngredientToIngredientCommand;
import com.jovani.recipes.domain.Ingredient;
import com.jovani.recipes.repositories.reactive.IngredientReactiveRepository;
import com.jovani.recipes.repositories.reactive.RecipeReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class IngredientServiceImpl implements  IngredientService {

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientReactiveRepository ingredientRepository;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final RecipeReactiveRepository recipeRepository;

    @Override
    public Mono<IngredientCommand> findById(String ingredientId) {
        Optional<Ingredient> optionalIngredient = this.ingredientRepository.findById(ingredientId).blockOptional();
        if(!optionalIngredient.isPresent()) throw new RuntimeException("Ingredient not found");
        IngredientCommand ingredientCommand = this.ingredientToIngredientCommand
                .convert(optionalIngredient.get());
        return Mono.just(ingredientCommand);
    }

    @Override
    public Mono<Void> deleteById(String ingredientId) {
        Optional<Ingredient> optionalIngredient = this.ingredientRepository.findById(ingredientId).blockOptional();
        if(!optionalIngredient.isPresent()) throw new RuntimeException("Ingredient not found");
        this.ingredientRepository.delete(optionalIngredient.get());
        return Mono.empty();
    }

    @Override
    public Mono<IngredientCommand> saveIngredientCommand(IngredientCommand ingredientCommand) {
        Ingredient ingredient = this.ingredientCommandToIngredient.convert(ingredientCommand);
        Ingredient savedIngredient = this.ingredientRepository.save(ingredient).block();
        ingredientCommand = this.ingredientToIngredientCommand.convert(savedIngredient);
        return Mono.just(ingredientCommand);
    }

}
