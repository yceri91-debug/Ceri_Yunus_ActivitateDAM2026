package com.example.seminar5;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListaActivity extends AppCompatActivity {

    private ArrayList<Ceas> lista;
    private ArrayAdapter<Ceas> adapter;

    private final ActivityResultLauncher<Intent> editareLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Ceas ceasModificat = (Ceas) result.getData().getSerializableExtra("ceas");
                    int pozitie = result.getData().getIntExtra("pozitie", -1);
                    if (ceasModificat != null && pozitie != -1 && pozitie < lista.size()) {
                        lista.set(pozitie, ceasModificat);
                        adapter.notifyDataSetChanged();
                        trimiteListaInapoi();
                    }
                }
            });

    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        lista = (ArrayList<Ceas>) getIntent().getSerializableExtra("lista");
        if (lista == null) lista = new ArrayList<>();

        ListView listView = findViewById(R.id.LV_ceasuri);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Ceas ceas = lista.get(position);
            Intent intent = new Intent(this, AdaugareActivity.class);
            intent.putExtra("ceas", ceas);
            intent.putExtra("pozitie", position);
            editareLauncher.launch(intent);
        });

        listView.setOnItemLongClickListener((parent, view, position, id) -> {
            lista.remove(position);
            adapter.notifyDataSetChanged();
            trimiteListaInapoi();
            return true;
        });

        trimiteListaInapoi();
    }

    private void trimiteListaInapoi() {
        Intent rezultat = new Intent();
        rezultat.putExtra("lista", lista);
        setResult(RESULT_OK, rezultat);

        int test = 0;
    }
}
