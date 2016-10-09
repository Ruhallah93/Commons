package com.ahmadian.ruhallah.commons.application;

import android.content.Context;
import android.graphics.Typeface;
import android.util.SparseArray;

import java.util.Hashtable;

/**
 * Created by rahmadian on 05/10/2016.
 */
public class CommonsConfiguration {
    private static CommonsConfiguration ourInstance = new CommonsConfiguration();
    private static final Hashtable<String, Typeface> cache = new Hashtable<String, Typeface>();
    private static final String preAddress = "fonts/";

    public static CommonsConfiguration getInstance() {
        return ourInstance;
    }

    private CommonsConfiguration() {
        if(!cache.containsKey(null)) {
            cache.put(null, Typeface.DEFAULT);
        }
    }

    public static void setTypeface(Context context, String fontName) {
        synchronized(cache){
            if(!cache.containsKey(fontName)){
                Typeface t = Typeface.createFromAsset(context.getAssets(), String.format("%s", preAddress+fontName));
                cache.put(fontName, t);
            }
        }
    }

    public static Typeface getTypeface(String fontName) {
        return cache.get(fontName);
    }

}
