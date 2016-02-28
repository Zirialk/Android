package com.example.aleja.practica2.fragmentos;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.aleja.practica2.R;
import com.example.aleja.practica2.actividades.CreadorVisitaActivity;
import com.example.aleja.practica2.actividades.MainActivity;
import com.example.aleja.practica2.adaptadores.VisitaAdapter;
import com.example.aleja.practica2.bdd.DAO;
import com.example.aleja.practica2.modelos.Alumno;
import com.example.aleja.practica2.modelos.Visita;

import java.util.ArrayList;

public class VisitasFragment extends Fragment implements VisitaAdapter.OnVisitaClickListener {
    private static final String STATE_VISITA = "stateVisitas";
    private static final String ARG_ALUMNO = "alumno";
    private ArrayList<Visita> mVisitas = new ArrayList<>();
    private IVisitasFragment mListener;
    private RecyclerView rvVisitas;
    private VisitaAdapter mAdaptador;
    private LinearLayoutManager mLayoutManager;
    private FloatingActionButton fab;

    private int idAlumno = -1;


    // Interfaz para notificación de eventos desde el fragmento.
    public interface IVisitasFragment {
        // Cuando se selecciona un Alumno.
        void onVisitaSelected(Visita visita, int position);
    }

    public static VisitasFragment newInstance(Alumno alumno) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_ALUMNO, alumno);
        VisitasFragment fragment = new VisitasFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public static VisitasFragment newInstance() {
        return new VisitasFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recycler_view, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //Si se entra sin argumentos, se pedirá la lista de próximas visitas.
        if(getArguments() == null){
            mAdaptador = new VisitaAdapter(mVisitas, true);
            mAdaptador.replaceAll(DAO.getInstance(getContext()).getAllProxVisitas());
        }
        else{
            idAlumno = ((Alumno) getArguments().getParcelable(ARG_ALUMNO)).getId();
            mAdaptador = new VisitaAdapter(mVisitas, false);
            //Si existe argumento, se pedira las visitas del alumno pasado por parámetro.
            mAdaptador.replaceAll(DAO.getInstance(getContext()).getAlumnoVisitas(idAlumno));
        }
        initViews();
    }

    private void initViews() {
        configRecyclerView();
        configFab();
    }

    private void configRecyclerView() {
        rvVisitas = (RecyclerView) getActivity().findViewById(R.id.rv);
        rvVisitas.setHasFixedSize(true);

        mAdaptador.setOnItemClickListener(this);
        rvVisitas.setAdapter(mAdaptador);
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvVisitas.setLayoutManager(mLayoutManager);
        rvVisitas.setItemAnimator(new DefaultItemAnimator());
    }

    private void configFab() {
        fab = (FloatingActionButton)getActivity().findViewById(R.id.fab);
        MainActivity.translateFab(fab, 0, 0, getResources().getDrawable(R.drawable.ic_add));

    }
    public void onFabPressed(){
        //Si se entró en las visitas especificas de un alumno.
        if(idAlumno != -1){
            Intent intent = new Intent(getContext(), CreadorVisitaActivity.class);
            intent.putExtra(CreadorVisitaActivity.INTENT_ID_ALUMNO, idAlumno);
            startActivity(intent);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (IVisitasFragment) context;
        } catch (ClassCastException e) {
            // La actividad no implementa la interfaz necesaria.
            throw new ClassCastException(context.toString() + " must implements IVisitasFragment");
        }
    }


    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(STATE_VISITA, mVisitas);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onVisitaClick(View view, Visita visita, int position) {

    }


}
