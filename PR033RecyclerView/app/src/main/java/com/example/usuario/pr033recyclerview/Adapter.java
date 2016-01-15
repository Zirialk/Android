package com.example.usuario.pr033recyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class Adapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Interfaz que debe implementar el listener para cuando se haga click sobre un elemento.
    public interface OnItemClickListener {
        void onItemClick(View view, Alumno alumno, int position);
    }

    private final ArrayList<ListItem> mDatos;
    private OnItemClickListener onItemClickListener;
    private View emptyView;


    public Adapter(ArrayList<ListItem> datos){
        mDatos=datos;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == ListItem.TYPE_CHILD)
            return onCreateAlumnoViewHolder(parent);
        else
            return onCreateGrupoViewHolder(parent);
    }

    private RecyclerView.ViewHolder onCreateAlumnoViewHolder(ViewGroup parent){
        //Se infla el layout.
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alumno,parent ,false);
        //Se crea el contenedor de vistas para la fila
        final RecyclerView.ViewHolder viewHolder = new AlumnoViewHolder(itemView);
        //Cuando se hace click sobre el elemento
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener != null)
                    onItemClickListener.onItemClick(v, (Alumno) mDatos.get(viewHolder.getAdapterPosition()),viewHolder.getAdapterPosition());
            }
        });
        return viewHolder;
    }

    private RecyclerView.ViewHolder onCreateGrupoViewHolder(ViewGroup parent) {
        // Se infla el layout.
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_grupo, parent, false);
        // Se crea el contenedor de vistas para la fila.
        return new GrupoViewHolder(itemView);

    }
    @Override
    public int getItemViewType(int position) {
        return mDatos.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return mDatos.size();
    }



    public void addItem(Alumno alumno) {
        mDatos.add(alumno);
        notifyItemInserted(mDatos.size()-1);
        checkIfEmpty();
    }


    public void removeItem(int position){
        mDatos.remove(position);
        notifyItemRemoved(position);
        // Si la lista ha quedado vacía se muestra la empty view.
        checkIfEmpty();
    }

    private void checkIfEmpty() {
        if(emptyView != null)
            emptyView.setVisibility(getItemCount() > 0 ? View.GONE : View.VISIBLE);
    }
    // Establece la empty view para la lista.
    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
        // Muestra la empty view si  la lista está vacía.
        checkIfEmpty();
    }

    // Establece el listener a informar cuando se hace click sobre un elemento de la lista.
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(mDatos.get(position).getType() == ListItem.TYPE_CHILD)
            ((AlumnoViewHolder)holder).onBind((Alumno)mDatos.get(position));
        else
            ((GrupoViewHolder)holder).onBind((Grupo)mDatos.get(position));
    }


    static class AlumnoViewHolder extends RecyclerView.ViewHolder{


        private final TextView lblNombre;

        public AlumnoViewHolder(View itemView) {
            super(itemView);
            lblNombre = (TextView) itemView.findViewById(R.id.lblNombre);
        }
        public void onBind(Alumno alumno){
            lblNombre.setText(alumno.getNombre());
        }
    }
    static class GrupoViewHolder extends RecyclerView.ViewHolder{


        private final TextView lblNombre;

        public GrupoViewHolder(View itemView) {
            super(itemView);
            lblNombre = (TextView) itemView.findViewById(R.id.lblNombre);
        }
        public void onBind(Grupo grupo){
            lblNombre.setText(grupo.getNombre());
        }
    }


}
