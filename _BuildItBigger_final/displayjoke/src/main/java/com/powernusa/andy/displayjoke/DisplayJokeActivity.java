package com.powernusa.andy.displayjoke;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Andy on 10/4/2016.
 */
public class DisplayJokeActivity extends AppCompatActivity{
    public static final String EXTRA_JOKE_KEY = "extra_joke_key";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displayjoke);
    }
}
