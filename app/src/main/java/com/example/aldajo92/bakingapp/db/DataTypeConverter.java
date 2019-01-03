package com.example.aldajo92.bakingapp.db;

import androidx.room.TypeConverter;

import com.example.aldajo92.bakingapp.models.network.IngredientModel;
import com.example.aldajo92.bakingapp.models.network.StepModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class DataTypeConverter {
    private static Gson gson = new Gson();

    @TypeConverter
    public static List<IngredientModel> stringIngredientToList(String json) {
        if (json == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<IngredientModel>>() {}.getType();

        return gson.fromJson(json, listType);
    }

    @TypeConverter
    public static List<StepModel> stringStepToList(String json) {
        if (json == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<StepModel>>() {}.getType();

        return gson.fromJson(json, listType);
    }
 
    @TypeConverter
    public static String ingredientListToString(List<IngredientModel> ingredientModelList) {
        return gson.toJson(ingredientModelList);
    }

    @TypeConverter
    public static String stepListToString(List<StepModel> stepModelList) {
        return gson.toJson(stepModelList);
    }
}