package com.ahmadian.ruhallah.commons.application;

/**
 * Created by rahmadian on 05/10/2016.
 */
public class CommonsConfiguration {
    private static CommonsConfiguration ourInstance = new CommonsConfiguration();

    public static CommonsConfiguration getInstance() {
        return ourInstance;
    }

    private CommonsConfiguration() {}
}
