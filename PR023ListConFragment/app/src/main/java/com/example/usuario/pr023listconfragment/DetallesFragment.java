package com.example.usuario.pr023listconfragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;


public class DetallesFragment extends Fragment {

    private static final String ARG_ALUMNO = "AlumnoDetalles";
    private Alumno alumno;
    private final int NUM_ITEMS_MENU=2;

    public static DetallesFragment newInstance(Alumno alumno) {

        DetallesFragment fragment = new DetallesFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_ALUMNO,alumno);
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detalles,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        TextView lblNombre = (TextView) getView().findViewById(R.id.lblNombre);
        TextView lblEdad = (TextView) getView().findViewById(R.id.lblEdad);
        TextView lblLocalidad = (TextView) getView().findViewById(R.id.lblLocalidad);
        TextView lblCalle = (TextView) getView().findViewById(R.id.lblCalle);
        ImageView imgAvatar = (ImageView) getView().findViewById(R.id.imgAvatar);
        TextView lblTlf = (TextView) getView().findViewById(R.id.lblTlf);
        //Se recupera el alumno
        alumno = getArguments().getParcelable(ARG_ALUMNO);

        lblNombre.setText(alumno.getNombre());
        lblEdad.setText(alumno.getEdad() + " años");
        lblLocalidad.setText(alumno.getLocalidad());
        lblCalle.setText(alumno.getCalle());
        lblTlf.setText(alumno.getTlf());


        if(imgAvatar !=null) // Pregunta si puede ser null porque en fragmen_detalles(Land) no existe imgAvatar.
            //Carga en el ImageView una imagen cargada por una URL.
        Picasso.with(getActivity()).load(alumno.getAvatar()).into(imgAvatar);

        setHasOptionsMenu(true);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Se infla el menú a partir del XML
        inflater.inflate(R.menu.menu_main, menu);
        menu.removeItem(R.id.itemAdd);
        //Evita que salgan en MainActivity (Land) 2 simbolos de llamar iguales.
        if(menu.size()>=NUM_ITEMS_MENU)
            menu.removeItem(R.id.itemLlamar);


        super.onCreateOptionsMenu(menu, inflater);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemLlamar:
                Toast.makeText(getActivity(),"Llamando..",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
