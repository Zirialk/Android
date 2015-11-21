package com.example.usuario.pr018listviewsimple;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class Adaptador extends ArrayAdapter<Alumno> {

    ArrayList<Alumno> alumnos;
    public Adaptador(Context context, ArrayList<Alumno> alumnos) {
        super(context, R.layout.item_alumno ,alumnos);
        this.alumnos=alumnos;
    }

    public static class ViewHolder{
        private TextView nombre;

        public ViewHolder(View itemView) {
            nombre= (TextView)itemView.findViewById(R.id.lblNombre);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null){ //Si no se necesita reciclar
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_alumno,parent,false);

            //Añade a la "mochila" del convertView los IDs de los Labels.
            viewHolder= new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder) convertView.getTag();
        }
        onBindViewHolder(viewHolder,position);

        return convertView;
    }

    private void onBindViewHolder(ViewHolder viewHolder, int position) {
        Alumno alumno= alumnos.get(position);
        viewHolder.nombre.setText(alumno.nombre);

    }
}
