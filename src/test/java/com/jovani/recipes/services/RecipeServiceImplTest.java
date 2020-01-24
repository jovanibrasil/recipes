package com.jovani.recipes.services;

import com.jovani.recipes.converters.RecipeCommandToRecipe;
import com.jovani.recipes.converters.RecipeToRecipeCommand;
import com.jovani.recipes.domain.Recipe;
import com.jovani.recipes.exceptions.NotFoundException;
import com.jovani.recipes.repositories.RecipeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class RecipeServiceImplTest {

    private RecipeServiceImpl recipeService;

    @Mock
    private RecipeRepository recipeRepository;

    @Mock
    private RecipeToRecipeCommand recipeToRecipeCommand;
    @Mock
    private RecipeCommandToRecipe recipeCommandToRecipe;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        recipeService = new RecipeServiceImpl(recipeRepository, recipeCommandToRecipe, recipeToRecipeCommand);
    }

    @Test
    void getRecipesTest() {
        Recipe recipe = new Recipe();
        when(recipeRepository.findAll()).thenReturn(Arrays.asList(recipe));
        List<Recipe> recipes = recipeService.getRecipes();
        assertEquals(1, recipes.size());
        // verify if a method is called a determined number of times
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    void getRecipeByIdTest(){
        Recipe recipe = new Recipe();
        recipe.setId("1L");
        Optional<Recipe> optRecipe = Optional.of(recipe);

        when(this.recipeRepository.findById("1L")).thenReturn(optRecipe);

        Recipe recipeReturned = this.recipeService.findById("1L");
        assertNotNull(recipeReturned); // test if not null
        verify(recipeRepository).findById(anyString()); // execution count must be 1
        verify(recipeRepository, never()).findAll(); // findAll method must not be executed

    }

    @Test
    void getRecipeByIdTestNotFound(){
        Assertions.assertThrows(NotFoundException.class, () -> {
            Optional<Recipe> optionalRecipe = Optional.empty();
            when(recipeRepository.findById(any())).thenReturn(optionalRecipe);
            Recipe recipe = recipeService.findById("1L");
        });
    }

}