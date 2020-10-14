package com.example.Movie;

import android.app.Application;
import android.content.Context;

public class MovieApplication extends Application {

    private static MovieApplication instance;
   // lateinit var MoviesDbHelper : MoviesDbHelper

    public MovieApplication() {
        instance = this;
    }

    public static Context getContext() {
        return instance;
    }
}
