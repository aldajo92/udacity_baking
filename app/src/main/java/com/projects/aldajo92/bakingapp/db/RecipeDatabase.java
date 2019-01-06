package com.projects.aldajo92.bakingapp.db;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(entities = {RecipeEntry.class}, version = 3, exportSchema = false)
public abstract class RecipeDatabase extends RoomDatabase {

    private static final Object LOCK = new Object();
    private static RecipeDatabase favoriteMovieInstance;

    public static RecipeDatabase getInstance(Context context) {
        if (favoriteMovieInstance == null) {
            synchronized (LOCK) {
                favoriteMovieInstance = Room.databaseBuilder(context.getApplicationContext(),
                        RecipeDatabase.class, DBConstants.DATABASE_NAME)
                        .build();
            }
        }
        return favoriteMovieInstance;
    }

    public abstract RecipeDao getRecipeDao();

}
