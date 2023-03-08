package guru.springframework.bootstrap;

import guru.springframework.domain.Category;
import guru.springframework.domain.Difficulty;
import guru.springframework.domain.Ingredient;
import guru.springframework.domain.Notes;
import guru.springframework.domain.Recipe;
import guru.springframework.domain.UnitOfMeasure;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class RecipeBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public RecipeBootstrap(CategoryRepository categoryRepository, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    private List<Recipe> getRecipes() {

        List<Recipe> recipes = new ArrayList<>(2);

        // get unit of measures
        Optional<UnitOfMeasure> teaspoonUnitOfMeasureOptional = unitOfMeasureRepository.findByDescription("Teaspoon");
        throwErrorIfUnitOfMeasureOptionalNotFound(teaspoonUnitOfMeasureOptional);

        Optional<UnitOfMeasure> tablespoonUnitOfMeasureOptional = unitOfMeasureRepository.findByDescription("Tablespoon");
        throwErrorIfUnitOfMeasureOptionalNotFound(tablespoonUnitOfMeasureOptional);

        Optional<UnitOfMeasure> cupUnitOfMeasureOptional = unitOfMeasureRepository.findByDescription("Cup");
        throwErrorIfUnitOfMeasureOptionalNotFound(cupUnitOfMeasureOptional);

        Optional<UnitOfMeasure> pinchUnitOfMeasureOptional = unitOfMeasureRepository.findByDescription("Pinch");
        throwErrorIfUnitOfMeasureOptionalNotFound(pinchUnitOfMeasureOptional);

        Optional<UnitOfMeasure> ounceUnitOfMeasureOptional = unitOfMeasureRepository.findByDescription("Ounce");
        throwErrorIfUnitOfMeasureOptionalNotFound(ounceUnitOfMeasureOptional);

        Optional<UnitOfMeasure> pintUnitOfMeasureOptional = unitOfMeasureRepository.findByDescription("Pint");
        throwErrorIfUnitOfMeasureOptionalNotFound(pintUnitOfMeasureOptional);

        // get optionals
        UnitOfMeasure teaspoon = teaspoonUnitOfMeasureOptional.get();
        UnitOfMeasure tablespoon = tablespoonUnitOfMeasureOptional.get();
        UnitOfMeasure cup = cupUnitOfMeasureOptional.get();
        UnitOfMeasure pinch = pinchUnitOfMeasureOptional.get();
        UnitOfMeasure ounce = ounceUnitOfMeasureOptional.get();
        UnitOfMeasure pint = pintUnitOfMeasureOptional.get();

        // get categories
        Optional<Category> americanCategoryOptional = categoryRepository.findByDescription("American");
        if (!americanCategoryOptional.isPresent()) {
            throw new RuntimeException("Expected category not found.");
        }

        Optional<Category> mexicanCategoryOptional = categoryRepository.findByDescription("Mexican");
        if (!mexicanCategoryOptional.isPresent()) {
            throw new RuntimeException("Expected category not found.");
        }

        // get optionals
        Category american = americanCategoryOptional.get();
        Category mexican = mexicanCategoryOptional.get();

        // add guacamole recipe
        Recipe perfectGuacamole = new Recipe();
        perfectGuacamole.setDescription("Perfect Guacamole");
        perfectGuacamole.setPrepTime(10);
        perfectGuacamole.setCookTime(0);
        perfectGuacamole.setServings(4);
        perfectGuacamole.setSource("Simply Recipes");
        perfectGuacamole.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");
        perfectGuacamole.setDifficulty(Difficulty.EASY);

        perfectGuacamole.setDirections("Cut the avocados:\n" +
                "Cut the avocados in half. Remove the pit. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl.\n" +
                "\n" +
                "How to make guacamole - scoring avocado\n" +
                "Mash the avocado flesh:\n" +
                "Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)\n" +
                "\n" +
                "How to make guacamole - smashing avocado with fork\n" +
                "Add the remaining ingredients to taste:\n" +
                "Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "\n" +
                "Add the chopped onion, cilantro, black pepper, and chilis. Chili peppers vary individually in their spiciness. So, start with a half of one chili pepper and add more to the guacamole to your desired degree of heat.\n" +
                "\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
                "\n" +
                "Serve immediately:\n" +
                "If making a few hours ahead, place plastic wrap on the surface of the guacamole and press down to cover it to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.)\n" +
                "\n" +
                "Garnish with slices of red radish or jigama strips. Serve with your choice of store-bought tortilla chips or make your own homemade tortilla chips.\n" +
                "\n" +
                "Refrigerate leftover guacamole up to 3 days.\n" +
                "\n" +
                "Note: Chilling tomatoes hurts their flavor. So, if you want to add chopped tomato to your guacamole, add it just before serving.");

        Notes perfectGuacamoleNotes = new Notes();
        perfectGuacamoleNotes.setRecipeNotes("This is yummy.");
        perfectGuacamole.setNotes(perfectGuacamoleNotes);

        perfectGuacamole.getCategories().add(american);
        perfectGuacamole.getCategories().add(mexican);

        perfectGuacamole.addIngredient(new Ingredient("ripe avocados", new BigDecimal(2)));
        perfectGuacamole.addIngredient(new Ingredient("kosher salt", new BigDecimal(1/4), tablespoon));
        perfectGuacamole.addIngredient(new Ingredient("fresh lime or lemon juice", new BigDecimal(1), tablespoon));
        perfectGuacamole.addIngredient(new Ingredient("minced red onion or thinly sliced green onion", new BigDecimal(4), tablespoon));
        perfectGuacamole.addIngredient(new Ingredient("serrano or jalapeno chilis, stems and seeds removed, minced", new BigDecimal(2)));
        perfectGuacamole.addIngredient(new Ingredient("cilantro (leaves and tender stems), finely chopped", new BigDecimal(2), tablespoon));
        perfectGuacamole.addIngredient(new Ingredient("freshluy ground black pepper", new BigDecimal(1), pinch));
        perfectGuacamole.addIngredient(new Ingredient("ripe tomato, chopped (optional)", new BigDecimal(1/2)));
        perfectGuacamole.addIngredient(new Ingredient("red radish or jicama slices for garnish", new BigDecimal(0)));
        perfectGuacamole.addIngredient(new Ingredient("tortilla chips", new BigDecimal(0)));

        recipes.add(perfectGuacamole);

        // add taco recipe
        Recipe spicyGrilledChickenTacos = new Recipe();
        spicyGrilledChickenTacos.setDescription("Spicy Grilled Chicken Tacos");
        spicyGrilledChickenTacos.setPrepTime(20);
        spicyGrilledChickenTacos.setCookTime(15);
        spicyGrilledChickenTacos.setServings(6);
        spicyGrilledChickenTacos.setSource("Simply Recipes");
        spicyGrilledChickenTacos.setUrl("https://www.simplyrecipes.com/recipes/spicy_grilled_chicken_tacos/");
        spicyGrilledChickenTacos.setDifficulty(Difficulty.MODERATE);

        spicyGrilledChickenTacos.setDirections("Prepare the grill:\n" +
                "Prepare either a gas or charcoal grill for medium-high, direct heat.\n" +
                "\n" +
                "Make the marinade and coat the chicken:\n" +
                "In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +
                "\n" +
                "Set aside to marinate while the grill heats and you prepare the rest of the toppings.\n" +
                "\n" +
                "Spicy Grilled Chicken Tacos\n" +
                "Grill the chicken:\n" +
                "Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165°F. Transfer to a plate and rest for 5 minutes.\n" +
                "\n" +
                "Thin the sour cream with milk:\n" +
                "Stir together the sour cream and milk to thin out the sour cream to make it easy to drizzle.\n" +
                "\n" +
                "Assemble the tacos:\n" +
                "Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.\n" +
                "\n" +
                "Warm the tortillas:\n" +
                "Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n" +
                "\n" +
                "Wrap warmed tortillas in a tea towel to keep them warm until serving.");

        Notes spicyGrilledChickenTacosNotes = new Notes();
        spicyGrilledChickenTacosNotes.setRecipeNotes("Meat is murder. Replace chicken with vegan alternative.");
        spicyGrilledChickenTacos.setNotes(spicyGrilledChickenTacosNotes);

        spicyGrilledChickenTacos.getCategories().add(mexican);

        spicyGrilledChickenTacos.addIngredient(new Ingredient("ancho chili powder", new BigDecimal(2), tablespoon));
        spicyGrilledChickenTacos.addIngredient(new Ingredient("dried oregano", new BigDecimal(1), teaspoon));
        spicyGrilledChickenTacos.addIngredient(new Ingredient("dried cumin", new BigDecimal(1), teaspoon));
        spicyGrilledChickenTacos.addIngredient(new Ingredient("sugar", new BigDecimal(1), teaspoon));
        spicyGrilledChickenTacos.addIngredient(new Ingredient("kosher salt", new BigDecimal(1/2), teaspoon));
        spicyGrilledChickenTacos.addIngredient(new Ingredient("clove garlic, finely chopped", new BigDecimal(1)));
        spicyGrilledChickenTacos.addIngredient(new Ingredient("finely grated orange zest", new BigDecimal(1), tablespoon));
        spicyGrilledChickenTacos.addIngredient(new Ingredient("fresh-squeezed orange juice", new BigDecimal(3), tablespoon));
        spicyGrilledChickenTacos.addIngredient(new Ingredient("olive oil", new BigDecimal(2), tablespoon));
        spicyGrilledChickenTacos.addIngredient(new Ingredient("skinless, boneless chicken thighs", new BigDecimal(6)));

        spicyGrilledChickenTacos.addIngredient(new Ingredient("small corn tortillas", new BigDecimal(8)));
        spicyGrilledChickenTacos.addIngredient(new Ingredient("packed baby arugula", new BigDecimal(3), cup));
        spicyGrilledChickenTacos.addIngredient(new Ingredient("medium ripe avocados, sliced", new BigDecimal(2)));
        spicyGrilledChickenTacos.addIngredient(new Ingredient("radishes, thinly sliced", new BigDecimal(4)));
        spicyGrilledChickenTacos.addIngredient(new Ingredient("cherry tomatoes, halved", new BigDecimal(1/2), pint));
        spicyGrilledChickenTacos.addIngredient(new Ingredient("red onion, thinly sliced", new BigDecimal(1/4)));
        spicyGrilledChickenTacos.addIngredient(new Ingredient("roughly chopped cilantro", new BigDecimal(1)));
        spicyGrilledChickenTacos.addIngredient(new Ingredient("sour cream", new BigDecimal(1/2), cup));
        spicyGrilledChickenTacos.addIngredient(new Ingredient("milk", new BigDecimal(1/4), cup));
        spicyGrilledChickenTacos.addIngredient(new Ingredient("lime, cut into wedges", new BigDecimal(1)));

        recipes.add(spicyGrilledChickenTacos);

        return recipes;
    }

    private void throwErrorIfUnitOfMeasureOptionalNotFound(Optional<UnitOfMeasure> teaspoonUnitOfMeasureOptional) {
        if (!teaspoonUnitOfMeasureOptional.isPresent()) {
            throw new RuntimeException("Expected Unit of Measure not found.");
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        recipeRepository.saveAll(getRecipes());
    }
}
