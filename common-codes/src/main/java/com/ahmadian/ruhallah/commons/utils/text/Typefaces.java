package com.ahmadian.ruhallah.commons.utils.text;

import android.content.Context;
import android.graphics.Typeface;

import java.util.Hashtable;

/**
 * Created by ruhallah-PC on 10/9/2016.
 */

public class Typefaces {

    private static final Hashtable<String, Typeface> cache = new Hashtable<String, Typeface>();
    private static final String preAddress = "fonts/";

    public static Typeface get(Context c, String name) {
        if (!StringUtils.isEmpty(name)) {
            synchronized (cache) {
                if (!cache.containsKey(name)) {
                    Typeface t = Typeface.createFromAsset(c.getAssets(), String.format("%s", preAddress + name));
                    cache.put(name, t);
                    return cache.get(name);
                }
            }
        }
        return Typeface.DEFAULT;
    }

}
