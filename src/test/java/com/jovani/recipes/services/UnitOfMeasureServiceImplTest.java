package com.jovani.recipes.services;

import com.jovani.recipes.commands.UnitOfMeasureCommand;
import com.jovani.recipes.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.jovani.recipes.domain.UnitOfMeasure;
import com.jovani.recipes.repositories.UnitOfMeasureRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UnitOfMeasureServiceImplTest {

    @Mock
    private UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;
    private UnitOfMeasureService unitOfMeasureService;
    @Mock
    private UnitOfMeasureRepository unitOfMeasureRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        this.unitOfMeasureService = new
                UnitOfMeasureServiceImpl(unitOfMeasureRepository, unitOfMeasureToUnitOfMeasureCommand);
    }

    @Test
    public void listAllUoms(){
        UnitOfMeasure unitOfMeasure1 = new UnitOfMeasure();
        unitOfMeasure1.setId("1L");
        UnitOfMeasure unitOfMeasure2 = new UnitOfMeasure();
        unitOfMeasure1.setId("2L");

        List<UnitOfMeasure> unitOfMeasureList = Arrays.asList(unitOfMeasure1, unitOfMeasure2);

        when(unitOfMeasureRepository.findAll()).thenReturn(unitOfMeasureList);

        List<UnitOfMeasureCommand> commands = this.unitOfMeasureService.listAllUoms();

        assertEquals(2, commands.size());
        verify(unitOfMeasureRepository).findAll();
    }

}