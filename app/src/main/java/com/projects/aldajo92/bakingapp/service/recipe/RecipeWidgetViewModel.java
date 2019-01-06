package com.projects.aldajo92.bakingapp.service.recipe;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;

import com.projects.aldajo92.bakingapp.db.RecipeDatabase;

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
