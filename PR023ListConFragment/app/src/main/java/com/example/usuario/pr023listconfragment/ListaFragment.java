package com.example.usuario.pr023listconfragment;


import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class ListaFragment extends Fragment {


    private ListView lsvAlumnos;
    private OnItemSelected mListener;
    private AlumnoAdapter adaptador;
    private TextView lblNoHayAlumno;
    private Menu menu;

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
        setHasOptionsMenu(true);

        adaptador= new AlumnoAdapter(getActivity(),BddAlumnos.listaAlumnos);
        lsvAlumnos = (ListView) getView().findViewById(R.id.lsvAlumnos);
        lblNoHayAlumno = (TextView) getView().findViewById(R.id.lblNoHayAlumno);

        lsvAlumnos.setAdapter(adaptador);
        lblNoHayAlumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.iconAddContactoPulsado();
            }
        });

        configurarLista();
        super.onActivityCreated(savedInstanceState);
    }

    private void configurarLista() {
        //Selecciona el layout que tiene que  aparecer cuando no hay ningún item en la lista.
        lsvAlumnos.setEmptyView(lblNoHayAlumno);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            lsvAlumnos.setChoiceMode(AbsListView.CHOICE_MODE_SINGLE);
        else{
            lsvAlumnos.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE_MODAL);
            lsvAlumnos.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
                @Override
                public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                    // Ej: 1/2
                    mode.setTitle(lsvAlumnos.getCheckedItemCount() + "/" + lsvAlumnos.getCount());
                }

                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    mode.getMenuInflater().inflate(R.menu.menu_lista, menu);
                    //Se oculta el icono de añadir.
                    menu.findItem(R.id.itemAdd).setVisible(false);
                    return true;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    if(item.getItemId()==R.id.itemBorrar)
                        eliminarAlumnosSeleccionados(getAlumnosSeleccionados());

                    return true;
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {

                }
            });
        }
        //onClick --> Muestra detalles.
        lsvAlumnos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.listViewItemPulsado((Alumno) parent.getItemAtPosition(position));
                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                    menu.findItem(R.id.itemBorrar).setVisible(true);
            }
        });
    }
    private ArrayList<Alumno> getAlumnosSeleccionados() {
        ArrayList<Alumno> datos = new ArrayList<>();
        //Se guardan los alumnos seleccionados de la lista.
        SparseBooleanArray alumnosSeleccionados = lsvAlumnos.getCheckedItemPositions();
        if(alumnosSeleccionados!=null)
        for (int i = 0; i < alumnosSeleccionados.size(); i++) {
            // Si está seleccionado.
            if (alumnosSeleccionados.valueAt(i)) {
                int position = alumnosSeleccionados.keyAt(i);
                // Se deselecciona.
                lsvAlumnos.setItemChecked(position, false);
                // Se añade al resultado.
                datos.add((Alumno) lsvAlumnos.getItemAtPosition(alumnosSeleccionados.keyAt(i)));
            }
        }
        // Se retorna el resultado.
        return datos;
    }
    private ArrayList<Integer> eliminarAlumnosSeleccionados(ArrayList<Alumno> alumnos){
        ArrayList<Integer> indicesEliminados = new ArrayList<>();
        //Elimina de la lista los elementos seleccionados.
            for(Alumno al : alumnos){
                indicesEliminados.add(BddAlumnos.listaAlumnos.indexOf(al));
                adaptador.remove(al);
            }
            //Notifica al adaptador del cambio.
            adaptador.notifyDataSetChanged();
        return indicesEliminados;
    }

    //Comunicación Fragmeno -> Actividad
    public interface OnItemSelected {
        void listViewItemPulsado(Alumno alumno);
        void iconAddContactoPulsado();
        void sinAlumnos();
    }
    //Comunicación Actividad -> Fragmento
    public void seleccionarItemChecked(int postListView){
        lsvAlumnos.setItemChecked(postListView, true);
    }

    public void deseleccionarItemsChecked(){
        for (int i = 0; i < lsvAlumnos.getCount(); i++)
            lsvAlumnos.setItemChecked(i,false);

    }

    public void refrescarListView(){
        adaptador.notifyDataSetChanged();
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        this.menu=menu;
        inflater.inflate(R.menu.menu_lista, menu);
        //Si la pantalla está en vertical, no se mostrará el item de borrar.

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        //Si no hay ningún alumno en la lista, se ocultara el icono de borrar.
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT || BddAlumnos.listaAlumnos.size()==0)
            menu.findItem(R.id.itemBorrar).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemAdd:
                AgregarContactoActivity.startForResultCreando(getActivity());
                break;
            case R.id.itemBorrar: //Este item solo aparece en horizontal.
                int eliminado=eliminarAlumnosSeleccionados(getAlumnosSeleccionados()).get(0);
                //Si quedan alumnos, selecciona otro de al lado.
                if(BddAlumnos.listaAlumnos.size()>0) {
                    if(eliminado==BddAlumnos.listaAlumnos.size()){
                        seleccionarItemChecked(eliminado-1);
                        //Actualiza el fragmento detalles, con los datos del nuevo seleccionado tras el borrado.
                        mListener.listViewItemPulsado(BddAlumnos.listaAlumnos.get(eliminado-1));
                    }
                    else{
                        seleccionarItemChecked(eliminado);
                        //Actualiza el fragmento detalles, con los datos del nuevo seleccionado tras el borrado.
                        mListener.listViewItemPulsado(BddAlumnos.listaAlumnos.get(eliminado));
                    }
                }
                //Si no quedan alumnos se notifica al listener.
                else
                    mListener.sinAlumnos();
                break;
        }
        return super.onOptionsItemSelected(item);
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
