package com.example.seminar5;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class WebActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        WebView wv = findViewById(R.id.WV_pagina);
        wv.setWebViewClient(new WebViewClient());

        String link = getIntent().getStringExtra("link");
        if (link != null) wv.loadUrl(link);
    }
}