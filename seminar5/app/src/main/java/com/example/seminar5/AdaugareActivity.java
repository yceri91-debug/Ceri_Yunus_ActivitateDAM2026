package com.example.seminar5;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdaugareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adaugare);

        EditText etMarca = findViewById(R.id.ET_marca);
        EditText etCuloare = findViewById(R.id.ET_culoare);
        EditText etPret = findViewById(R.id.ET_pret);
        Button btnSalveaza = findViewById(R.id.BTN_salveaza);

        Ceas ceasDeEditat = (Ceas) getIntent().getSerializableExtra("ceas");
        int pozitie = getIntent().getIntExtra("pozitie", -1);

        if (ceasDeEditat != null) {
            etMarca.setText(ceasDeEditat.getMarca());
            etCuloare.setText(ceasDeEditat.getCuloare());
            etPret.setText(String.valueOf(ceasDeEditat.getPret()));
        }

        btnSalveaza.setOnClickListener(v -> {
            String marca = etMarca.getText().toString().trim();
            String culoare = etCuloare.getText().toString().trim();
            String pretText = etPret.getText().toString().trim();

            if (marca.isEmpty() || culoare.isEmpty() || pretText.isEmpty()) {
                Toast.makeText(this, "Completează toate câmpurile", Toast.LENGTH_SHORT).show();
                return;
            }

            Ceas ceas = new Ceas(marca, culoare, Float.parseFloat(pretText));

            Intent rezultat = new Intent();
            rezultat.putExtra("ceas", ceas);
            if (pozitie != -1) {
                rezultat.putExtra("pozitie", pozitie);
            }
            setResult(RESULT_OK, rezultat);
            finish();

            int test = 0;
        });
    }
}
