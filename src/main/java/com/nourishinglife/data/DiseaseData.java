package com.nourishinglife.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Static, in-memory seed data (mirrors the original app's hardcoded diseases/meals),
 * written fresh for this rebuild rather than copied from the source project.
 */
public final class DiseaseData {

    private static final List<Disease> DISEASES = new ArrayList<>();
    private static final List<Meal> MEALS = new ArrayList<>();

    private DiseaseData() {
    }

    static {
        DISEASES.add(new Disease(
                "cancer", "Cancer", "Nutritional support for cancer patients", "🎗️",
                "#EC4899", "#E11D48",
                Arrays.asList(
                        "A diet rich in fruits and vegetables can help reduce cancer risk.",
                        "Green leafy vegetables contain compounds that may help fight cancer cells.",
                        "Cruciferous vegetables like broccoli have anti-cancer properties.",
                        "Antioxidants in colorful fruits help protect cells from damage."
                )));
        DISEASES.add(new Disease(
                "diabetes", "Diabetes", "Blood sugar-friendly meal plans", "🩸",
                "#3B82F6", "#0891B2",
                Arrays.asList(
                        "Fiber-rich foods help regulate blood sugar levels.",
                        "Small, frequent meals can maintain stable glucose levels.",
                        "Whole grains are better than refined grains for diabetes management."
                )));
        DISEASES.add(new Disease(
                "hypertension", "Hypertension", "Heart-healthy, low-sodium meals", "❤️",
                "#EF4444", "#DB2777",
                Arrays.asList(
                        "Reducing sodium intake can lower blood pressure significantly.",
                        "Potassium-rich foods help balance sodium levels.",
                        "The DASH diet is scientifically proven to reduce hypertension."
                )));

        seedMeals("cancer");
        seedMeals("diabetes");
        seedMeals("hypertension");
    }

    private static void seedMeals(String diseaseId) {
        String prefix = diseaseId + "-";

        MEALS.add(new Meal(prefix + "b1", diseaseId, MealCategory.BREAKFAST, "Oat & Berry Bowl",
                "Steel-cut oats topped with mixed berries and a drizzle of honey.",
                new Nutrients("280 kcal", "9g", "48g", "6g"),
                Meal.steps("Cook oats in water or low-fat milk for 5 minutes",
                        "Top with fresh mixed berries", "Add a light drizzle of honey", "Serve warm"),
                Arrays.asList("High in fiber", "Rich in antioxidants", "Supports digestion")));

        MEALS.add(new Meal(prefix + "b2", diseaseId, MealCategory.BREAKFAST, "Veggie Egg White Omelette",
                "Fluffy egg whites folded with spinach, tomato and bell pepper.",
                new Nutrients("210 kcal", "18g", "9g", "8g"),
                Meal.steps("Whisk egg whites", "Saute chopped vegetables lightly",
                        "Pour eggs over vegetables and fold", "Cook until set"),
                Arrays.asList("High protein, low fat", "Nutrient-dense vegetables", "Keeps you full longer")));

        MEALS.add(new Meal(prefix + "l1", diseaseId, MealCategory.LUNCH, "Grilled Chicken & Quinoa Salad",
                "Lean grilled chicken breast over quinoa, greens and cherry tomatoes.",
                new Nutrients("420 kcal", "34g", "38g", "12g"),
                Meal.steps("Grill seasoned chicken breast", "Cook quinoa and let it cool",
                        "Toss with greens and tomatoes", "Finish with a light olive oil dressing"),
                Arrays.asList("Complete protein source", "Balanced macronutrients", "Anti-inflammatory greens")));

        MEALS.add(new Meal(prefix + "l2", diseaseId, MealCategory.LUNCH, "Lentil & Vegetable Soup",
                "A hearty, fiber-packed lentil soup with carrots, celery and onion.",
                new Nutrients("310 kcal", "18g", "45g", "5g"),
                Meal.steps("Saute onion, carrot and celery", "Add lentils and low-sodium broth",
                        "Simmer for 25 minutes", "Season lightly and serve"),
                Arrays.asList("High in plant protein", "Low glycemic impact", "Very filling and low fat")));

        MEALS.add(new Meal(prefix + "d1", diseaseId, MealCategory.DINNER, "Baked Salmon & Steamed Greens",
                "Oven-baked salmon fillet served with steamed broccoli and asparagus.",
                new Nutrients("390 kcal", "32g", "12g", "22g"),
                Meal.steps("Season salmon with herbs and lemon", "Bake at 200C for 15 minutes",
                        "Steam broccoli and asparagus", "Plate together and serve"),
                Arrays.asList("Rich in omega-3 fatty acids", "Supports heart health", "Low in sodium")));

        MEALS.add(new Meal(prefix + "d2", diseaseId, MealCategory.DINNER, "Stir-Fried Tofu & Vegetables",
                "Cubed tofu stir-fried with a colorful mix of seasonal vegetables.",
                new Nutrients("340 kcal", "20g", "28g", "14g"),
                Meal.steps("Pan-sear tofu cubes until golden", "Stir-fry mixed vegetables briefly",
                        "Combine with a light low-sodium sauce", "Serve hot over brown rice"),
                Arrays.asList("Plant-based protein", "High in fiber", "Low saturated fat")));

        MEALS.add(new Meal(prefix + "s1", diseaseId, MealCategory.SNACKS, "Apple Slices & Almond Butter",
                "Crisp apple slices paired with a spoon of natural almond butter.",
                new Nutrients("180 kcal", "4g", "22g", "9g"),
                Meal.steps("Slice apple into wedges", "Serve with a side of almond butter"),
                Arrays.asList("Natural fiber and sweetness", "Healthy unsaturated fats", "Quick and portable")));

        MEALS.add(new Meal(prefix + "s2", diseaseId, MealCategory.SNACKS, "Greek Yogurt & Walnuts",
                "Plain Greek yogurt topped with crushed walnuts and cinnamon.",
                new Nutrients("190 kcal", "14g", "10g", "10g"),
                Meal.steps("Spoon yogurt into a bowl", "Top with crushed walnuts", "Dust with cinnamon"),
                Arrays.asList("High in protein and probiotics", "Supports gut health", "Low added sugar")));

        MEALS.add(new Meal(prefix + "dr1", diseaseId, MealCategory.DRINKS, "Green Tea",
                "A warm cup of antioxidant-rich green tea.",
                new Nutrients("2 kcal", "0g", "0g", "0g"),
                Meal.steps("Steep green tea leaves in hot water for 3 minutes", "Serve without sugar"),
                Arrays.asList("Rich in antioxidants", "Supports metabolism", "Virtually calorie-free")));

        MEALS.add(new Meal(prefix + "dr2", diseaseId, MealCategory.DRINKS, "Cucumber Mint Infused Water",
                "Refreshing water infused with cucumber slices and fresh mint.",
                new Nutrients("5 kcal", "0g", "1g", "0g"),
                Meal.steps("Slice cucumber thinly", "Add cucumber and mint leaves to water",
                        "Chill for at least 1 hour before serving"),
                Arrays.asList("Keeps you hydrated", "No added sugar", "Naturally refreshing")));

        MEALS.add(new Meal(prefix + "v1", diseaseId, MealCategory.VITAMINS, "Vitamin D Supplement",
                "A daily vitamin D3 supplement to support immune and bone health.",
                new Nutrients("0 kcal", "0g", "0g", "0g"),
                Meal.steps("Take one capsule daily with a meal", "Consult your doctor for correct dosage"),
                Arrays.asList("Supports bone health", "Supports immune function")));

        MEALS.add(new Meal(prefix + "v2", diseaseId, MealCategory.VITAMINS, "Vitamin C Boost",
                "A citrus-based vitamin C supplement or fresh-squeezed orange juice.",
                new Nutrients("60 kcal", "1g", "14g", "0g"),
                Meal.steps("Squeeze fresh oranges or take a supplement as directed"),
                Arrays.asList("Supports immune health", "Powerful antioxidant", "Aids iron absorption")));
    }

    public static List<Disease> getDiseases() {
        return DISEASES;
    }

    public static Disease getDiseaseById(String id) {
        return DISEASES.stream().filter(d -> d.getId().equals(id)).findFirst().orElse(null);
    }

    public static List<Meal> getMealsFor(String diseaseId, MealCategory category) {
        List<Meal> result = new ArrayList<>();
        for (Meal m : MEALS) {
            if (m.getDiseaseId().equals(diseaseId) && m.getCategory() == category) {
                result.add(m);
            }
        }
        return result;
    }

    public static int totalMealCount() {
        return MEALS.size();
    }
}
