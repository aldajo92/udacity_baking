package com.example.aldajo92.bakingapp.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
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
