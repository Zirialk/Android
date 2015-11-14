package com.example.usuario.pr019listviewseleccion;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;

import java.util.ArrayList;


public class Adaptador extends ArrayAdapter<Respuesta> {
    ArrayList<Respuesta> datos;

    public Adaptador(Context context, ArrayList<Respuesta> datos) {
        super(context,R.layout.respuesta_layout, datos);
        this.datos=datos;
    }

    public static class viewHolder{

        public viewHolder(View){

        }
    }
}
