package com.example.usuario.pr023listconfragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class DetallesActivity extends AppCompatActivity {

    private static final String EXTRA_ALUMNO = "mensaje";
    FragmentManager mGestor= getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getApplication().getResources().getConfiguration().orientation== Configuration.ORIENTATION_LANDSCAPE)
            onBackPressed();

        Alumno alumno = getIntent().getParcelableExtra(EXTRA_ALUMNO);
        loadFragmentDetalles(alumno, "detalles");

    }

    public static void start(Context contexto, Alumno alumno){
        Intent intent= new Intent(contexto,DetallesActivity.class);
        intent.putExtra(EXTRA_ALUMNO,alumno);

        contexto.startActivity(intent);

    }
    private void loadFragmentDetalles(Alumno alumno,String tag){
        FragmentTransaction transaction = mGestor.beginTransaction();
        transaction.replace(R.id.flHuecoPrincipal, DetallesFragment.newInstance(alumno), tag).commit();
        //Carga la imagen de la collapseToolBar desde una URL
        Picasso.with(this).load(alumno.getAvatar()).into((ImageView)findViewById(R.id.imgAvatar));

    }


}
