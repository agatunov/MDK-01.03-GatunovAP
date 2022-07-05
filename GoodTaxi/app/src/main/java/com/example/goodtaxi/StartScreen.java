package com.example.goodtaxi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

public class StartScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
    }

    public void profileButtonOnClick(View view) {
        Intent i = new Intent(StartScreen.this, ProfileActivity.class);
        startActivity(i);
    }

    public void historyButtonOnClick(View view) {
        Intent i = new Intent(StartScreen.this, History.class);
        startActivity(i);
    }

    public void settingButtonOnClick(View view) {
        Intent i = new Intent(StartScreen.this, SettingsActivity.class);
        startActivity(i);
    }
}