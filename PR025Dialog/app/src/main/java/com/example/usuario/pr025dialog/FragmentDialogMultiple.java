package com.example.usuario.pr025dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;


public class FragmentDialogMultiple extends DialogFragment {
    FragmentDialogListener listener;
    String[] equipos;
    boolean[] equipoSeleccionados;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder =  new AlertDialog.Builder(getActivity());
        equipos = getResources().getStringArray(R.array.equipos);
        equipoSeleccionados= new boolean[equipos.length];
        builder.setTitle("Pulsador");
        builder.setIcon(R.mipmap.ic_launcher);

        builder.setMultiChoiceItems(R.array.equipos, equipoSeleccionados, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                     equipoSeleccionados[which]=isChecked;
            }
        });

        builder.setNeutralButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onMultipleItemClick(FragmentDialogMultiple.this, equipoSeleccionados);
            }
        });
        return builder.create();
    }

    public interface FragmentDialogListener {
        public void onMultipleItemClick(DialogFragment dialog, boolean[] equiposCheckeds);
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
