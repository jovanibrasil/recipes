package com.jovani.recipes.services;

import com.jovani.recipes.domain.Recipe;
import com.jovani.recipes.repositories.RecipeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public List<Recipe> getRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        recipeRepository.findAll().forEach(recipes::add);
        return recipes;
    }
}
