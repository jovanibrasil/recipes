package com.jovani.recipes.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.UUID;

@Getter @Setter
@Document
public class Ingredient {

    private String id = UUID.randomUUID().toString();
    private String description;
    private BigDecimal amount;
    @DBRef
    private UnitOfMeasure uom;
    private String recipeId;

    public Ingredient() {}

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure uom, String recipeId) {
        this.description = description;
        this.amount = amount;
        this.uom = uom;
        this.recipeId = recipeId;
    }

}
