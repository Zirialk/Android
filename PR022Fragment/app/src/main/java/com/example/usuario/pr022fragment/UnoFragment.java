package com.example.usuario.pr022fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UnoFragment extends Fragment {

    private static final String ARG_MENSAJE = "mensaje";
    private Callback listener;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_uno,container,false);
    }

    public static UnoFragment newInstance(String mensaje) {
        Bundle argumentos= new Bundle();
        UnoFragment fragment= new UnoFragment();

        argumentos.putString(ARG_MENSAJE,mensaje);

        fragment.setArguments(argumentos);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Bundle argumentos = getArguments();
        String mensaje = argumentos.getString(ARG_MENSAJE);

        //Se llama al getView(), porque el propio fragmento no tiene el método de findViewById().
        TextView lblMensaje= (TextView) getView().findViewById(R.id.lblMensaje);
        lblMensaje.setText(mensaje);

        Button btnEnviar = (Button) getView().findViewById(R.id.btnEnviar);
        final EditText txtMensaje = (EditText) getView().findViewById(R.id.txtMensaje);
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.pulsado(txtMensaje.getText().toString());
            }
        });
        super.onActivityCreated(savedInstanceState);
    }

    public interface Callback {
        public void pulsado(String mensaje);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener= (Callback) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("La actividad debe implementar la interfaz Callback");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener=null;
    }
}
