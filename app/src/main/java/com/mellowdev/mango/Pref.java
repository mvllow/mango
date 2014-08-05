package com.mellowdev.mango;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class Pref extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.layout.pref);
    }
}