package com.example.usuario.pr025dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;


public class MyFragmentDialog3 extends DialogFragment {

    MiDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder =  new AlertDialog.Builder(getActivity());
        builder.setTitle("Equipo");

        builder.setItems(R.array.equipos, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                listener.onItemClick(MyFragmentDialog3.this, getResources().getStringArray(R.array.equipos)[which]);
            }
        });
        builder.setIcon(R.mipmap.ic_launcher);

        return builder.create();
    }

    public interface MiDialogListener {
        public void onItemClick(DialogFragment dialog,String elegido);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (MiDialogListener) activity;
        } catch (ClassCastException e) {
            // La actividad no implementa la interfaz. Lanzamos una excepción.
            throw new ClassCastException(activity.toString()
                    + " debe implementar MiDialogListener");
        }


    }
}
