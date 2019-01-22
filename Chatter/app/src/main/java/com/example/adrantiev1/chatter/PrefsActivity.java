package com.example.adrantiev1.chatter;

import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.PerformanceTestCase;

public class PrefsActivity extends PreferenceActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.addPreferencesFromResource(R.xml.prefs);
    }
}
