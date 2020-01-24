package com.jovani.recipes.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class Notes {

    private String id = UUID.randomUUID().toString();
    private String recipeNotes;

}
