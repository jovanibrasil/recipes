package com.jovani.recipes.commands;

import com.jovani.recipes.domain.Difficulty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@NoArgsConstructor
public class RecipeCommand {

    private String id;
    @NotBlank
    @Size(min = 3, max = 255)
    private String description;
    @Min(1) @Max(999) @NotNull
    private Integer prepTime;
    @Min(1) @Max(999) @NotNull
    private Integer cookTime;
    @Min(1) @Max(100) @NotNull
    private Integer servings;
    private String source;
    @NotBlank
    private String directions;
    private Difficulty difficulty;
    private Byte[] image;
    private NotesCommand notes;
    private List<IngredientCommand> ingredients = new ArrayList<>();
    private List<CategoryCommand> categories = new ArrayList<>();

}
