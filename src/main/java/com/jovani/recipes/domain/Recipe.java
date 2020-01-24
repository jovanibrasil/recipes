package com.jovani.recipes.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Document
public class Recipe {

    @Id
    private String id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String directions;
    private Difficulty difficulty;
    private Byte[] image;
    private Notes notes;
    @DBRef
    private List<Ingredient> ingredients = new ArrayList<>();
    @DBRef
    private List<Category> categories = new ArrayList<>();

    public void addIngredient(Ingredient ingredient){
        this.ingredients.add(ingredient);
    }

    public void setNotes(Notes notes) {
        if(notes != null){
            this.notes = notes;
        }
    }

}
