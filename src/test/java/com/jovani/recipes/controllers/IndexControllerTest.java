package com.jovani.recipes.controllers;

import com.jovani.recipes.domain.Recipe;
import com.jovani.recipes.services.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class IndexControllerTest {

    @Mock
    private RecipeService recipeService;
    @Mock
    private Model model;

    private IndexController indexController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.indexController = new IndexController(this.recipeService);
    }

    @Test
    public void testMockMVC() throws Exception {
        Recipe recipe = new Recipe();
        when(this.recipeService.getRecipes()).thenReturn(Flux.just(recipe));
        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(this.indexController).build();
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"));
    }

    @Test
    public void getIndexPage() {
        Recipe recipe = new Recipe();
        when(this.recipeService.getRecipes()).thenReturn(Flux.just(recipe));
        String viewName = this.indexController.getIndexPage(model);
        assertEquals("index", viewName);
        verify(this.recipeService, times(1)).getRecipes(); // test number of invocations
        // test number of invocations and attribute existence
        //verify(model, times(1)).addAttribute(eq("recipes"), anyList());
        // test number of invocations and verify attribute name and value
        ArgumentCaptor<List<Recipe>> argumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(model, times(1))
                .addAttribute(eq("recipes"), argumentCaptor.capture());
        List<Recipe> listInController = argumentCaptor.getValue();
        assertEquals(1, listInController.size());
        // without argument captor
        verify(model, times(1)).addAttribute(eq("recipes"), eq(Arrays.asList(recipe)));
    }

}