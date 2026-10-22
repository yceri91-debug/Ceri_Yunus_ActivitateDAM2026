package com.example.seminar2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Activitate3 extends AppCompatActivity {

    private int nr1;
    private int nr2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_activitate3);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent it = getIntent();
        Bundle bundle = it.getExtras();

        String mesaj = bundle.getString("mesaj");
        this.nr1 = bundle.getInt("numar1", 0);
        this.nr2 = bundle.getInt("numar2");

        StringBuilder builder = new StringBuilder(mesaj);
        builder.append(" ").append(nr1).append(" ").append(nr2);

        Toast.makeText(this, builder.toString(), Toast.LENGTH_LONG).show();
    }

    public void intoarceRezultat(View view) {
        int suma = this.nr1 + this.nr2;
        String mesaj = "Un alt mesaj";

        Intent intent = new Intent();
        intent.putExtra("mesaj", mesaj);
        intent.putExtra("suma", suma);

        setResult(RESULT_OK, intent);
        finish();
    }
}
