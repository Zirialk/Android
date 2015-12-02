package com.example.usuario.pr023listconfragment;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class AlumnoAdapter extends ArrayAdapter<Alumno>{

    private ArrayList<Alumno> mAlumnos;
    private LayoutInflater mInflador;

    public AlumnoAdapter(Context context, ArrayList<Alumno> alumnos){
        super(context,R.layout.item_alumno,alumnos);
        mAlumnos=alumnos;
        mInflador= LayoutInflater.from(context);
    }

    static class ViewHolder{
        private TextView lblNombre;
        private TextView lblEdad;
        private TextView lblLocalidad;
        private CircleImageView imgAvatar;

        public ViewHolder(View itemView){
            lblNombre = (TextView) itemView.findViewById(R.id.lblNombre);
            lblEdad = (TextView) itemView.findViewById(R.id.lblEdad);
            lblLocalidad = (TextView) itemView.findViewById(R.id.lblLocalidad);
            imgAvatar = (CircleImageView) itemView.findViewById(R.id.imgAvatar);
        }
    }

    private void onBindViewHolder(ViewHolder holder, int position) {
        Alumno alumno = mAlumnos.get(position);

        holder.lblNombre.setText(alumno.getNombre());
        if(holder.lblEdad!=null) {
            holder.lblEdad.setText(alumno.getEdad() + " años");
            holder.lblLocalidad.setText(alumno.getLocalidad());
        }
        //Obtiene la imagen desde el archivo creado.
        if(alumno.getAvatar()!=null)
            Picasso.with(getContext()).load(new File(alumno.getAvatar())).into(holder.imgAvatar);
        else
            holder.imgAvatar.setImageDrawable(getContext().getResources().getDrawable(R.drawable.icon_user_default));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){ //Si no se puede reciclar el convertView porque aun no existe.
            convertView= mInflador.inflate(R.layout.item_alumno,parent,false);
            holder= new ViewHolder(convertView);
            convertView.setTag(holder);
        }else
            holder=(ViewHolder) convertView.getTag();

        onBindViewHolder(holder, position);
        return convertView;
    }


}
