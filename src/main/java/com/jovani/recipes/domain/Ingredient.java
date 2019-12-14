package com.jovani.recipes.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private BigDecimal amount;
    @OneToOne
    private UnitOfMeasure uof;
    @ManyToOne
    private Recipe recipe;

    public Ingredient() {}

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure uof, Recipe recipe) {
        this.description = description;
        this.amount = amount;
        this.uof = uof;
        this.recipe = recipe;
    }

    public Ingredient(String description, BigDecimal amount, UnitOfMeasure uof) {
        this.description = description;
        this.amount = amount;
        this.uof = uof;
    }

}
