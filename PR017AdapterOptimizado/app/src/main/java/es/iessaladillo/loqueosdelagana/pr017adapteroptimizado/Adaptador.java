package es.iessaladillo.loqueosdelagana.pr017adapteroptimizado;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class Adaptador extends ArrayAdapter<Alumno> {
    ArrayList<Alumno> datos;

    public Adaptador(Context contexto, ArrayList<Alumno> datos) {
        super(contexto,R.layout.fila_alumno, datos);
        this.datos=datos;
    }

    public static class ViewHolder{
        private TextView nombre;
        private TextView edad;

        public ViewHolder(View itemView) {
            nombre= (TextView)itemView.findViewById(R.id.lblNombre);
            edad= (TextView)itemView.findViewById(R.id.lblEdad);
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if(convertView == null){ //Si no se necesita reciclar
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fila_alumno,parent,false);

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
        Alumno alumno= datos.get(position);
        viewHolder.nombre.setText(alumno.getNombre());
        viewHolder.edad.setText(alumno.getEdad());
        if(alumno.getEdad()>18)
            viewHolder.edad.setTextColor(Color.BLACK);
        else
            viewHolder.edad.setTextColor(Color.RED);
    }


}
