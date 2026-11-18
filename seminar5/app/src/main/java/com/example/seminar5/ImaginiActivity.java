package com.example.seminar5;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ImaginiActivity extends AppCompatActivity {

    private List<CeasImagine> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagini);

        List<String> linkuriImagini = new ArrayList<>();
        linkuriImagini.add("https://img.magnific.com/premium-psd/png-black-luxury-wrist-watch-transparent-background-ai-generated_768733-78500.jpg?semt=ais_hybrid&w=740&q=80");
        linkuriImagini.add("https://pluspng.com/img-png/watch-hd-png-watch-png-hd-png-image-1000.png");
        linkuriImagini.add("https://e7.pngegg.com/pngimages/758/778/png-clipart-pocket-watch-chronograph-watch-watch-accessory-accessories-thumbnail.png");

        linkuriImagini.add("https://png.pngtree.com/png-clipart/20190916/original/pngtree-vector-electronics-watch-illustration-png-image_4588602.jpg");
        linkuriImagini.add("https://png.pngtree.com/png-vector/20241015/ourmid/pngtree-rolex-mens-watch-isolated-png-image_14092707.png");
        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                List<Bitmap> imagini = new ArrayList<>();

                for (String link : linkuriImagini) {
                    HttpURLConnection con = null;
                    try {
                        URL url = new URL(link);
                        con = (HttpURLConnection) url.openConnection();
                        con.connect();
                        InputStream is = con.getInputStream();
                        imagini.add(BitmapFactory.decodeStream(is));
                        android.util.Log.d("IMG", "Descarcat: " + link + " -> " + imagini.get(imagini.size() - 1));
                    } catch (Exception e) {

                        android.util.Log.e("IMG", "Eroare la: " + link + " -> " + e);
                        imagini.add(null);
                    } finally {
                        if (con != null) con.disconnect();
                    }
                }




                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        lista = new ArrayList<>();
                        lista.add(new CeasImagine("Rolex Oyster Perpetual", imagini.get(0), "https://en.wikipedia.org/wiki/Watch"));
                        lista.add(new CeasImagine("Vacheron Constantin Patrimony", imagini.get(1), "https://www.catawiki.com/en/c/333-watches?utm_medium=cpc&utm_source=google&utm_content=142594036685&utm_term=watches%20website&gad_source=1&gad_campaignid=15790541196&gbraid=0AAAAADHD_vQ-v_u9bSTHalyCreopJoQOu&gclid=Cj0KCQiAtvXXBhC2ARIsAJc8SUSwHa1hWoervwdr58jmvvlp4W_NgCLLmBKz6yn1UwmIFyv-phsaY9kaAku1EALw_wcB"));
                        lista.add(new CeasImagine("Junghans Mega", imagini.get(2), "https://www.swarovski.com/ro-RO/c-02/Categorii/Ceasuri?utm_source=google&utm_medium=cpc&utm_campaign=RO_RO_P-max_Watches_Generics&utm_content=&utm_term=&utm_id=22556912157&gad_source=1&gad_campaignid=23948121780&gbraid=0AAAAADgqjmwloiaYu4gcnFDtDWsxtcCgO&gclid=Cj0KCQiAtvXXBhC2ARIsAJc8SUS1Ra85JrLAcuBKiINqZ2vRgBdtRxcfXlwTEB4eg6mnlpAhouM9rEUaAmpxEALw_wcB"));
                        lista.add(new CeasImagine("Poljot Aviator", imagini.get(3), "https://www.garmin.com/ro-RO/c/wearables-smartwatches/?gad_source=1&gad_campaignid=23954441518&gbraid=0AAAAADnByCITx9lUHcn1Rjx5UgRkLvp3-&gclid=Cj0KCQiAtvXXBhC2ARIsAJc8SUSkHZwIa3g-sORdRy7nMsiABTm5XpiHwwQC330kHj8q1GK1tYwg-hoaAgDSEALw_wcB"));
                        lista.add(new CeasImagine("Jumphour ", imagini.get(4), "https://www.thewatchpages.com/"));









                        ListView lv = findViewById(R.id.LV_imagini);
                        ImagineAdapter adapter =
                                new ImagineAdapter(getApplicationContext(), R.layout.item_imagine, lista);
                        lv.setAdapter(adapter);

                        lv.setOnItemClickListener((parent, view, position, id) -> {
                            Intent it = new Intent(getApplicationContext(), WebActivity.class);
                            it.putExtra("link", lista.get(position).getLink());
                            startActivity(it);
                        });
                    }
                });
            }
        });
    }
}