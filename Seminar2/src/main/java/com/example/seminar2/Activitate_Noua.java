package com.example.seminar2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Activitate_Noua extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_activitate_noua);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    void openActivity() {
        Intent intent = new Intent(getApplicationContext(), Activitate3.class);
        Bundle bundle = new Bundle();
        bundle.putInt("numar1", 6);
        bundle.putInt("numar2", 8);
        bundle.putString("mesaj", "Mesaj transmis din prima activitate");
        intent.putExtras(bundle);
        startActivityForResult(intent, 37);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 37) {
            if(resultCode == RESULT_OK) {
                String mesaj = data.getStringExtra("mesaj");
                int suma = data.getIntExtra("suma", 0);

                Toast.makeText(this, mesaj + ' ' + suma, Toast.LENGTH_LONG).show();
            }
        }
    }

    public void deschideActivitate(View view) {
        openActivity();
    }
}
