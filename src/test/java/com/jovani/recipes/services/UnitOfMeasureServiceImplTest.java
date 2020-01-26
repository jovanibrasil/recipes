package com.jovani.recipes.services;

import com.jovani.recipes.commands.UnitOfMeasureCommand;
import com.jovani.recipes.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.jovani.recipes.domain.UnitOfMeasure;
import com.jovani.recipes.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import reactor.core.publisher.Flux;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
class UnitOfMeasureServiceImplTest {

    private UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand() ;
    private UnitOfMeasureService unitOfMeasureService;

    @Mock
    private UnitOfMeasureReactiveRepository unitOfMeasureRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.unitOfMeasureService = new UnitOfMeasureServiceImpl(unitOfMeasureRepository, unitOfMeasureToUnitOfMeasureCommand);
    }

    @Test
    public void listAllUoms(){
        UnitOfMeasure unitOfMeasure1 = new UnitOfMeasure();
        unitOfMeasure1.setId("1L");
        UnitOfMeasure unitOfMeasure2 = new UnitOfMeasure();
        unitOfMeasure2.setId("2L");

        when(unitOfMeasureRepository.findAll()).thenReturn(Flux.just(unitOfMeasure1, unitOfMeasure2));

        List<UnitOfMeasureCommand> commands = this.unitOfMeasureService.listAllUoms().collectList().block();

        assertEquals(2, commands.size());
        verify(unitOfMeasureRepository).findAll();
    }

}