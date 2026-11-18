package com.example.seminar5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ImagineAdapter extends BaseAdapter {

    private Context ctx;
    private int resursaLayout;
    private List<CeasImagine> lista;

    public ImagineAdapter(Context ctx, int resursaLayout, List<CeasImagine> lista) {
        this.ctx = ctx;
        this.resursaLayout = resursaLayout;
        this.lista = lista;
    }

    @Override
    public int getCount() { return lista.size(); }

    @Override
    public Object getItem(int i) { return lista.get(i); }

    @Override
    public long getItemId(int i) { return i; }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = LayoutInflater.from(ctx).inflate(resursaLayout, viewGroup, false);

        ImageView iv = v.findViewById(R.id.IV_ceas);
        TextView tv = v.findViewById(R.id.TV_descriere);

        CeasImagine item = (CeasImagine) getItem(i);
        tv.setText(item.getTextAfisat());
        iv.setImageBitmap(item.getImagine());

        return v;
    }
}