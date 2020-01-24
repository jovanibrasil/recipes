package com.jovani.recipes.repositories.reactive;

import com.jovani.recipes.domain.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataMongoTest
class RecipeReactiveRepositoryTest {

    @Autowired
    private RecipeReactiveRepository recipeReactiveRepository;

    @BeforeEach
    void setUp() {
        this.recipeReactiveRepository.deleteAll().block();
    }

    @Test
    public void testRecipeSave(){
        Recipe r = new Recipe();
        r.setDescription("Batata Frita");
        this.recipeReactiveRepository.save(r).block();
        Long count = this.recipeReactiveRepository.count().block();
        assertEquals(1, count);
    }

}