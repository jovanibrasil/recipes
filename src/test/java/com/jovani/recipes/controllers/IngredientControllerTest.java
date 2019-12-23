package com.jovani.recipes.controllers;

import com.jovani.recipes.commands.IngredientCommand;
import com.jovani.recipes.commands.RecipeCommand;
import com.jovani.recipes.services.IngredientService;
import com.jovani.recipes.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class IngredientControllerTest {

    @Mock
    private RecipeService recipeService;
    @Mock
    private IngredientService ingredientService;
    private IngredientController ingredientController;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.ingredientController = new IngredientController(this.recipeService, ingredientService);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.ingredientController).build();
    }

    @Test
    public void testListIngredients() throws Exception {

        RecipeCommand recipeCommand = new RecipeCommand();
        when(recipeService.findIngredientCommandById(anyLong())).thenReturn(recipeCommand);

        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredients/list"))
                .andExpect(model().attributeExists("recipe"));

        verify(recipeService).findIngredientCommandById(anyLong());
    }

    @Test
    public void testViewIngredient() throws Exception {

        IngredientCommand ingredientCommand = new IngredientCommand();
        when(ingredientService.findByRecipeIdAndIngredientID(anyLong(), anyLong())).thenReturn(ingredientCommand);

        mockMvc.perform(get("/recipe/1/ingredient/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredients/viewingredient"))
                .andExpect(model().attributeExists("ingredient"));

        verify(ingredientService).findByRecipeIdAndIngredientID(anyLong(), anyLong());
    }

}