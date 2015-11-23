package com.example.usuario.pr023listconfragment;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

public class AgregarContactoActivity extends AppCompatActivity {


    private EditText txtNombre;
    private EditText txtLocalidad;
    private EditText txtCalle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_contacto);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        initViews();
    }

    private void initViews() {
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtLocalidad = (EditText) findViewById(R.id.txtLocalidad);
        txtCalle = (EditText) findViewById(R.id.txtCalle);
    }
}
