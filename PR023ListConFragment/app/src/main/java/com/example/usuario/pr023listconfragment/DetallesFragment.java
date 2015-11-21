package com.example.usuario.pr023listconfragment;

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


public class DetallesFragment extends Fragment {

    private static final String ARG_ALUMNO = "AlumnoDetalles";
    //Se guardan las Views por si en un futuro se añade la funcionalidad de editar contacto.
    private TextView lblNombre;
    private TextView lblEdad;
    private TextView lblLocalidad;
    private TextView lblCalle;
    private ImageView imgAvatar;
    private Alumno alumno;

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
        alumno = getArguments().getParcelable(ARG_ALUMNO);

        lblNombre = (TextView) getView().findViewById(R.id.lblNombre);
        lblEdad = (TextView) getView().findViewById(R.id.lblEdad);
        lblLocalidad = (TextView) getView().findViewById(R.id.lblLocalidad);
        lblCalle = (TextView) getView().findViewById(R.id.lblCalle);
        imgAvatar = (ImageView) getView().findViewById(R.id.imgAvatar);

        lblNombre.setText(alumno.getNombre());
        lblEdad.setText(alumno.getEdad()+" años");
        lblLocalidad.setText(alumno.getLocalidad());
        lblCalle.setText(alumno.getCalle());
        //Carga en el ImageView una imagen cargada por una URL.
        Picasso.with(getActivity()).load(alumno.getAvatar()).into(imgAvatar);

        setHasOptionsMenu(true);
        super.onActivityCreated(savedInstanceState);
    }
    //EJERCICIO DE CLASE DE ITEMS DE MENU DESDE FRAGMENTS
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Se infla el menú a partir del XML
        menu.removeItem(R.id.itemLlamar);
        inflater.inflate(R.menu.menu_main, menu);

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

    public Alumno getAlumno(){
        return alumno;
    }

}
