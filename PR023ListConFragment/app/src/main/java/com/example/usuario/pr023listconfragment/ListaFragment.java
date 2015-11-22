package com.example.usuario.pr023listconfragment;


import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.internal.widget.ThemeUtils;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;


public class ListaFragment extends Fragment {


    private static final String ARG_ALUMNOS = "Alumnos";
    private ListView lsvAlumnos;
    private OnItemSelected listener;

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
                listener.pulsado((Alumno) parent.getItemAtPosition(position));
            }

        });
        configurarLista();
        super.onActivityCreated(savedInstanceState);
    }

    private void configurarLista() {
        lsvAlumnos.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        lsvAlumnos.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {

            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.menu_main, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });
    }
    private ArrayList<Alumno> getAlumnosSeleccionados(ListView lsvAlumnos, boolean uncheck){
        ArrayList<Alumno> alumnos = new ArrayList<>();
        SparseBooleanArray seleccionados = lsvAlumnos.getCheckedItemPositions();
        int position;

        for (int i = 0; i < seleccionados.size(); i++) {
            if(seleccionados.valueAt(i)){
                position=seleccionados.keyAt(i);

                if(uncheck)
                    lsvAlumnos.setItemChecked(position,false);

                alumnos.add((Alumno)lsvAlumnos.getItemAtPosition(seleccionados.keyAt(i)));
            }

        }



        return alumnos;
    }


    public interface OnItemSelected {
        public void pulsado(Alumno alumno);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener= (OnItemSelected) activity;

        } catch (ClassCastException e) {
            throw new ClassCastException("The interface must implements CallBack interface");
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener=null;
    }


}
