package com.example.usuario.pr027hilosleep;

import android.app.Activity;
import android.content.Context;
import android.widget.TextView;

/**
 * Created by Usuario on 11/01/2016.
 */
public class HiloSecundario implements Runnable {


    private Activity contexto;

    TextView t;

    public HiloSecundario(Activity contexto){

        this.contexto = contexto;
    }
    @Override
    public void run() {
        t = (TextView) contexto.findViewById(R.id.texto);
        t.setText("Hola jeje");
    }
}
