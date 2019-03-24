package com.example.anton.todoornot;

import android.os.Bundle;
import android.preference.PreferenceActivity;

/**
 * Created by anton on 3/23/2019.
 */

public class prefsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.addPreferencesFromResource(R.xml.prefs);
    }
}
