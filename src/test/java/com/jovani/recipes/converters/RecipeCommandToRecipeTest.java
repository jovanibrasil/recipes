package com.jovani.recipes.converters;

import com.jovani.recipes.commands.CategoryCommand;
import com.jovani.recipes.commands.IngredientCommand;
import com.jovani.recipes.commands.NotesCommand;
import com.jovani.recipes.commands.RecipeCommand;
import com.jovani.recipes.domain.Difficulty;
import com.jovani.recipes.domain.Notes;
import com.jovani.recipes.domain.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class RecipeCommandToRecipeTest {

    public static final Long RECIPE_ID = 1L;
    public static final Integer COOK_TIME = Integer.valueOf("5");
    public static final Integer PREP_TIME = Integer.valueOf("7");
    public static final String DESCRIPTION = "My recipe";
    public static final String DIRECTIONS = "Directions";
    public static final Difficulty DIFFICULTY = Difficulty.EASY;
    public static final Integer SERVINGS = Integer.valueOf("3");
    public static final String SOURCE = "Source";
    public static final String URL = "Some url";
    public static final Long CAT_ID1 = 1L;
    public static final Long CAT_ID2 = 2L;
    public static final Long INGRED_ID1 = 1L;
    public static final Long INGRED_ID2 = 2L;
    public static final Long NOTES_ID = 9L;
    public RecipeCommandToRecipe converter;

    @BeforeEach
    void setUp() {
        this.converter = new RecipeCommandToRecipe(
                new CategoryCommandToCategory(), new IngredientCommandToIngredient(new UnitOfMeasureCommandToUnitOfMeasure()),
                new NotesCommandToNotes()
        );
    }

    @Test
    public void testNullObject(){
        assertNull(this.converter.convert(null));
    }

    @Test
    public void testEmptyObject(){
        assertNotNull(this.converter.convert(new RecipeCommand()));
    }

    @Test
    void convert() {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(RECIPE_ID);
        recipeCommand.setCookTime(COOK_TIME);
        recipeCommand.setPrepTime(PREP_TIME);
        recipeCommand.setDescription(DESCRIPTION);
        recipeCommand.setDirections(DIRECTIONS);
        recipeCommand.setServings(SERVINGS);
        recipeCommand.setSource(SOURCE);
        recipeCommand.setDifficulty(DIFFICULTY);

        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(NOTES_ID);
        recipeCommand.setNotes(notesCommand);

        CategoryCommand categoryCommand1 = new CategoryCommand();
        categoryCommand1.setId(CAT_ID1);
        CategoryCommand categoryCommand2 = new CategoryCommand();
        categoryCommand2.setId(CAT_ID2);
        recipeCommand.setCategories(Arrays.asList(categoryCommand1, categoryCommand2));

        IngredientCommand ingredientCommand1 = new IngredientCommand();
        ingredientCommand1.setId(INGRED_ID1);
        IngredientCommand ingredientCommand2 = new IngredientCommand();
        ingredientCommand1.setId(INGRED_ID2);
        recipeCommand.setIngredients(Arrays.asList(ingredientCommand1, ingredientCommand2));

        Recipe recipe = this.converter.convert(recipeCommand);

        assertEquals(RECIPE_ID, recipe.getId());
        assertEquals(PREP_TIME, recipe.getPrepTime());
        assertEquals(COOK_TIME, recipe.getCookTime());
        assertEquals(DESCRIPTION, recipe.getDescription());
        assertEquals(DIRECTIONS, recipe.getDirections());
        assertEquals(SERVINGS, recipe.getServings());
        assertEquals(SOURCE, recipe.getSource());
        assertEquals(DIFFICULTY, recipe.getDifficulty());
        assertNotNull(recipe.getNotes());
        assertNotNull(recipe.getCategories());
        assertNotNull(recipe.getIngredients());
        assertEquals(2, recipe.getCategories().size());
        assertEquals(2, recipe.getIngredients().size());
    }

}