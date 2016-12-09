package com.ahmadian.ruhallah.commons.base;

import android.os.AsyncTask;

/**
 * Created by ruhallah-PC on 11/25/2016.
 */

public class SmartAsyncTask<T> extends AsyncTask<Void,Void,T> {

    private BaseAsyncTaskCallback callback;

    public SmartAsyncTask(BaseAsyncTaskCallback callback) {
        this.callback = callback;
    }

    @Override
    protected T doInBackground(Void... voids) {
        return (T) callback.doInBackground();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        callback.onPreExecute();
    }

    @Override
    protected void onPostExecute(T t) {
        super.onPostExecute(t);
        callback.onPostExecute(t);
    }

    public interface BaseAsyncTaskCallback<T> {

        void onPreExecute();
        void onPostExecute(T t);
        T doInBackground();

    }
}
