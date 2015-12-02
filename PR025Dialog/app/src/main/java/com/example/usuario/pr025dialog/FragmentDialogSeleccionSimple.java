package com.example.usuario.pr025dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

public class FragmentDialogSeleccionSimple extends DialogFragment{
    FragmentDialogListener listener;
    String[] equipos;
    String equipoSeleccionado;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder =  new AlertDialog.Builder(getActivity());
        equipos = getResources().getStringArray(R.array.equipos);
        equipoSeleccionado = equipos[0];
        builder.setTitle("Pulsador");
        builder.setIcon(R.mipmap.ic_launcher);

        builder.setSingleChoiceItems(R.array.equipos, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                equipoSeleccionado = equipos[which];
            }
        });

        builder.setNeutralButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onItemClick(FragmentDialogSeleccionSimple.this,equipoSeleccionado );
            }
        });
        return builder.create();
    }

    public interface FragmentDialogListener {
        public void onItemClick(DialogFragment dialog, String elegido);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (FragmentDialogListener) activity;
        } catch (ClassCastException e) {
            // La actividad no implementa la interfaz. Lanzamos una excepción.
            throw new ClassCastException(activity.toString()
                    + " debe implementar MiDialogListener");
        }


    }
}
