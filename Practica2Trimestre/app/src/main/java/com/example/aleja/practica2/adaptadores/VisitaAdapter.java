package com.example.aleja.practica2.adaptadores;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.aleja.practica2.R;
import com.example.aleja.practica2.modelos.Visita;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class VisitaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Visita> mDatos;
    OnVisitaClickListener mOnVisitaClickListener;
    private View emptyView;

    public interface OnVisitaClickListener{
        void onVisitaClick(View view, Visita visita, int position);
    }

    public VisitaAdapter(List<Visita> datos){
        mDatos=datos;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        //Se infla el item de Visita
        View visitaView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_visita, parent, false);
        final RecyclerView.ViewHolder viewHolder = new VisitaViewHolder(visitaView);

        visitaView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnVisitaClickListener != null)
                    mOnVisitaClickListener.onVisitaClick(v, mDatos.get(viewHolder.getAdapterPosition()), viewHolder.getAdapterPosition());
            }
        });
        //Se retorna el contenedor con la vista del item de alumno en su interior.
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((VisitaViewHolder)holder).onBind(mDatos.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatos.size();
    }
    //VIEWHOLDER
    static class VisitaViewHolder extends RecyclerView.ViewHolder{

        private final TextView lblFecha;
        private final TextView lblHora;
        private final TextView lblResumen;

        public VisitaViewHolder(View itemView) {
            super(itemView);
            lblFecha = (TextView) itemView.findViewById(R.id.lblFecha);
            lblHora = (TextView) itemView.findViewById(R.id.lblHora);
            lblResumen = (TextView) itemView.findViewById(R.id.lblResumen);
        }
        public void onBind(Visita visita){
            SimpleDateFormat formatHora = new SimpleDateFormat("HH:mm", Locale.getDefault());

            lblFecha.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(visita.getDia()));
            lblHora.setText( formatHora.format(visita.getHoraInicio()) + "-" + formatHora.format(visita.getHoraFin()) );
            lblResumen.setText(visita.getResumen());
        }
    }

    //CONTROL del adaptador
    public void addItem(Visita visita){
        notifyItemInserted(mDatos.size()-1);
        mDatos.add(visita);
    }
    public void removeItem(int posicion){
        mDatos.remove(posicion);
        notifyItemRemoved(posicion);
        checkIfEmpty();
    }
    public void replaceAll(List<Visita> listaVisitas){
        mDatos.clear();
        mDatos.addAll(listaVisitas);
        notifyDataSetChanged();
    }


    private void checkIfEmpty() {
        if(emptyView != null)
            emptyView.setVisibility(getItemCount() > 0 ? View.GONE : View.VISIBLE);
    }
    // Establece el listener a informar cuando se hace click sobre un elemento de la lista.
    public void setOnItemClickListener(OnVisitaClickListener listener) {
        this.mOnVisitaClickListener = listener;
    }

    // Establece la empty view para la lista.
    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
        // Muestra la empty view si  la lista está vacía.
        checkIfEmpty();
    }
}
