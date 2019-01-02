package com.example.aldajo92.bakingapp.service;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;

import com.example.aldajo92.bakingapp.AppExecutors;
import com.example.aldajo92.bakingapp.db.RecipeDatabase;

class RecipeWidgetViewModel extends AndroidViewModel {

    private RecipeDatabase database;

    public RecipeWidgetViewModel(Application application) {
        super(application);

        database = RecipeDatabase.getInstance(application);

    }

    public RecipeDatabase getDatabase() {
        return database;
    }
}
