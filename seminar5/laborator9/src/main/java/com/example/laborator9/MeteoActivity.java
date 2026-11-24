package com.example.laborator9;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MeteoActivity extends AppCompatActivity {

    private static final String API_KEY = "CHEIA_TA_AICI";

    private EditText etOras;
    private Spinner spZile;
    private TextView tvRezultat;

    private final Executor executor = Executors.newSingleThreadExecutor();
    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meteo);

        etOras = findViewById(R.id.ET_oras);
        spZile = findViewById(R.id.SP_zile);
        tvRezultat = findViewById(R.id.TV_rezultat);
        Button btnCauta = findViewById(R.id.BTN_cauta);




        String[] optiuni = {"1 zi", "5 zile"};
        spZile.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, optiuni));

        btnCauta.setOnClickListener(v -> {
            String oras = etOras.getText().toString().trim();
            if (oras.isEmpty()) {
                Toast.makeText(this, "Introdu un oraș", Toast.LENGTH_SHORT).show();
                return;
            }

            String metoda = spZile.getSelectedItemPosition() == 0 ? "1day" : "5day";
            cautaVremea(oras, metoda);
        });
    }

    private void cautaVremea(String oras, String metoda) {
        tvRezultat.setText("Se încarcă...");

        executor.execute(() -> {
            try {

                String linkOras = "https://dataservice.accuweather.com/locations/v1/cities/search"
                        + "?apikey=" + API_KEY + "&q=" + URLEncoder.encode(oras, "UTF-8");
                JSONArray vector = new JSONArray(descarca(linkOras));
                if (vector.length() == 0) {
                    afiseaza("Orașul nu a fost găsit.");
                    return;
                }
                JSONObject obiect = vector.getJSONObject(0);
                String cheieOras = obiect.getString("Key");


                String linkVreme = "https://dataservice.accuweather.com/forecasts/v1/daily/"
                        + metoda + "/" + cheieOras + "?apikey=" + API_KEY + "&metric=true";
                JSONObject radacina = new JSONObject(descarca(linkVreme));
                JSONArray zile = radacina.getJSONArray("DailyForecasts");

                StringBuilder sb = new StringBuilder();
                sb.append("Cheie oraș: ").append(cheieOras).append("\n\n");
                for (int i = 0; i < zile.length(); i++) {
                    JSONObject zi = zile.getJSONObject(i);
                    String data = zi.getString("Date").substring(0, 10);
                    JSONObject temp = zi.getJSONObject("Temperature");
                    double min = temp.getJSONObject("Minimum").getDouble("Value");
                    double max = temp.getJSONObject("Maximum").getDouble("Value");
                    sb.append(data).append("\n")
                            .append("  Minimă: ").append(min).append(" °C\n")
                            .append("  Maximă: ").append(max).append(" °C\n\n");
                }
                afiseaza(sb.toString());

            } catch (Exception e) {
                android.util.Log.e("METEO", "Eroare: " + e);
                afiseaza("Eroare: " + e.getMessage());
            }
        });
    }


    private String descarca(String link) throws Exception {
        HttpURLConnection conn = (HttpURLConnection) new URL(link).openConnection();
        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String linie;
            while ((linie = reader.readLine()) != null) {
                builder.append(linie);
            }
            return builder.toString();
        } finally {
            conn.disconnect();
        }
    }


    private void afiseaza(String text) {
        handler.post(() -> tvRezultat.setText(text));
    }
}