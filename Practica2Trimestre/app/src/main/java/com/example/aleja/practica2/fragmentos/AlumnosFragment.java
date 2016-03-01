package com.example.aleja.practica2.fragmentos;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aleja.practica2.actividades.MainActivity;
import com.example.aleja.practica2.modelos.Alumno;
import com.example.aleja.practica2.adaptadores.AlumnoAdapter;
import com.example.aleja.practica2.bdd.DAO;
import com.example.aleja.practica2.R;

import java.util.ArrayList;


public class AlumnosFragment extends Fragment implements AlumnoAdapter.IAlumnoAdapter {

    private static final String STATE_ALUMNOS = "stateAlumnos";
    private ArrayList<Alumno> mAlumnos = new ArrayList<>();
    private OnAlumnoSelectedListener mListener;
    private RecyclerView rvAlumnos;
    private AlumnoAdapter mAdaptador;
    private LinearLayoutManager mLayoutManager;
    private FloatingActionButton fab;


    // Interfaz para notificación de eventos desde el fragmento.
    public interface OnAlumnoSelectedListener {
        // Cuando se selecciona un Alumno.
        void onAlumnoSelected(Alumno alumno);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_recycler_view, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
        //Solo consultará la BDD al cargar el fragmento si no se había cargado de antes.
        //Evita que se consulte cada vez que se gira la pantalla.
        if(savedInstanceState == null)
            mAdaptador.replaceAll(DAO.getInstance(getContext()).getAllAlumnos());
        else
            //Recuperará los alumnos de la antigua vez que cargó el fragmento.
            mAdaptador.replaceAll(savedInstanceState.<Alumno>getParcelableArrayList(STATE_ALUMNOS));

    }

    private void initViews() {
        configRecyclerView();
        configSwipeToDismiss();
    }

    private void configRecyclerView() {
        rvAlumnos = (RecyclerView) getActivity().findViewById(R.id.rv);
        rvAlumnos.setHasFixedSize(true);
        mAdaptador = new AlumnoAdapter(mAlumnos);
        mAdaptador.setiAlumnoAdapter(this);
        rvAlumnos.setAdapter(mAdaptador);
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvAlumnos.setLayoutManager(mLayoutManager);
        rvAlumnos.setItemAnimator(new DefaultItemAnimator());
    }

    private void configSwipeToDismiss() {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(
                        ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                        ItemTouchHelper.RIGHT) {

                    // Cuando se detecta un gesto drag & drop.
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {

                        return false;
                    }

                    // Cuando se detecta un gesto swipe to dismiss.
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        // Se elimina el elemento.
                        mAdaptador.removeItem(viewHolder.getAdapterPosition());
                    }
                });
        // Se enlaza con el RecyclerView.
        itemTouchHelper.attachToRecyclerView(rvAlumnos);
    }


    //Click en un item del RecyclerView.
    @Override
    public void onItemClick(View view, Alumno alumno, int position) {
        //La actividad que contenga este fragmento, se encargará de que hacer con el alumno seleccionado.
        mListener.onAlumnoSelected(alumno);
    }

    @Override
    public void onDeleteAlumno(Alumno alumno) {
        DAO.getInstance(getContext()).deleteAlumno(alumno.getId());
    }

    @Override
    public void onAttach(Context context) {
        //Solo volvera el botón a su punto inicial cuando sea ligado, no cuando un fragmento superior a este ejecute el onBackPressed().
        fab = (FloatingActionButton)getActivity().findViewById(R.id.fab);
        MainActivity.translateFab(fab, 0, 0, R.drawable.ic_add);
        super.onAttach(context);
        try {
            mListener = (OnAlumnoSelectedListener) context;
        } catch (ClassCastException e) {
            // La actividad no implementa la interfaz necesaria.
            throw new ClassCastException(context.toString() + " must implements OnAlumnoSeleccionadoListener");
        }
    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(STATE_ALUMNOS, mAlumnos);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
}
