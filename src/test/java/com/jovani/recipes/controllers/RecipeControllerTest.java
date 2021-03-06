package com.jovani.recipes.controllers;

import com.jovani.recipes.commands.RecipeCommand;
import com.jovani.recipes.domain.Recipe;
import com.jovani.recipes.exceptions.NotFoundException;
import com.jovani.recipes.services.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class RecipeControllerTest {

    @Mock
    private RecipeService recipeService;
    private RecipeController recipeController;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        recipeController = new RecipeController(recipeService);
        this.mockMvc = MockMvcBuilders
                        .standaloneSetup(this.recipeController)
                        .setControllerAdvice(new ControllerExceptionHandler())
                        .build();
    }

    @Test
    public void testGetRecipe() throws Exception {

        Recipe recipe = new Recipe();
        recipe.setId("1L");

        when(this.recipeService.findById("1L")).thenReturn(Mono.just(recipe));

        this.mockMvc.perform(get("/recipe/1L"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/show"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void testGetRecipeNotFound() throws Exception {
        when(this.recipeService.findById(anyString())).thenThrow(NotFoundException.class);
        this.mockMvc.perform(get("/recipe/500"))
                .andExpect(status().isNotFound())
                .andExpect(view().name("404error"));
    }

    @Test
    public void testGetNewRecipeForm() throws Exception {
        RecipeCommand command = new RecipeCommand();
        this.mockMvc.perform(get("/recipe/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void testPostNewRecipeForm() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId("2L");

        when(recipeService.saveRecipeCommand(any())).thenReturn(Mono.just(command));

        this.mockMvc.perform(post("/recipe")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some description")
                .param("prepTime", "5")
                .param("cookTime", "5")
                .param("servings", "10")
                .param("directions", "some directions"))
            .andExpect(status().is3xxRedirection())
            .andExpect(view().name("redirect:/recipe/2L"));
    }

    @Test
    public void testPostNewRecipeFormWithInvalidData() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId("2L");

        when(recipeService.saveRecipeCommand(any())).thenReturn(Mono.just(command));

        this.mockMvc.perform(post("/recipe")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", ""))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("recipe"))
            .andExpect(view().name("recipe/recipeform"));
    }

    @Test
    public void testPostUpdateRecipeForm() throws Exception {
        RecipeCommand command = new RecipeCommand();
        command.setId("2L");

        when(recipeService.findRecipeCommand(any())).thenReturn(Mono.just(command));

        this.mockMvc.perform(get("/recipe/2/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/recipeform"))
                .andExpect(model().attributeExists("recipe"));
    }

    @Test
    public void testDeleteRecipeForm() throws Exception {
        this.mockMvc.perform(get("/recipe/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
        verify(recipeService).deleteById(anyString());
    }

}