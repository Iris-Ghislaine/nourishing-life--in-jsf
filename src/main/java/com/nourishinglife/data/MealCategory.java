package com.nourishinglife.data;

public enum MealCategory {
    BREAKFAST("Breakfast"),
    LUNCH("Lunch"),
    DINNER("Dinner"),
    SNACKS("Snacks"),
    DRINKS("Drinks"),
    VITAMINS("Vitamins");

    private final String label;

    MealCategory(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
