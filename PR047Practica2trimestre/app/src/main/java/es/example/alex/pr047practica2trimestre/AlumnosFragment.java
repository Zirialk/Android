package es.example.alex.pr047practica2trimestre;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


public class AlumnosFragment extends Fragment implements AlumnoAdapter.OnItemClickListener{

    private OnAlumnoSelectedListener mListener;
    private RecyclerView rvAlumnos;
    private AlumnoAdapter mAdaptador;
    private LinearLayoutManager mLayoutManager;



    // Interfaz para notificación de eventos desde el fragmento.
    public interface OnAlumnoSelectedListener {
        // Cuando se selecciona un Alumno.
        void onAlumnoSelected(Alumno obra, int position);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alumnos, container, false);
    }

    @Override
    public void onAttach(Context context) {
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
    }

    private void initViews() {
        configRecyclerView();
    }
    private void configRecyclerView() {
        rvAlumnos = (RecyclerView) getActivity().findViewById(R.id.rvAlumnos);
        rvAlumnos.setHasFixedSize(true);
        mAdaptador = new AlumnoAdapter(new ArrayList<Alumno>());
        mAdaptador.setOnItemClickListener(this);
        rvAlumnos.setAdapter(mAdaptador);
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvAlumnos.setLayoutManager(mLayoutManager);
        rvAlumnos.setItemAnimator(new DefaultItemAnimator());
    }
    //Click en un item del RecyclerView.
    @Override
    public void onItemClick(View view, Alumno alumno, int position) {
        //La actividad que contenga este fragmento, se encargará de que hacer con el alumno seleccionado.
        mListener.onAlumnoSelected(alumno,position);
    }
}
