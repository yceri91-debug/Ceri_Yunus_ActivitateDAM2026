package com.example.chessclock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private enum Stare { NEINCEPUT, PORNIT, PAUZA, TERMINAT }

    private Stare stare = Stare.NEINCEPUT;

    private long timpInitial = 10 * 60 * 1000L;
    private long increment = 0;
    private final long[] timpRamas = new long[2];
    private final int[] mutari = new int[2];

    private int activ = -1;        // jucatorul al carui timp curge (0 = jos, 1 = sus)
    private int pierzator = -1;
    private long ultimTick;

    private final FrameLayout[] panouri = new FrameLayout[2];
    private final TextView[] timpuri = new TextView[2];
    private Button btnPauza;

    private final Handler handler = new Handler(Looper.getMainLooper());
    private final Runnable tick = new Runnable() {
        @Override
        public void run() {
            long acum = SystemClock.elapsedRealtime();
            timpRamas[activ] -= (acum - ultimTick);
            ultimTick = acum;

            if (timpRamas[activ] <= 0) {
                timpRamas[activ] = 0;
                pierzator = activ;
                stare = Stare.TERMINAT;
                actualizeazaUI();
                return;
            }
            actualizeazaUI();
            handler.postDelayed(this, 50);
        }
    };

    // primeste setarile din SetariActivity
    private final ActivityResultLauncher<Intent> setariLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    int minute = result.getData().getIntExtra("minute", 10);
                    int inc = result.getData().getIntExtra("increment", 0);
                    timpInitial = minute * 60 * 1000L;
                    increment = inc * 1000L;
                    getSharedPreferences("chess", MODE_PRIVATE).edit()
                            .putLong("timp", timpInitial)
                            .putLong("inc", increment).apply();
                    reseteaza();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        SharedPreferences prefs = getSharedPreferences("chess", MODE_PRIVATE);
        timpInitial = prefs.getLong("timp", 10 * 60 * 1000L);
        increment = prefs.getLong("inc", 0);

        panouri[0] = findViewById(R.id.FL_jucator1);
        panouri[1] = findViewById(R.id.FL_jucator2);
        timpuri[0] = findViewById(R.id.TV_timp1);
        timpuri[1] = findViewById(R.id.TV_timp2);
        Button btnReset = findViewById(R.id.BTN_reset);
        btnPauza = findViewById(R.id.BTN_pauza);

        panouri[0].setOnClickListener(v -> apasat(0));
        panouri[1].setOnClickListener(v -> apasat(1));
        btnReset.setOnClickListener(v -> reseteaza());
        btnPauza.setOnClickListener(v -> comutaPauza());

        findViewById(R.id.BTN_setari).setOnClickListener(v -> {
            if (stare == Stare.PORNIT) comutaPauza();
            setariLauncher.launch(new Intent(this, SetariActivity.class));
        });

        reseteaza();
    }

    // Apasarea pe panoul tau inseamna "mi-am terminat mutarea"
    private void apasat(int jucator) {
        if (stare == Stare.NEINCEPUT) {
            // prima apasare porneste ceasul adversarului
            activ = 1 - jucator;
            stare = Stare.PORNIT;
            ultimTick = SystemClock.elapsedRealtime();
            handler.post(tick);
        } else if (stare == Stare.PORNIT && jucator == activ) {
            timpRamas[jucator] += increment;
            mutari[jucator]++;
            activ = 1 - activ;
            ultimTick = SystemClock.elapsedRealtime();
        }
        actualizeazaUI();
    }

    private void comutaPauza() {
        if (stare == Stare.PORNIT) {
            handler.removeCallbacks(tick);
            stare = Stare.PAUZA;
        } else if (stare == Stare.PAUZA) {
            stare = Stare.PORNIT;
            ultimTick = SystemClock.elapsedRealtime();
            handler.post(tick);
        }
        actualizeazaUI();
    }

    private void reseteaza() {
        handler.removeCallbacks(tick);
        timpRamas[0] = timpInitial;
        timpRamas[1] = timpInitial;
        mutari[0] = 0;
        mutari[1] = 0;
        activ = -1;
        pierzator = -1;
        stare = Stare.NEINCEPUT;
        actualizeazaUI();
    }

    private void actualizeazaUI() {
        for (int i = 0; i < 2; i++) {
            timpuri[i].setText(formateaza(timpRamas[i]));

            if (stare == Stare.TERMINAT && i == pierzator) {
                panouri[i].setBackgroundResource(R.drawable.bg_panel_lost);
            } else if (stare == Stare.PORNIT && i == activ) {
                panouri[i].setBackgroundResource(R.drawable.bg_panel_active);
            } else {
                panouri[i].setBackgroundResource(R.drawable.bg_panel_idle);
            }
        }
        btnPauza.setText(stare == Stare.PAUZA ? "Continuă" : "Pauză");
    }

    private String formateaza(long ms) {
        long secundeTotal = (ms + 999) / 1000; // rotunjire in sus
        return String.format(Locale.getDefault(), "%02d:%02d",
                secundeTotal / 60, secundeTotal % 60);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // daca utilizatorul iese din aplicatie, ceasul intra in pauza
        if (stare == Stare.PORNIT) {
            handler.removeCallbacks(tick);
            stare = Stare.PAUZA;
            actualizeazaUI();
        }
    }
}