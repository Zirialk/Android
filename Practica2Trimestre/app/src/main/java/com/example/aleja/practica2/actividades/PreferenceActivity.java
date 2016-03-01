package com.example.aleja.practica2.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.example.aleja.practica2.R;
import com.example.aleja.practica2.fragmentos.PreferencesFragment;


public class PreferenceActivity extends AppCompatActivity implements PreferencesFragment.ICambiosRealizados{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferencias);
        PreferencesFragment frgPreference = new PreferencesFragment();
        frgPreference.setICambiosRealizadosListener(this);

        getFragmentManager().beginTransaction().replace(R.id.frmContenido, frgPreference).commit();
    }
    //Solo mandará un resultado satifactorio cuando han habido cambios.
    //De esta forma no se actualizará la lista de visitas cuando no sea necesario.
    @Override
    public void cambiosRealizados() {
        setResult(RESULT_OK, new Intent());
    }
}
