package com.projects.aldajo92.bakingapp.models.ui;

import android.os.Parcel;
import android.os.Parcelable;

import com.projects.aldajo92.bakingapp.models.network.IngredientModel;

public class Ingredient implements Parcelable {

    private double quantity;

    private String measure;

    private String name;

    private Ingredient(Parcel in) {
        quantity = in.readDouble();
        measure = in.readString();
        name = in.readString();
    }

    public Ingredient() {

    }

    public Ingredient(IngredientModel ingredientModel) {
        quantity = ingredientModel.getQuantity();
        measure = ingredientModel.getMeasure();
        name = ingredientModel.getName();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(quantity);
        dest.writeString(measure);
        dest.writeString(name);
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", quantity, measure, name);
    }
}
