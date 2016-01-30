package com.example.usuario.pr037navigationdrawer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


public class TextoFragment extends Fragment {
    private static final String ARG_TEXTO = "texto";
    private String texto;
    private TextView lblTexto;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_texto,container,false);
    }

    public static TextoFragment newInstance(String texto) {

        Bundle args = new Bundle();

        TextoFragment fragment = new TextoFragment();
        args.putString(ARG_TEXTO, texto);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        lblTexto = (TextView) getActivity().findViewById(R.id.lblTexto);
        lblTexto.setText(getArguments().getString(ARG_TEXTO));
        super.onActivityCreated(savedInstanceState);
    }
    public void setTexto(String texto){
        this.texto=texto;
        lblTexto.setText(texto);
    }
    public void onFabClick(){
        Toast.makeText(getContext(),"Fab pulsado en el fragmento con texto: "+texto, Toast.LENGTH_SHORT).show();
    }

}
