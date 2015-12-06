package com.example.usuario.pr023listconfragment;


import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
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

import com.squareup.picasso.Picasso;

import java.io.File;


public class DetallesFragment extends Fragment {

    private static final String ARG_ALUMNO = "AlumnoDetalles";
    private Alumno alumno;
    private final int NUM_ITEMS_MENU=3;
    private TextView lblNombre;
    private TextView lblEdad;
    private TextView lblLocalidad;
    private TextView lblTlf;
    private TextView lblCalle;
    private CambiarImg mListener;

    public static DetallesFragment newInstance(int indiceAlumno) {

        DetallesFragment fragment = new DetallesFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ALUMNO, indiceAlumno);
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
        setHasOptionsMenu(true);
        lblNombre = (TextView) getView().findViewById(R.id.lblNombre);
        lblEdad = (TextView) getView().findViewById(R.id.lblEdad);
        lblLocalidad = (TextView) getView().findViewById(R.id.lblLocalidad);
        lblTlf = (TextView) getView().findViewById(R.id.lblTlf);
        lblCalle = (TextView) getView().findViewById(R.id.lblCalle);
        //Se recupera el alumno de la lista, con el indice recuperado.
        alumno = ListaFragment.listaAlumnos.get(getArguments().getInt(ARG_ALUMNO));
        refrescarDetalles();

        super.onActivityCreated(savedInstanceState);
    }
    private void refrescarDetalles(){


        lblNombre.setText(alumno.getNombre());
        lblEdad.setText(alumno.getEdad() + " años");
        lblLocalidad.setText(alumno.getLocalidad());
        lblCalle.setText(alumno.getCalle());
        lblTlf.setText(alumno.getTlf());
        if(alumno.getAvatar()!=null){
            if(getActivity().getApplication().getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE )
                Picasso.with(getActivity()).load(new File(alumno.getAvatar())).into((ImageView) getView().findViewById(R.id.imgAvatar));
            else
                mListener.cambiarImgAvatar(alumno.getAvatar());
        }
    }
    public interface CambiarImg {
        void cambiarImgAvatar(String path);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Se infla el menú a partir del XML
        inflater.inflate(R.menu.menu_detalles, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemLlamar:
                //Llama
                Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+alumno.getTlf()));
                startActivity(intent);
                return true;
            case R.id.itemEditar:
                //Le paso el índice del alumno en el array, ya que pasarle un Parcelable no modificaría el original.
                AgregarContactoActivity.startForResultEditando(getActivity(), ListaFragment.listaAlumnos.indexOf(alumno));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    @Override
    public void onResume() {
        //Cuando vuelve del editor de Alumnos, actualiza los datos por si ha editado algún dato.
        refrescarDetalles();
        super.onResume();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener= (CambiarImg) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Debe implementar la interfaz CambiarImg");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener=null;
    }
}
