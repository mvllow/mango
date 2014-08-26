package com.mellowdev.mango;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

public class Main extends Activity implements SharedPreferences.OnSharedPreferenceChangeListener {
    public static String sel = "";
    public static String reply = "";

    private boolean prefVibrate;

    private AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        audioManager = (AudioManager)getSystemService(AUDIO_SERVICE);

        checkStates();
        loadPrefs();
    }

    public void onClick(View v) {
        final Button bAway = (Button) findViewById(R.id.bAway);
        final Button bBusy = (Button) findViewById(R.id.bBusy);
        final Button bDriving = (Button) findViewById(R.id.bDriving);
        final Button bWorking = (Button) findViewById(R.id.bWorking);
        final Switch sDnd = (Switch) findViewById(R.id.sDnd);

        bAway.setBackgroundColor(Color.parseColor("#03a9f4"));
        bBusy.setBackgroundColor(Color.parseColor("#03a9f4"));
        bDriving.setBackgroundColor(Color.parseColor("#03a9f4"));
        bWorking.setBackgroundColor(Color.parseColor("#03a9f4"));

        Sms.people.clear();

        switch (v.getId()) {
            case R.id.bAway:
                reply = "// Auto Reply // I am away right now.";
                bAway.setBackgroundColor(Color.parseColor("#8bc34a"));
                break;

            case R.id.bBusy:
                reply = "// Auto Reply // I am busy right now.";
                bBusy.setBackgroundColor(Color.parseColor("#8bc34a"));
                break;

            case R.id.bDriving:
                reply = "// Auto Reply // I am driving right now.";
                bDriving.setBackgroundColor(Color.parseColor("#8bc34a"));
                break;

            case R.id.bWorking:
                reply = "// Auto Reply // I am working right now.";
                bWorking.setBackgroundColor(Color.parseColor("#8bc34a"));
                break;

            case R.id.sDnd:
                if (sDnd.isChecked()) {
                    if (prefVibrate) audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
                    else audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                } else {
                    audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                }
                break;

            case R.id.bDisable:
                reply = "";
                break;
        }
    }

    public void checkStates() {
        final Switch sDnd = (Switch) findViewById(R.id.sDnd);

        int ringer = audioManager.getRingerMode();

        if (prefVibrate && ringer == AudioManager.RINGER_MODE_VIBRATE) {
            sDnd.setChecked(true);
        } else if (!prefVibrate && ringer == AudioManager.RINGER_MODE_SILENT) {
            sDnd.setChecked(true);
        } else {
            sDnd.setChecked(false);
        }
    }

    public void loadPrefs() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPrefs.registerOnSharedPreferenceChangeListener(Main.this);

        prefVibrate = sharedPrefs.getBoolean("prefVibrate", false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Intent intent_pref = new Intent(Main.this, Pref.class);
            startActivity(intent_pref);
            return true;
        }

        if (item.getItemId() == R.id.action_contact) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("plain/text");
            intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"hello@mellowdev.com"});
            intent.putExtra(Intent.EXTRA_SUBJECT, "Mango App");
            startActivity(Intent.createChooser(intent, ""));
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        loadPrefs();
    }

    @Override
    public void onResume() {
        checkStates();
        loadPrefs();

        super.onResume();
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }
}
