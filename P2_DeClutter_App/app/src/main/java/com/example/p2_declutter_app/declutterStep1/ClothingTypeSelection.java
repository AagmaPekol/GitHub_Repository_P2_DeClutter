package com.example.p2_declutter_app.declutterStep1;

public class ClothingTypeSelection {
    private static ClothingTypeSelection instance;
    private String ClothingTypeSelection;

    private ClothingTypeSelection() {} // Private constructor to prevent instantiation

    public static ClothingTypeSelection getInstance() {
        if (instance == null) {
            instance = new ClothingTypeSelection();
        }
        return instance;
    }

    public void setClothingTypeSelection(String choice) {
        this.ClothingTypeSelection = choice;
    }

    public String getUClothingTypeSelection() {
        return ClothingTypeSelection;
    }
}
