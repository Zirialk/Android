package com.example.usuario.pr022fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements UnoFragment.Callback{


    private Button btnCambiar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFragment("Uno",R.id.flHueco1);
        btnCambiar = (Button) findViewById(R.id.btnCambiar);
        btnCambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(findViewById(R.id.flHueco2)==null)
                    SecundariaActivity.start(MainActivity.this,"Texto Cambiado");
                else
                    loadFragment("Texto Cambiado", R.id.flHueco2);
            }
        });
    }
    private void loadFragment(String mensaje,int idHueco){
        FragmentManager gestor;
        FragmentTransaction transaccion;

        gestor=getSupportFragmentManager();
        //Iniciando la transacción del fragmento.
        transaccion=gestor.beginTransaction();
        //Se carga el fragmento en el hueco.
        transaccion.replace(idHueco, UnoFragment.newInstance(mensaje));
        //Se confirma (Si no se hace, no se muestra)
        transaccion.commit();
    }


    @Override
    public void pulsado(String mensaje) {
        if(findViewById(R.id.flHueco2)==null)
            SecundariaActivity.start(MainActivity.this,mensaje);
        else
            loadFragment(mensaje, R.id.flHueco2);
    }
}
