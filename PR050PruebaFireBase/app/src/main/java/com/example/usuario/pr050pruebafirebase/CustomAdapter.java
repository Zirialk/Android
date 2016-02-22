package com.example.usuario.pr050pruebafirebase;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseRecyclerAdapter;

/**
 * Created by Usuario on 11/02/2016.
 */
public class CustomAdapter extends FirebaseRecyclerAdapter<Mensaje, CustomAdapter.MensajeViewHolder> {


    public interface OnItemClickListener{

        void onItemClick(View view, Mensaje mensaje, String key, int position);
    }
    private OnItemClickListener mOnItemClickListener;

    public CustomAdapter(Firebase ref) {
        super(Mensaje.class, R.layout.item_mensaje, MensajeViewHolder.class, ref);
    }

    @Override
    public MensajeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Se infla la especificación XML.
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mensaje, parent, false);
        // Se crea el contenedor de vistas.
        final MensajeViewHolder viewHolder = new MensajeViewHolder(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickListener != null){
                    //Editar mensaje
                }
            }
        });
        return viewHolder;
    }

    @Override
    protected void populateViewHolder(MensajeViewHolder mensajeViewHolder, Mensaje mensaje, int i) {
        mensajeViewHolder.titulo.setText(mensaje.getTitulo());
        mensajeViewHolder.mensaje.setText(mensaje.getMensaje());
    }

    public static class MensajeViewHolder extends RecyclerView.ViewHolder{
        TextView titulo;
        TextView mensaje;
        public MensajeViewHolder(View itemView) {
            super(itemView);
            titulo = (TextView) itemView.findViewById(R.id.lblTitulo);
            mensaje = (TextView) itemView.findViewById(R.id.lblMensaje);
        }
    }
}
