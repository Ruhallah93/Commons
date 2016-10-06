package com.ahmadian.ruhallah.commons.application;

import android.content.Context;
import android.graphics.Typeface;
import android.util.SparseArray;

/**
 * Created by rahmadian on 05/10/2016.
 */
public class CommonsConfiguration {
    private static CommonsConfiguration ourInstance = new CommonsConfiguration();
    private static SparseArray<Typeface> typefaces;
    private static final String preAddress = "fonts/";

    public static CommonsConfiguration getInstance() {
        return ourInstance;
    }

    private CommonsConfiguration() {
        typefaces.put(0, Typeface.DEFAULT);
    }

    public static void setTypeface(Context context, int fontName) {
        if(typefaces.get(fontName) == null){
            Typeface typeface = Typeface.createFromAsset(context.getAssets(), String.format("%s", preAddress+context.getString(fontName)));
            typefaces.put(fontName, typeface);
        }
    }

    public static Typeface getTypeface(int fontName) {
        return typefaces.get(fontName);
    }

}
