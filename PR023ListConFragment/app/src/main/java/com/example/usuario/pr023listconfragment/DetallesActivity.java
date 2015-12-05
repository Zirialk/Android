package com.example.usuario.pr023listconfragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class DetallesActivity extends AppCompatActivity implements DetallesFragment.CambiarImg {

    private static final String EXTRA_ALUMNO = "alumnoo";
    FragmentManager mGestor= getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ArrayList<Alumno> alumnos = ListaFragment.listaAlumnos;
        //Cuando giras la pantalla en esta actividad, vuelve al MainActivity doblado.
        if(getApplication().getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE)
            onBackPressed();

        Alumno alumno = ListaFragment.listaAlumnos.get( getIntent().getIntExtra(EXTRA_ALUMNO,-1));

        loadFragmentDetalles(alumno, "detalles");
    }

    public static void start(Context contexto, int indiceAlumno){
        Intent intent= new Intent(contexto,DetallesActivity.class);

        intent.putExtra(EXTRA_ALUMNO, indiceAlumno);
        contexto.startActivity(intent);
    }
    private void loadFragmentDetalles(Alumno alumno,String tag){
        FragmentTransaction transaction = mGestor.beginTransaction();
        transaction.replace(R.id.flHuecoPrincipal, DetallesFragment.newInstance(ListaFragment.listaAlumnos.indexOf(alumno)), tag).commit();
    }


    @Override
    public void cambiarImgAvatar(String path) {
        //Carga la imagen en la CollapsedToolbar
        Picasso.with(this).load(new File(path)).into((ImageView) findViewById(R.id.imgAvatar));

    }
}
