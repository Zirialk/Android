package com.example.usuario.pr023listconfragment;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

public class AgregarContactoActivity extends AppCompatActivity {


    private EditText txtNombre;
    private EditText txtLocalidad;
    private EditText txtCalle;
    private EditText txtTelefono;

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
        txtTelefono = (EditText) findViewById(R.id.txtTlf);
    }

    public static void startForResult(Activity activity, int requestCode){
        Intent intent = new Intent(activity,AgregarContactoActivity.class);

        activity.startActivityForResult(intent,requestCode);

    }
}
