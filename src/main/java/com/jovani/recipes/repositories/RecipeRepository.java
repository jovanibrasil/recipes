package com.jovani.recipes.repositories;

import com.jovani.recipes.domain.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository  extends CrudRepository<Recipe, Long> { }
