package com.jovani.recipes.bootstrap;

import com.jovani.recipes.domain.*;
import com.jovani.recipes.repositories.reactive.CategoryReactiveRepository;
import com.jovani.recipes.repositories.reactive.IngredientReactiveRepository;
import com.jovani.recipes.repositories.reactive.RecipeReactiveRepository;
import com.jovani.recipes.repositories.reactive.UnitOfMeasureReactiveRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryReactiveRepository categoryRepository;
    private final RecipeReactiveRepository recipeRepository;
    private final UnitOfMeasureReactiveRepository unitOfMeasureRepository;
    private final IngredientReactiveRepository ingredientRepository;

    private void loadCategories(){
        Category cat1 = new Category();
        cat1.setDescription("American");
        categoryRepository.save(cat1).block();

        Category cat2 = new Category();
        cat2.setDescription("Italian");
        categoryRepository.save(cat2).block();

        Category cat3 = new Category();
        cat3.setDescription("Mexican");
        categoryRepository.save(cat3).block();

        Category cat4 = new Category();
        cat4.setDescription("Fast Food");
        categoryRepository.save(cat4).block();
    }

    private void loadUom(){
        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setDescription("Teaspoon");
        unitOfMeasureRepository.save(uom1).block();

        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setDescription("Tablespoon");
        unitOfMeasureRepository.save(uom2).block();

        UnitOfMeasure uom3 = new UnitOfMeasure();
        uom3.setDescription("Cup");
        unitOfMeasureRepository.save(uom3).block();

        UnitOfMeasure uom4 = new UnitOfMeasure();
        uom4.setDescription("Pinch");
        unitOfMeasureRepository.save(uom4).block();

        UnitOfMeasure uom5 = new UnitOfMeasure();
        uom5.setDescription("Ounce");
        unitOfMeasureRepository.save(uom5).block();

        UnitOfMeasure uom6 = new UnitOfMeasure();
        uom6.setDescription("Each");
        unitOfMeasureRepository.save(uom6).block();

        UnitOfMeasure uom7 = new UnitOfMeasure();
        uom7.setDescription("Pint");
        unitOfMeasureRepository.save(uom7).block();

        UnitOfMeasure uom8 = new UnitOfMeasure();
        uom8.setDescription("Dash");
        unitOfMeasureRepository.save(uom8).block();
    }

    private void loadRecipes() throws IOException {

        //get UOMs
        Optional<UnitOfMeasure> eachUomOptional = unitOfMeasureRepository.findByDescription("Each").blockOptional();

        if(!eachUomOptional.isPresent()){ throw new RuntimeException("Expected UOM Not Found"); }

        Optional<UnitOfMeasure> tableSpoonUomOptional = unitOfMeasureRepository.findByDescription("Tablespoon").blockOptional();

        if(!tableSpoonUomOptional.isPresent()){ throw new RuntimeException("Expected UOM Not Found"); }

        Optional<UnitOfMeasure> teaSpoonUomOptional = unitOfMeasureRepository.findByDescription("Teaspoon").blockOptional();

        if(!teaSpoonUomOptional.isPresent()){ throw new RuntimeException("Expected UOM Not Found"); }

        Optional<UnitOfMeasure> dashUomOptional = unitOfMeasureRepository.findByDescription("Dash").blockOptional();

        if(!dashUomOptional.isPresent()){ throw new RuntimeException("Expected UOM Not Found"); }

        Optional<UnitOfMeasure> pintUomOptional = unitOfMeasureRepository.findByDescription("Pint").blockOptional();

        if(!pintUomOptional.isPresent()){ throw new RuntimeException("Expected UOM Not Found"); }

        Optional<UnitOfMeasure> cupsUomOptional = unitOfMeasureRepository.findByDescription("Cup").blockOptional();

        if(!cupsUomOptional.isPresent()){ throw new RuntimeException("Expected UOM Not Found"); }

        //get optionals
        UnitOfMeasure eachUom = eachUomOptional.get();
        UnitOfMeasure tableSpoonUom = tableSpoonUomOptional.get();
        UnitOfMeasure teapoonUom = tableSpoonUomOptional.get();
        UnitOfMeasure dashUom = dashUomOptional.get();
        UnitOfMeasure pintUom = pintUomOptional.get();
        UnitOfMeasure cupsUom = cupsUomOptional.get();

        //get Categories
        Optional<Category> americanCategoryOptional = categoryRepository.findByDescription("American").blockOptional();

        if(!americanCategoryOptional.isPresent()){ throw new RuntimeException("Expected Category Not Found"); }

        Optional<Category> mexicanCategoryOptional = categoryRepository.findByDescription("Mexican").blockOptional();

        if(!mexicanCategoryOptional.isPresent()){ throw new RuntimeException("Expected Category Not Found"); }

        Category americanCategory = americanCategoryOptional.get();
        Category mexicanCategory = mexicanCategoryOptional.get();

        //Yummy Guac
        Recipe guacRecipe = new Recipe();
        this.recipeRepository.save(guacRecipe);
        guacRecipe.setDescription("Perfect Guacamole");
        guacRecipe.setPrepTime(10);
        guacRecipe.setCookTime(0);
        guacRecipe.setDifficulty(Difficulty.EASY);
        guacRecipe.setDirections("1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon" +
                "\n" +
                "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)" +
                "\n" +
                "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
                "4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.\n" +
                "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvpiV9Sd");

        Notes guacNotes = new Notes();
        guacNotes.setRecipeNotes("For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados.\n" +
                "Feel free to experiment! One classic Mexican guacamole has pomegranate seeds and chunks of peaches in it (a Diana Kennedy favorite). Try guacamole with added pineapple, mango, or strawberries.\n" +
                "The simplest version of guacamole is just mashed avocados with salt. Don't let the lack of availability of other ingredients stop you from making guacamole.\n" +
                "To extend a limited supply of avocados, add either sour cream or cottage cheese to your guacamole dip. Purists may be horrified, but so what? It tastes great.\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/perfect_guacamole/#ixzz4jvoun5ws");
        guacRecipe.setNotes(guacNotes);

        Ingredient ripeAvocados = new Ingredient("ripe avocados", new BigDecimal(2), eachUom, guacRecipe.getId());
        Ingredient kosherSalt = new Ingredient("Kosher salt", new BigDecimal(".5"), teapoonUom, guacRecipe.getId());
        Ingredient limeLemonJuice = new Ingredient("fresh lime juice or lemon juice", new BigDecimal(2), tableSpoonUom, guacRecipe.getId());
        Ingredient redOnion = new Ingredient("minced red onion or thinly sliced green onion", new BigDecimal(2), tableSpoonUom, guacRecipe.getId());
        Ingredient serranoChiles = new Ingredient("serrano chiles, stems and seeds removed, minced", new BigDecimal(2), eachUom, guacRecipe.getId());
        Ingredient cilantro = new Ingredient("Cilantro", new BigDecimal(2), tableSpoonUom, guacRecipe.getId());
        Ingredient pepper = new Ingredient("freshly grated black pepper", new BigDecimal(2), dashUom, guacRecipe.getId());
        Ingredient tomato = new Ingredient("ripe tomato, seeds and pulp removed, chopped", new BigDecimal(".5"), eachUom, guacRecipe.getId());

        List<Ingredient> guacIngredients = Arrays.asList(
            ingredientRepository.save(ripeAvocados).block(),
            ingredientRepository.save(kosherSalt).block(),
            ingredientRepository.save(limeLemonJuice).block(),
            ingredientRepository.save(redOnion).block(),
            ingredientRepository.save(serranoChiles).block(),
            ingredientRepository.save(cilantro).block(),
            ingredientRepository.save(pepper).block(),
            ingredientRepository.save(tomato).block()
        );
        guacRecipe.setIngredients(guacIngredients);

        guacRecipe.getCategories().add(americanCategory);
        guacRecipe.getCategories().add(mexicanCategory);
        guacRecipe.setImage(loadImage("./static/images/guacamole.jpg"));
        guacRecipe.setSource("Simply Recipes");
        guacRecipe.setServings(4);

        this.recipeRepository.save(guacRecipe).block();

        //Yummy Tacos
        Recipe tacosRecipe = new Recipe();
        this.recipeRepository.save(tacosRecipe).block();
        tacosRecipe.setDescription("Spicy Grilled Chicken Taco");
        tacosRecipe.setCookTime(9);
        tacosRecipe.setPrepTime(20);
        tacosRecipe.setDifficulty(Difficulty.MODERATE);

        tacosRecipe.setDirections("1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" +
                "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +
                "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n" +
                "\n" +
                "\n" +
                "3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n" +
                "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n" +
                "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n" +
                "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvtrAnNm");

        Notes tacoNotes = new Notes();
        tacoNotes.setRecipeNotes("We have a family motto and it is this: Everything goes better in a tortilla.\n" +
                "Any and every kind of leftover can go inside a warm tortilla, usually with a healthy dose of pickled jalapenos. I can always sniff out a late-night snacker when the aroma of tortillas heating in a hot pan on the stove comes wafting through the house.\n" +
                "Today’s tacos are more purposeful – a deliberate meal instead of a secretive midnight snack!\n" +
                "First, I marinate the chicken briefly in a spicy paste of ancho chile powder, oregano, cumin, and sweet orange juice while the grill is heating. You can also use this time to prepare the taco toppings.\n" +
                "Grill the chicken, then let it rest while you warm the tortillas. Now you are ready to assemble the tacos and dig in. The whole meal comes together in about 30 minutes!\n" +
                "\n" +
                "\n" +
                "Read more: http://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/#ixzz4jvu7Q0MJ");
        tacosRecipe.setNotes(tacoNotes);


        Ingredient anchoChili = new Ingredient("Ancho Chili Powder", new BigDecimal(2), tableSpoonUom, tacosRecipe.getId());
        Ingredient oregano = new Ingredient("Dried Oregano", new BigDecimal(1), teapoonUom, tacosRecipe.getId());
        Ingredient cumin = new Ingredient("Dried Cumin", new BigDecimal(1), teapoonUom, tacosRecipe.getId());
        Ingredient sugar = new Ingredient("Sugar", new BigDecimal(1), teapoonUom, tacosRecipe.getId());
        Ingredient salt = new Ingredient("Salt", new BigDecimal(".5"), teapoonUom, tacosRecipe.getId());
        Ingredient garlic = new Ingredient("Clove of Garlic, Choppedr", new BigDecimal(1), eachUom, tacosRecipe.getId());
        Ingredient orange = new Ingredient("finely grated orange zestr", new BigDecimal(1), tableSpoonUom, tacosRecipe.getId());
        Ingredient juice = new Ingredient("fresh-squeezed orange juice", new BigDecimal(3), tableSpoonUom, tacosRecipe.getId());
        Ingredient oil = new Ingredient("Olive Oil", new BigDecimal(2), tableSpoonUom, tacosRecipe.getId());
        Ingredient chicken = new Ingredient("boneless chicken thighs", new BigDecimal(4), tableSpoonUom, tacosRecipe.getId());
        Ingredient corn = new Ingredient("small corn tortillasr", new BigDecimal(8), eachUom, tacosRecipe.getId());
        Ingredient arugula = new Ingredient("packed baby arugula", new BigDecimal(3), cupsUom, tacosRecipe.getId());
        Ingredient avocados = new Ingredient("medium ripe avocados, slic", new BigDecimal(2), eachUom, tacosRecipe.getId());
        Ingredient radishes = new Ingredient("radishes, thinly sliced", new BigDecimal(4), eachUom, tacosRecipe.getId());
        Ingredient tomatos = new Ingredient("cherry tomatoes, halved", new BigDecimal(".5"), pintUom, tacosRecipe.getId());
        Ingredient onion = new Ingredient("red onion, thinly sliced", new BigDecimal(".25"), eachUom, tacosRecipe.getId());
        Ingredient choppedCilantro = new Ingredient("Roughly chopped cilantro", new BigDecimal(4), eachUom, tacosRecipe.getId());
        Ingredient milk = new Ingredient("cup sour cream thinned with 1/4 cup milk", new BigDecimal(4), cupsUom, tacosRecipe.getId());
        Ingredient lime = new Ingredient("lime, cut into wedges", new BigDecimal(4), eachUom, tacosRecipe.getId());

        List<Ingredient> tacoIngredients = Arrays.asList(
            ingredientRepository.save(anchoChili).block(),
            ingredientRepository.save(oregano).block(),
            ingredientRepository.save(cumin).block(),
            ingredientRepository.save(sugar).block(),
            ingredientRepository.save(salt).block(),
            ingredientRepository.save(garlic).block(),
            ingredientRepository.save(orange).block(),
            ingredientRepository.save(juice).block(),
            ingredientRepository.save(oil).block(),
            ingredientRepository.save(chicken).block(),
            ingredientRepository.save(corn).block(),
            ingredientRepository.save(arugula).block(),
            ingredientRepository.save(avocados).block(),
            ingredientRepository.save(radishes).block(),
            ingredientRepository.save(tomatos).block(),
            ingredientRepository.save(onion).block(),
            ingredientRepository.save(choppedCilantro).block(),
            ingredientRepository.save(milk).block(),
            ingredientRepository.save(lime).block()
        );

        tacosRecipe.setIngredients(tacoIngredients);
        tacosRecipe.setImage(loadImage("./static/images/tacos.jpg")); // Byte[]
        tacosRecipe.getCategories().add(americanCategory);
        tacosRecipe.getCategories().add(mexicanCategory);
        tacosRecipe.setServings(5);
        tacosRecipe.setSource("Simply Recipes");
        this.recipeRepository.save(tacosRecipe).block();

    }

    public Byte[] loadImage(String path){
        try {
            byte[] image = Files.readAllBytes(Paths.get(this.getClass()
                    .getClassLoader().getResource(path).toURI()));
            Byte[] byteImage = new Byte[image.length];
            for (int i=0; i < image.length; i++) byteImage[i] = Byte.valueOf(image[i]);
            return byteImage;
        }catch (Exception e){
            return null;
        }
    }

    @SneakyThrows
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.debug("Loading bootstrap data");
        loadCategories();
        loadUom();
        loadRecipes();
    }
}
