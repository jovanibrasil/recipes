package com.jovani.recipes.repositories.reactive;

import com.jovani.recipes.domain.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataMongoTest
class CategoryReactiveRepositoryTest {

    @Autowired
    private CategoryReactiveRepository categoryReactiveRepository;

    @BeforeEach
    void setUp() {
        this.categoryReactiveRepository.deleteAll().block();
    }

    @Test
    public void testSave(){
        Category category = new Category();
        category.setDescription("Friturinha");
        this.categoryReactiveRepository.save(category).block();
        Long count = this.categoryReactiveRepository.count().block();
        assertEquals(1, count);
    }

    @Test
    public void testFindByDescription(){
        String description = "Friturinha";
        Category category = new Category();
        category.setDescription(description);
        this.categoryReactiveRepository.save(category).block();
        Category savedCategory = this.categoryReactiveRepository.findByDescription(description).block();
        assertEquals(description, savedCategory.getDescription());
    }

}