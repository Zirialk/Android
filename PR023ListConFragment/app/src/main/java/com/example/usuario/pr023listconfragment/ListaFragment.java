package com.example.usuario.pr023listconfragment;


import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class ListaFragment extends Fragment {


    private static final String ARG_ALUMNOS = "Alumnos";
    private ListView lsvAlumnos;
    private OnItemSelected mListener;

    public static ListaFragment newInstance(ArrayList<Alumno> listaAlumnos) {

        ListaFragment fragment = new ListaFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_ALUMNOS,listaAlumnos);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lista,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Bundle args = getArguments();
        ArrayList<Alumno> listaAlumnos= args.getParcelableArrayList(ARG_ALUMNOS);

        final AlumnoAdapter adaptador= new AlumnoAdapter(getActivity(),listaAlumnos);
        lsvAlumnos = (ListView) getView().findViewById(R.id.lsvAlumnos);
        lsvAlumnos.setAdapter(adaptador);
        lsvAlumnos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.pulsado((Alumno) parent.getItemAtPosition(position));
            }

        });
        configurarLista();
        super.onActivityCreated(savedInstanceState);
    }

    private void configurarLista() {
        lsvAlumnos.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);

    }

    public interface OnItemSelected {
        public void pulsado(Alumno alumno);
        public Alumno addContacto();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnItemSelected) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException("The interface must implements CallBack interface");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener =null;
    }


}
