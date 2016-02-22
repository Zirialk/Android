package com.example.aleja.practica2.fragmentos;

import android.animation.Animator;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aleja.practica2.R;
import com.example.aleja.practica2.actividades.MainActivity;
import com.example.aleja.practica2.modelos.Alumno;
import com.squareup.picasso.Picasso;


public class EditorFragment extends Fragment {

    private static final String ARG_ALUMNO = "alumno_a_editar";
    private TextView txtEmail;
    private TextView txtNombre;
    private TextView txtTelefono;
    private TextView txtEmpresa;
    private TextView txtTutor;
    private TextView txtHorario;
    private TextView txtDireccion;
    private ImageView imgFoto;
    private Alumno alumno;
    private FloatingActionButton fab;

    public static EditorFragment newInstance(Alumno alumno) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_ALUMNO, alumno);
        EditorFragment fragment = new EditorFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_editor_alumno, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
        //Si se ha entrado a editar un alumno, se cargará sus datos en los editText.
        if(getArguments()!=null)
            if((alumno = getArguments().getParcelable(ARG_ALUMNO)) != null)
                cargarDatosAlumno();



    }

    private void initViews() {
        txtNombre = (TextView) getActivity().findViewById(R.id.txtNombre);
        txtTelefono = (TextView) getActivity().findViewById(R.id.txtTelefono);
        txtEmail = (TextView) getActivity().findViewById(R.id.txtEmail);
        txtEmpresa = (TextView) getActivity().findViewById(R.id.txtEmpresa);
        txtTutor = (TextView) getActivity().findViewById(R.id.txtTutor);
        txtHorario = (TextView) getActivity().findViewById(R.id.txtHorario);
        txtDireccion = (TextView) getActivity().findViewById(R.id.txtDireccion);
        imgFoto = (ImageView) getActivity().findViewById(R.id.imgFoto);
        configFab();
    }

    private void configFab() {
        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        float pos = imgFoto.getLayoutParams().height - fab.getY();
        //Si el fab no se encuentra ya encima de imgFoto, se moverá encima.
        if(pos != 0)
            MainActivity.translateFab(fab, 0, pos, getResources().getDrawable(R.drawable.ic_photo_camera_white_24dp));


    }

    //Rellena los EditText con los datos del alumno.
    private void cargarDatosAlumno() {
        txtNombre.setText(alumno.getNombre());
        txtTelefono.setText(alumno.getTelefono());
        txtEmail.setText(alumno.getEmail());
        txtEmpresa.setText(alumno.getEmpresa());
        txtTutor.setText(alumno.getTutor());
        txtHorario.setText(alumno.getHorario());
        txtDireccion.setText(alumno.getDireccion());
        if(alumno.getFoto().isEmpty())
            imgFoto.setImageDrawable(getResources().getDrawable(R.drawable.icon_user_default));
        else
            Picasso.with(getContext()).load(alumno.getFoto()).error(R.drawable.icon_user_default).into(imgFoto);
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }
}
