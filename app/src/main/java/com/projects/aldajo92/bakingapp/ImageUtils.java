package com.projects.aldajo92.bakingapp;

import android.net.Uri;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import android.text.TextUtils;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageUtils {

    private static final String NUTELLA_PIE = "Nutella Pie";
    private static final String BROWNIES = "Brownies";
    private static final String YELLOW_CAKE = "Yellow Cake";
    private static final String CHEESECAKE = "Cheesecake";

    public static int getCakeImageResId(@NonNull String recipeName) {
        switch (recipeName) {
            case NUTELLA_PIE:
                return R.drawable.nutella_pie;
            case YELLOW_CAKE:
                return R.drawable.yellow_cake;
            case CHEESECAKE:
                return R.drawable.cheesecake;
            case BROWNIES:
                return R.drawable.brownies;
            default:
                return R.drawable.nutella_pie;
        }
    }

    public static void loadImageFromUrl(String url, ImageView imageView, @DrawableRes int placeHolder){
        if (!TextUtils.isEmpty(url)) {
            try {
                Picasso.get().load(Uri.parse(url)).into(imageView);
            } catch (Exception ex) {
                Picasso.get().load(placeHolder).into(imageView);
            }
        } else {
            Picasso.get().load(placeHolder).into(imageView);
        }
    }
}
