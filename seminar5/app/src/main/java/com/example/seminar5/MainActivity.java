package com.example.seminar5;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Ceas> listaCeasuri = new ArrayList<>();

    private final ActivityResultLauncher<Intent> adaugareLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Ceas ceas = (Ceas) result.getData().getSerializableExtra("ceas");
                    if (ceas != null) listaCeasuri.add(ceas);
                }
            });

    private final ActivityResultLauncher<Intent> listaLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getData() != null) {
                    @SuppressWarnings("unchecked")
                    ArrayList<Ceas> actualizata =
                            (ArrayList<Ceas>) result.getData().getSerializableExtra("lista");
                    if (actualizata != null) listaCeasuri = actualizata;
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnAdaugare = findViewById(R.id.BTN_adaugare);
        Button btnLista = findViewById(R.id.BTN_lista);

        btnAdaugare.setOnClickListener(v ->
                adaugareLauncher.launch(new Intent(this, AdaugareActivity.class)));

        btnLista.setOnClickListener(v -> {
            Intent intent = new Intent(this, ListaActivity.class);
            intent.putExtra("lista", listaCeasuri);
            listaLauncher.launch(intent);
        });
    }
}