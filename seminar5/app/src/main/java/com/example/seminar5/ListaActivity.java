package com.example.seminar5;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class ListaActivity extends AppCompatActivity {

    private ArrayList<Ceas> lista;

    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        lista = (ArrayList<Ceas>) getIntent().getSerializableExtra("lista");
        if (lista == null) lista = new ArrayList<>();

        ListView listView = findViewById(R.id.LV_ceasuri);

        ArrayAdapter<Ceas> adapter =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lista);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) ->
                Toast.makeText(this, lista.get(position).toString(), Toast.LENGTH_SHORT).show());

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
    }
}