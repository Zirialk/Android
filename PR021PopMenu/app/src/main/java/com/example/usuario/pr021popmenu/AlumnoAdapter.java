package com.example.usuario.pr021popmenu;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


public class AlumnoAdapter extends ArrayAdapter<Alumno>{
    private final List<Alumno> mAlumnos;
    private final LayoutInflater mInflador;

    public AlumnoAdapter(Context context, List<Alumno> alumnos){
        super(context,R.layout.alumno,alumnos);
        mAlumnos=alumnos;
        mInflador=LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){ //Si no se puede reciclar
            convertView=mInflador.inflate(R.layout.alumno,parent,false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder)convertView.getTag();

        }
        onBindViewHolder(holder,position);

        return convertView;
    }
    private void onBindViewHolder(ViewHolder holder, int position){
        Alumno alumno = mAlumnos.get(position);
        holder.lblNombre.setText(alumno.nombre);
        holder.lblTlf.setText(alumno.tlf);

        holder.imgPopUp.setOnClickListener(new imgPopUpMenuOnClickListener(mAlumnos.get(position)));
    }
    static class ViewHolder{

        private final TextView lblNombre;
        private final TextView lblTlf;
        private final ImageView imgPopUp;

        public ViewHolder(View itemView){
            lblNombre= (TextView)itemView.findViewById(R.id.lblNombre);
            lblTlf= (TextView) itemView.findViewById(R.id.lblTlf);
            imgPopUp= (ImageView) itemView.findViewById(R.id.imgPopUp);
        }
    }
    private class imgPopUpMenuOnClickListener implements View.OnClickListener {
        private final Alumno mAlumno;

        public imgPopUpMenuOnClickListener(Alumno alumno){
            mAlumno=alumno;
        }
        @Override
        public void onClick(View v) {
            //Se crea el menú.
            PopupMenu popUp= new PopupMenu(getContext(),v);
            //Se infla el menú
            MenuInflater inflador= popUp.getMenuInflater();
            inflador.inflate(R.menu.menu_main,popUp.getMenu());
            //Se crea el listener para cuando se pulse un item del menú
            popUp.setOnMenuItemClickListener(new PopUpMenuOnMenuItemClickListener(mAlumno));
            popUp.show();

        }
    }
    private class PopUpMenuOnMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {
        final Alumno alumno;

        public PopUpMenuOnMenuItemClickListener(Alumno alumno){
            this.alumno=alumno;
        }
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()){
                case R.id.llamar:
                    Toast.makeText(getContext(),getContext().getString(R.string.llamando_A)+alumno.nombre,Toast.LENGTH_SHORT).show();
                    break;
                case R.id.enviarMensaje:
                    Toast.makeText(getContext(),getContext().getString(R.string.enviandoMensaje_A)+alumno.nombre,Toast.LENGTH_SHORT).show();
                    break;
            }
            return true;
        }
    }
}
