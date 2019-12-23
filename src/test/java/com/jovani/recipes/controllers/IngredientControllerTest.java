package com.jovani.recipes.controllers;

import com.jovani.recipes.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class IngredientControllerTest {

    private RecipeService recipeService;
    private IngredientController ingredientController;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);
        this.ingredientController = new IngredientController(this.recipeService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.ingredientController).build();

    }

    @Test
    public void testListIngredients(){



    }

}