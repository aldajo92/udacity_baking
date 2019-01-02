package com.example.aldajo92.bakingapp.db;

import android.arch.persistence.room.TypeConverter;

import com.example.aldajo92.bakingapp.models.network.IngredientModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class DataTypeConverter {
    private static Gson gson = new Gson();
    @TypeConverter
    public static List<IngredientModel> stringToList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<IngredientModel>>() {}.getType();

        return gson.fromJson(data, listType);
    }
 
    @TypeConverter
    public static String ListToString(List<IngredientModel> someObjects) {
        return gson.toJson(someObjects);
    }
}