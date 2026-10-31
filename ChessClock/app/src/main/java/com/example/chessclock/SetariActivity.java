package com.example.chessclock;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SetariActivity extends AppCompatActivity {

    private EditText etMinute, etIncrement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setari);

        etMinute = findViewById(R.id.ET_minute);
        etIncrement = findViewById(R.id.ET_increment);

        findViewById(R.id.BTN_blitz).setOnClickListener(v -> trimite(3, 2));
        findViewById(R.id.BTN_rapid).setOnClickListener(v -> trimite(10, 0));
        findViewById(R.id.BTN_clasic).setOnClickListener(v -> trimite(90, 30));

        Button btnAplica = findViewById(R.id.BTN_aplica);
        btnAplica.setOnClickListener(v -> {
            String m = etMinute.getText().toString().trim();
            String i = etIncrement.getText().toString().trim();
            if (m.isEmpty()) {
                Toast.makeText(this, "Introdu numărul de minute", Toast.LENGTH_SHORT).show();
                return;
            }
            int minute = Integer.parseInt(m);
            int increment = i.isEmpty() ? 0 : Integer.parseInt(i);
            if (minute <= 0) {
                Toast.makeText(this, "Minutele trebuie să fie pozitive", Toast.LENGTH_SHORT).show();
                return;
            }
            trimite(minute, increment);
        });
    }

    private void trimite(int minute, int incrementSecunde) {
        Intent rezultat = new Intent();
        rezultat.putExtra("minute", minute);
        rezultat.putExtra("increment", incrementSecunde);
        setResult(RESULT_OK, rezultat);
        finish();
    }
}