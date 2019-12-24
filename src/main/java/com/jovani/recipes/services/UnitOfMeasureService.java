package com.jovani.recipes.services;

import com.jovani.recipes.commands.UnitOfMeasureCommand;

import java.util.List;

public interface UnitOfMeasureService {
    public List<UnitOfMeasureCommand> listAllUoms();
}
