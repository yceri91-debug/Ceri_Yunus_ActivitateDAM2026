package com.example.rezervari;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Rezervare> listaRezervari = new ArrayList<>();


    private final ActivityResultLauncher<Intent> adaugareLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Rezervare rezervare = (Rezervare) result.getData().getSerializableExtra("rezervare");
                    if (rezervare != null) {
                        listaRezervari.add(rezervare);
                        Stocare.salveaza(this, listaRezervari);
                    }
                }
            });


    private final ActivityResultLauncher<Intent> listaLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getData() != null) {
                    @SuppressWarnings("unchecked")
                    ArrayList<Rezervare> actualizata =
                            (ArrayList<Rezervare>) result.getData().getSerializableExtra("lista");
                    if (actualizata != null) {
                        listaRezervari = actualizata;
                        Stocare.salveaza(this, listaRezervari);
                    }
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listaRezervari = Stocare.incarca(this);

        Button btnAdaugare = findViewById(R.id.BTN_adaugare);
        Button btnLista = findViewById(R.id.BTN_lista);

        btnAdaugare.setOnClickListener(v ->
                adaugareLauncher.launch(new Intent(this, AdaugareActivity.class)));

        btnLista.setOnClickListener(v -> {
            Intent intent = new Intent(this, ListaActivity.class);
            intent.putExtra("lista", listaRezervari);
            listaLauncher.launch(intent);
        });
    }
}