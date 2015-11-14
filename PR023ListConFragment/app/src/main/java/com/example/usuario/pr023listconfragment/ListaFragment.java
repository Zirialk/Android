package com.example.usuario.pr023listconfragment;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Parcelable;

import java.util.ArrayList;


public class ListaFragment extends Fragment {


    private static final String ARG_ALUMNOS = "Alumnos";

    public static ListaFragment newInstance(ArrayList<Alumno> listaAlumnos) {

        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_ALUMNOS,listaAlumnos);
        ListaFragment fragment = new ListaFragment();
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Bundle args = getArguments();
        Parcelable[] listaAlumnos= args.getParcelableArray(ARG_ALUMNOS);

        
        super.onActivityCreated(savedInstanceState);
    }
}
