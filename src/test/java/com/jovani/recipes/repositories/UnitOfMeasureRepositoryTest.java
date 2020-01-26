package com.jovani.recipes.repositories;

import com.jovani.recipes.bootstrap.DataLoader;
import com.jovani.recipes.domain.UnitOfMeasure;
import com.jovani.recipes.repositories.reactive.CategoryReactiveRepository;
import com.jovani.recipes.repositories.reactive.IngredientReactiveRepository;
import com.jovani.recipes.repositories.reactive.RecipeReactiveRepository;
import com.jovani.recipes.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class) // Load spring context
@DataMongoTest
class UnitOfMeasureRepositoryTest {

    @Autowired
    private CategoryReactiveRepository categoryRepository;
    @Autowired
    private RecipeReactiveRepository recipeRepository;
    @Autowired
    private UnitOfMeasureReactiveRepository unitOfMeasureRepository;
    @Autowired
    private IngredientReactiveRepository ingredientRepository;

    @BeforeEach
    void setUp() {
        this.categoryRepository.deleteAll();
        this.recipeRepository.deleteAll();
        this.unitOfMeasureRepository.deleteAll();
        DataLoader dataLoader = new DataLoader(this.categoryRepository,
                this.recipeRepository, this.unitOfMeasureRepository, this.ingredientRepository);
        dataLoader.onApplicationEvent(null);
    }

    @Test
    public void findByDescription(){
        Optional<UnitOfMeasure> uomOptional = this.unitOfMeasureRepository
                .findByDescription("Teaspoon").blockOptional();
        assertEquals("Teaspoon", uomOptional.get().getDescription());
    }

}