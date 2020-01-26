package com.jovani.recipes.services;

import com.jovani.recipes.bootstrap.DataLoader;
import com.jovani.recipes.commands.RecipeCommand;
import com.jovani.recipes.converters.RecipeCommandToRecipe;
import com.jovani.recipes.converters.RecipeToRecipeCommand;
import com.jovani.recipes.domain.Recipe;
import com.jovani.recipes.repositories.reactive.CategoryReactiveRepository;
import com.jovani.recipes.repositories.reactive.IngredientReactiveRepository;
import com.jovani.recipes.repositories.reactive.RecipeReactiveRepository;
import com.jovani.recipes.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RecipeServiceIT {

    private static final String NEW_DESCRIPTION = "New description";

    @Autowired
    private RecipeService recipeService;
    @Autowired
    private RecipeReactiveRepository recipeRepository;
    @Autowired
    private RecipeToRecipeCommand recipeToRecipeCommand;

    @Autowired
    private CategoryReactiveRepository categoryRepository;
    @Autowired
    private UnitOfMeasureReactiveRepository unitOfMeasureRepository;
    @Autowired
    private IngredientReactiveRepository ingredientRepository;

    @BeforeAll
    public void setUp() throws Exception {
        this.categoryRepository.deleteAll();
        this.recipeRepository.deleteAll();
        this.unitOfMeasureRepository.deleteAll();
        DataLoader dataLoader = new DataLoader(this.categoryRepository,
                this.recipeRepository, this.unitOfMeasureRepository, this.ingredientRepository);
        dataLoader.onApplicationEvent(null);
    }

    @Test
    public void testSaveOfDescription(){
        Flux<Recipe> fluxRecipes = this.recipeRepository.findAll();
        List<Recipe> recipes = fluxRecipes.collectList().block();
        Recipe testRecipe = recipes.iterator().next();
        RecipeCommand recipeCommand = this.recipeToRecipeCommand.convert(testRecipe);

        recipeCommand.setDescription(NEW_DESCRIPTION);
        RecipeCommand savedRecipeCommand = this.recipeService
                .saveRecipeCommand(recipeCommand).block();

        assertEquals(NEW_DESCRIPTION, recipeCommand.getDescription());
        assertEquals(testRecipe.getId(), savedRecipeCommand.getId());
        assertEquals(testRecipe.getIngredients().size(), savedRecipeCommand.getIngredients().size());
        assertEquals(testRecipe.getCategories().size(), savedRecipeCommand.getCategories().size());

    }

}
