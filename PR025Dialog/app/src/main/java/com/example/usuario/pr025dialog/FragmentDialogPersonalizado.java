package com.example.usuario.pr025dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;


public class FragmentDialogPersonalizado extends DialogFragment {

    PersonalizadoListener listener;
    View vista;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater= getActivity().getLayoutInflater();
        vista = inflater.inflate(R.layout.cita,null);
        builder.setView(vista);
        builder.setTitle("Cita");
        builder.setMessage("Escriba aqui");
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setNeutralButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onPersonalizadoButtonClick(FragmentDialogPersonalizado.this, ((EditText)vista.findViewById(R.id.txtCita)).getText().toString());
            }
        });

        return builder.create();
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public interface PersonalizadoListener {
        public void onPersonalizadoButtonClick(DialogFragment dialog, String texto);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (PersonalizadoListener) activity;
        } catch (ClassCastException e) {
            // La actividad no implementa la interfaz. Lanzamos una excepción.
            throw new ClassCastException(activity.toString()
                    + " debe implementar MiDialogListener");
        }


    }
}
