package com.example.usuario.pr022fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class SecundariaActivity extends AppCompatActivity implements UnoFragment.Callback{


    private static final String EXTRA_MENSAJE = "mensaje";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secundaria);
        Intent intent= getIntent();
        String mensaje = intent.getStringExtra(EXTRA_MENSAJE);
        loadFragment(mensaje);
    }

    public static void start(Context contexto, String mensaje){
        Intent intent= new Intent(contexto,SecundariaActivity.class);
        intent.putExtra(EXTRA_MENSAJE,mensaje);

        contexto.startActivity(intent);

    }
    private void loadFragment(String mensaje){
        FragmentManager gestor;
        FragmentTransaction transaccion;

        gestor=getSupportFragmentManager();
        //Iniciando la transacción del fragmento.
        transaccion=gestor.beginTransaction();
        //Se carga el fragmento en el hueco.
        transaccion.replace(R.id.flHuecoSecundaria, UnoFragment.newInstance(mensaje));
        //Se confirma (Si no se hace, no se muestra)
        transaccion.commit();
    }

    @Override
    public void pulsado(String mensaje) {

    }
}
