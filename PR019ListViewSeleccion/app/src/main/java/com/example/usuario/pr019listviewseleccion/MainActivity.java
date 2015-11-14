package com.example.usuario.pr019listviewseleccion;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView lstColores;
    private ImageView imgCabecera;
    private ArrayAdapter<String> adaptador;
    private String[] lista ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();


    }

    private void initViews() {
        lstColores= (ListView) findViewById(R.id.lstColores);
        imgCabecera = (ImageView) findViewById(R.id.imgCabecera);
        lista=getResources().getStringArray(R.array.imagenes);

        adaptador= new ArrayAdapter<String>(this,R.layout.respuesta_layout,R.id.imgRespuesta,lista);
        lstColores.setAdapter(adaptador);
        lstColores.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        //lstColores.setOnClickListener(new );
    }


}
