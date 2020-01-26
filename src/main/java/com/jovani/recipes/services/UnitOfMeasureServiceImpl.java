package com.jovani.recipes.services;

import com.jovani.recipes.commands.UnitOfMeasureCommand;
import com.jovani.recipes.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.jovani.recipes.repositories.reactive.UnitOfMeasureReactiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Service
public class UnitOfMeasureServiceImpl implements UnitOfMeasureService {

    private final UnitOfMeasureReactiveRepository unitOfMeasureRepository;
    private final UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand;

    @Override
    public Flux<UnitOfMeasureCommand> listAllUoms() {
        return this.unitOfMeasureRepository
            .findAll().map(unitOfMeasureToUnitOfMeasureCommand::convert);
    }
}
