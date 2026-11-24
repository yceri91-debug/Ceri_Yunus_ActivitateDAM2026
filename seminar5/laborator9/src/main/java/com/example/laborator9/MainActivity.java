package com.example.laborator9;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Button btnMeteo = findViewById(R.id.BTN_meteo);
        btnMeteo.setOnClickListener(v ->
                startActivity(new Intent(this, MeteoActivity.class)));
    }
}