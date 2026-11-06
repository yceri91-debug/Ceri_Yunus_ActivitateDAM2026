package com.example.seminar5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

public class CeasAdapter extends BaseAdapter {

    private List<Ceas> ceasuri = null;
    private Context ctx;
    private int resursaLayout;

    public CeasAdapter(List<Ceas> ceasuri, Context ctx, int resursaLayout) {
        this.ceasuri = ceasuri;
        this.ctx = ctx;
        this.resursaLayout = resursaLayout;
    }

    @Override
    public int getCount() { return ceasuri.size(); }

    @Override
    public Object getItem(int i) { return ceasuri.get(i); }

    @Override
    public long getItemId(int i) { return i; }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View v = inflater.inflate(resursaLayout, viewGroup, false);

        TextView tvCerc = v.findViewById(R.id.TV_cerc);
        TextView tvMarca = v.findViewById(R.id.TV_marca);
        TextView tvCuloare = v.findViewById(R.id.TV_culoare);
        TextView tvPret = v.findViewById(R.id.TV_pret);

        Ceas ceas = (Ceas) getItem(i);

        String marca = ceas.getMarca();
        tvCerc.setText(marca.isEmpty() ? "?" : marca.substring(0, 1).toUpperCase());
        tvMarca.setText(marca);
        tvCuloare.setText(ceas.getCuloare());
        tvPret.setText(String.format(Locale.getDefault(), "%.2f lei", ceas.getPret()));

        return v;
    }
}