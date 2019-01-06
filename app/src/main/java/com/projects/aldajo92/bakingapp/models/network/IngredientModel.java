package com.projects.aldajo92.bakingapp.models.network;

import com.google.gson.annotations.SerializedName;

public class IngredientModel {

    @SerializedName("quantity")
    private double quantity;

    @SerializedName("measure")
    private String measure;

    @SerializedName("ingredient")
    private String name;

    public double getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getName() {
        return name;
    }
}
