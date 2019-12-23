package com.jovani.recipes.converters;

import com.jovani.recipes.commands.IngredientCommand;
import com.jovani.recipes.commands.UnitOfMeasureCommand;
import com.jovani.recipes.domain.Ingredient;
import com.jovani.recipes.domain.Recipe;
import com.jovani.recipes.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class IngredientCommandToIngredientTest {


    public static final Long ID = 1L;
    public static final String DESCRIPTION = "My notes";
    public static final BigDecimal AMOUNT = new BigDecimal("2");
    public static final Long UOM_ID = new Long(2L);
    public static final Recipe RECIPE = new Recipe();

    private IngredientCommandToIngredient ingredientCommandToIngredient;

    @BeforeEach
    void setUp() {
        this.ingredientCommandToIngredient = new IngredientCommandToIngredient(
                new UnitOfMeasureCommandToUnitOfMeasure()
        );
    }

    @Test
    void convert() {

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(ID);
        ingredientCommand.setDescription(DESCRIPTION);

        UnitOfMeasureCommand uom = new UnitOfMeasureCommand();
        uom.setId(UOM_ID);

        ingredientCommand.setUom(uom);
        ingredientCommand.setAmount(AMOUNT);

        Ingredient ingredient = ingredientCommandToIngredient
                .convert(ingredientCommand);

        assertEquals(ID, ingredient.getId());
        assertEquals(DESCRIPTION, ingredient.getDescription());
        assertEquals(UOM_ID, ingredient.getUom().getId());
        assertEquals(AMOUNT, ingredient.getAmount());

    }
}