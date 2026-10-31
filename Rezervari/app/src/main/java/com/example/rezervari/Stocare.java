package com.example.rezervari;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Stocare {
    private static final String FISIER = "rezervari_prefs";
    private static final String CHEIE = "lista_rezervari";

    public static void salveaza(Context context, ArrayList<Rezervare> lista) {
        SharedPreferences prefs = context.getSharedPreferences(FISIER, Context.MODE_PRIVATE);
        String json = new Gson().toJson(lista);
        prefs.edit().putString(CHEIE, json).apply();
    }

    public static ArrayList<Rezervare> incarca(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(FISIER, Context.MODE_PRIVATE);
        String json = prefs.getString(CHEIE, null);
        if (json == null) return new ArrayList<>();

        Type tip = new TypeToken<ArrayList<Rezervare>>() {}.getType();
        ArrayList<Rezervare> lista = new Gson().fromJson(json, tip);
        return lista != null ? lista : new ArrayList<>();
    }
}