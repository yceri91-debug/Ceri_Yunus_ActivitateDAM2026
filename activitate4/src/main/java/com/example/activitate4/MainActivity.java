package com.example.activitate4;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAdaugare = findViewById(R.id.BTN_adaugare);

        btnAdaugare.setOnClickListener(v -> {
            Intent intent = new Intent(
                    MainActivity.this,
                    Ceas.class
            );

            startActivity(intent);
        });
    }
}