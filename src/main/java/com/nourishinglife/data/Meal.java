package com.nourishinglife.data;

import java.util.Arrays;
import java.util.List;

public class Meal {
    private final String id;
    private final String diseaseId;
    private final MealCategory category;
    private final String name;
    private final String description;
    private final List<String> preparationSteps;
    private final Nutrients nutrients;
    private final List<String> benefits;

    public Meal(String id, String diseaseId, MealCategory category, String name, String description,
                Nutrients nutrients, List<String> preparationSteps, List<String> benefits) {
        this.id = id;
        this.diseaseId = diseaseId;
        this.category = category;
        this.name = name;
        this.description = description;
        this.nutrients = nutrients;
        this.preparationSteps = preparationSteps;
        this.benefits = benefits;
    }

    public static List<String> steps(String... steps) {
        return Arrays.asList(steps);
    }

    public String getId() {
        return id;
    }

    public String getDiseaseId() {
        return diseaseId;
    }

    public MealCategory getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getPreparationSteps() {
        return preparationSteps;
    }

    public Nutrients getNutrients() {
        return nutrients;
    }

    public List<String> getBenefits() {
        return benefits;
    }
}
