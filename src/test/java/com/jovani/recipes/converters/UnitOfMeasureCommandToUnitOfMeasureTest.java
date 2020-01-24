package com.jovani.recipes.converters;

import com.jovani.recipes.commands.UnitOfMeasureCommand;
import com.jovani.recipes.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UnitOfMeasureCommandToUnitOfMeasureTest {

    private static final String DESCRIPTION = "description";
    private static final String LONG_VALUE = "1L";

    private UnitOfMeasureCommandToUnitOfMeasure converter;

    @BeforeEach
    void setUp() {
        this.converter = new UnitOfMeasureCommandToUnitOfMeasure();
    }

    @Test
    public void testNullParameter(){
        assertNull(this.converter.convert(null));
    }

    @Test
    public void testEmptyObject(){
        assertNotNull(this.converter.convert(new UnitOfMeasureCommand()));
    }

    @Test
    public void convert(){
        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setDescription(DESCRIPTION);
        unitOfMeasureCommand.setId(LONG_VALUE);

        UnitOfMeasure unitOfMeasure = converter.convert(unitOfMeasureCommand);

        assertNotNull(unitOfMeasure);
        assertEquals(LONG_VALUE, unitOfMeasure.getId());
        assertEquals(DESCRIPTION, unitOfMeasure.getDescription());
    }

}