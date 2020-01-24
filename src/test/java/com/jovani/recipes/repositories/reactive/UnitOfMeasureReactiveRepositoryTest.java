package com.jovani.recipes.repositories.reactive;

import com.jovani.recipes.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataMongoTest
class UnitOfMeasureReactiveRepositoryTest {

    @Autowired
    private UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;

    @BeforeEach
    public void setUp() throws Exception {
        this.unitOfMeasureReactiveRepository.deleteAll().block();
    }

    @Test
    public void testSaveUnitOfMeasure(){
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setDescription("Unidade");
        this.unitOfMeasureReactiveRepository.save(unitOfMeasure).block();
        Long count = this.unitOfMeasureReactiveRepository.count().block();
        assertEquals(1, count);
    }

    @Test
    public void testFindByDescriptionUnitOfMeasure(){
        String description = "Unidade";
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setDescription(description);
        this.unitOfMeasureReactiveRepository.save(unitOfMeasure).block();
        UnitOfMeasure savedUnitOfMeasure = this.unitOfMeasureReactiveRepository
                .findByDescription(description).block();
        assertEquals(description, savedUnitOfMeasure.getDescription());
    }

}