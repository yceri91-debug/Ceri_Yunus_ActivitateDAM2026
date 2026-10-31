package com.example.rezervari;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class AdaugareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adaugare);

        EditText etNume = findViewById(R.id.ET_nume);
        EditText etTelefon = findViewById(R.id.ET_telefon);
        EditText etData = findViewById(R.id.ET_data);
        EditText etOra = findViewById(R.id.ET_ora);
        EditText etPersoane = findViewById(R.id.ET_persoane);
        EditText etMasa = findViewById(R.id.ET_masa);
        Button btnSalveaza = findViewById(R.id.BTN_salveaza);

        // alegerea datei
        etData.setOnClickListener(v -> {
            Calendar cal = Calendar.getInstance();
            DatePickerDialog dialog = new DatePickerDialog(this, (view, an, luna, zi) ->
                    etData.setText(zi + "/" + (luna + 1) + "/" + an),
                    cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
            // nu permite date din trecut
            dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            dialog.show();
        });

        // alegerea orei
        etOra.setOnClickListener(v -> {
            TimePickerDialog dialog = new TimePickerDialog(this, (view, ora, minut) ->
                    etOra.setText(String.format("%02d:%02d", ora, minut)),
                    18, 0, true);
            dialog.show();
        });

        btnSalveaza.setOnClickListener(v -> {
            String nume = etNume.getText().toString().trim();
            String telefon = etTelefon.getText().toString().trim();
            String data = etData.getText().toString().trim();
            String ora = etOra.getText().toString().trim();
            String persoaneText = etPersoane.getText().toString().trim();
            String masaText = etMasa.getText().toString().trim();

            if (nume.isEmpty() || telefon.isEmpty() || data.isEmpty() || ora.isEmpty()
                    || persoaneText.isEmpty() || masaText.isEmpty()) {
                Toast.makeText(this, "Completează toate câmpurile", Toast.LENGTH_SHORT).show();
                return;
            }

            int persoane = Integer.parseInt(persoaneText);
            int masa = Integer.parseInt(masaText);

            if (persoane <= 0 || masa <= 0) {
                Toast.makeText(this, "Numărul de persoane și masa trebuie să fie pozitive",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            Rezervare rezervare = new Rezervare(nume, telefon, data, ora, persoane, masa);

            Intent rezultat = new Intent();
            rezultat.putExtra("rezervare", rezervare);
            setResult(RESULT_OK, rezultat);
            finish();
        });
    }
}