package com.jovani.recipes.services;

import com.jovani.recipes.domain.Recipe;
import org.springframework.stereotype.Service;

import java.util.List;

public interface RecipeService {
    public List<Recipe> getRecipes();
}
