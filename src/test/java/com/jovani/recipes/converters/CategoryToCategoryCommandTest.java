package com.jovani.recipes.converters;

import com.jovani.recipes.commands.CategoryCommand;
import com.jovani.recipes.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryToCategoryCommandTest {

    private static final String DESCRIPTION = "description";
    private static final String ID = "1L";

    private CategoryToCategoryCommand categoryToCategoryCommand;

    @BeforeEach
    void setUp() {
        this.categoryToCategoryCommand = new CategoryToCategoryCommand();
    }

    @Test
    public void testNullParameter(){
        assertNull(this.categoryToCategoryCommand.convert(null));
    }

    @Test
    public void testEmptyObject(){
        assertNotNull(this.categoryToCategoryCommand.convert(new Category()));
    }

    @Test
    public void convert(){
        Category categoryCommand = new Category();
        categoryCommand.setDescription(DESCRIPTION);
        categoryCommand.setId(ID);

        CategoryCommand category = this.categoryToCategoryCommand.convert(categoryCommand);

        assertNotNull(category);
        assertEquals(ID, category.getId());
        assertEquals(DESCRIPTION, category.getDescription());
    }
}