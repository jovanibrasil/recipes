package com.jovani.recipes.services;

import com.jovani.recipes.domain.Recipe;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface RecipeService {
    public List<Recipe> getRecipes();
    Recipe findById(Long id);
}
