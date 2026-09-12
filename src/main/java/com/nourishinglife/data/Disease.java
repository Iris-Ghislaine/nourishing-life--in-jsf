package com.nourishinglife.data;

import java.util.List;

public class Disease {
    private final String id;
    private final String name;
    private final String description;
    private final String icon;
    private final String colorFrom;
    private final String colorTo;
    private final List<String> didYouKnow;

    public Disease(String id, String name, String description, String icon,
                    String colorFrom, String colorTo, List<String> didYouKnow) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.colorFrom = colorFrom;
        this.colorTo = colorTo;
        this.didYouKnow = didYouKnow;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    public String getColorFrom() {
        return colorFrom;
    }

    public String getColorTo() {
        return colorTo;
    }

    public List<String> getDidYouKnow() {
        return didYouKnow;
    }
}
