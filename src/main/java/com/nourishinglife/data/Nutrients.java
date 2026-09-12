package com.nourishinglife.data;

public class Nutrients {
    private final String calories;
    private final String protein;
    private final String carbs;
    private final String fats;

    public Nutrients(String calories, String protein, String carbs, String fats) {
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fats = fats;
    }

    public String getCalories() {
        return calories;
    }

    public String getProtein() {
        return protein;
    }

    public String getCarbs() {
        return carbs;
    }

    public String getFats() {
        return fats;
    }
}
