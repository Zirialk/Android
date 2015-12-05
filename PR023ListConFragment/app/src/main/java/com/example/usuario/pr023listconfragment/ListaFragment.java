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
import android.widget.TextView;

import java.util.ArrayList;


public class ListaFragment extends Fragment {

    public static ArrayList<Alumno> listaAlumnos = new ArrayList<>();


    private ListView lsvAlumnos;
    private OnItemSelected mListener;
    private AlumnoAdapter adaptador;
    private TextView lblNoHayAlumno;

    public static ListaFragment newInstance() {
        return new ListaFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lista,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {


        adaptador= new AlumnoAdapter(getActivity(),listaAlumnos);
        lsvAlumnos = (ListView) getView().findViewById(R.id.lsvAlumnos);
        lblNoHayAlumno = (TextView) getView().findViewById(R.id.lblNoHayAlumno);

        lsvAlumnos.setAdapter(adaptador);
        lblNoHayAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.IconAddContactoPulsado();
            }
        });
        lsvAlumnos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.ListViewItemPulsado((Alumno) parent.getItemAtPosition(position));
            }

        });
        configurarLista();
        super.onActivityCreated(savedInstanceState);
    }

    private void configurarLista() {
        lsvAlumnos.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        //Selecciona el layout que tiene que  aparecer cuando no hay ningún item en la lista.
        lsvAlumnos.setEmptyView(lblNoHayAlumno);
    }

    public interface OnItemSelected {
        void ListViewItemPulsado(Alumno alumno);
        void IconAddContactoPulsado();
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



    public void refrescarListView(){
        adaptador.notifyDataSetChanged();
    }
}
