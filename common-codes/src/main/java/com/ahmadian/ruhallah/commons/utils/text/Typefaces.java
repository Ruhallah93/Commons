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
    private static final String defaultFont = "default";

    public static Typeface get(Context context, String name) {
        if (!StringUtils.isEmpty(name)) {
            synchronized (cache) {
                if (!cache.containsKey(name)) {
                    Typeface t = Typeface.createFromAsset(context.getAssets(), String.format("%s", preAddress + name));
                    cache.put(name, t);
                    return cache.get(name);
                }else {
                    return cache.get(name);
                }
            }
        }
        if (cache.containsKey(defaultFont))
            return cache.get(defaultFont);
        return Typeface.DEFAULT;
    }

    public static void setDefaultFont(Context context, String font) {
        synchronized (cache) {
            Typeface t = Typeface.createFromAsset(context.getAssets(), String.format("%s", preAddress + font));
            if (t != null) {
                if (cache.containsKey(defaultFont)) {
                    cache.remove(defaultFont);
                }
                cache.put("default", t);
            }
        }
    }

}
