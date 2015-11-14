package com.example.usuario.pr023listconfragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class DetallesActivity extends AppCompatActivity {


    private static final String EXTRA_ALUMNO = "alumno";
    private TextView lblNombre;
    private TextView lblEdad;
    private TextView lblLocalidad;
    private ImageView imgAvatar;
    private TextView lblCalle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalles_layout);

        initView(getIntent());


    }

    private void initView(Intent intent) {
        Alumno alumno = intent.getParcelableExtra(EXTRA_ALUMNO);

        lblNombre = (TextView) findViewById(R.id.lblNombre);
        lblEdad = (TextView) findViewById(R.id.lblEdad);
        lblLocalidad = (TextView) findViewById(R.id.lblLocalidad);
        lblCalle = (TextView) findViewById(R.id.lblCalle);
        imgAvatar = (ImageView) findViewById(R.id.imgAvatar);

        lblNombre.setText(alumno.getNombre());
        lblEdad.setText(alumno.getEdad()+" años");
        lblLocalidad.setText(alumno.getLocalidad());
        lblCalle.setText(alumno.getCalle());
        Picasso.with(this).load(alumno.getAvatar()).into(imgAvatar);
    }

    public static void start(Context context,Alumno alumno){
        Intent intent= new Intent(context,DetallesActivity.class);
        intent.putExtra(EXTRA_ALUMNO,alumno);

        context.startActivity(intent);
    }
}
