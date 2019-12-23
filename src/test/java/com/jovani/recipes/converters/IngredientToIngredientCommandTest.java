package com.jovani.recipes.converters;

import com.jovani.recipes.commands.IngredientCommand;
import com.jovani.recipes.domain.Ingredient;
import com.jovani.recipes.domain.Recipe;
import com.jovani.recipes.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class IngredientToIngredientCommandTest {

    public static final Long ID = 1L;
    public static final String DESCRIPTION = "My notes";
    public static final BigDecimal AMOUNT = new BigDecimal("2");
    public static final Long UOM_ID = new Long(2L);
    public static final Recipe RECIPE = new Recipe();

    private IngredientToIngredientCommand ingredientToIngredientCommand;

    @BeforeEach
    void setUp() {
        this.ingredientToIngredientCommand = new IngredientToIngredientCommand(
                new UnitOfMeasureToUnitOfMeasureCommand()
        );
    }

    @Test
    void convert() {

        Ingredient ingredient = new Ingredient();
        ingredient.setId(ID);
        ingredient.setDescription(DESCRIPTION);

        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(UOM_ID);

        ingredient.setUom(uom);
        ingredient.setAmount(AMOUNT);

        IngredientCommand notes = ingredientToIngredientCommand
                .convert(ingredient);

        assertEquals(ID, notes.getId());
        assertEquals(DESCRIPTION, notes.getDescription());
        assertEquals(UOM_ID, notes.getUom().getId());
        assertEquals(AMOUNT, notes.getAmount());

    }
}