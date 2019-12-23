package com.jovani.recipes.converters;

import com.jovani.recipes.commands.CategoryCommand;
import com.jovani.recipes.domain.Category;
import com.jovani.recipes.domain.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryCommandToCategoryTest {
    private static final String DESCRIPTION = "description";
    private static final Long ID = 1L;

    private CategoryCommandToCategory categoryCommandToCategory;

    @BeforeEach
    void setUp() {
        this.categoryCommandToCategory = new CategoryCommandToCategory();
    }

    @Test
    public void testNullParameter(){
        assertNull(this.categoryCommandToCategory.convert(null));
    }

    @Test
    public void testEmptyObject(){
        assertNotNull(this.categoryCommandToCategory.convert(new CategoryCommand()));
    }

    @Test
    public void convert(){
        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setDescription(DESCRIPTION);
        categoryCommand.setId(ID);

        Category category = this.categoryCommandToCategory.convert(categoryCommand);

        assertNotNull(category);
        assertEquals(ID, category.getId());
        assertEquals(DESCRIPTION, category.getDescription());
    }
}