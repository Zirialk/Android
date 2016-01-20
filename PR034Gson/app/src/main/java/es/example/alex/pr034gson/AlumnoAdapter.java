package es.example.alex.pr034gson;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import it.sephiroth.android.library.picasso.Picasso;


public class AlumnoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public interface OnItemClickListener{
        void onItemClick(View view, Alumno alumno,int position);
    }

    private final ArrayList<ListItem> mDatos;
    private OnItemClickListener onItemClickListener;
    private View emptyView;


    public AlumnoAdapter(ArrayList<ListItem> datos){
        mDatos=datos;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == ListItem.TYPE_CHILD)
            return onCreateAlumnoViewHolder(parent);

        return null;
    }
    private RecyclerView.ViewHolder onCreateAlumnoViewHolder(ViewGroup parent){
        //Se infla el layout
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alumno, parent, false);
        //Se crea el contenedor de vistas para la fila
        final RecyclerView.ViewHolder viewHolder = new AlumnoViewHolder(itemView);

        //Cuando se hace click en un elemento de la lista.
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener != null)
                    onItemClickListener.onItemClick(v, (Alumno) mDatos.get(viewHolder.getAdapterPosition()), viewHolder.getAdapterPosition());
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if(mDatos.get(position).getType() == ListItem.TYPE_CHILD)
            ((AlumnoViewHolder)viewHolder).onBind((Alumno)mDatos.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatos.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mDatos.get(position).getType();
    }

     class AlumnoViewHolder extends RecyclerView.ViewHolder{


        private final TextView lblNombre;
        private final ImageView imgFoto;
        private final TextView lblEdad;
        private final Button btnLlamar;
        private final Button btnBorrar;

        public AlumnoViewHolder(View itemView) {
            super(itemView);
            lblNombre = (TextView) itemView.findViewById(R.id.lblNombre);
            imgFoto = (ImageView) itemView.findViewById(R.id.imgFoto);
            lblEdad = (TextView) itemView.findViewById(R.id.lblEdad);
            btnLlamar = (Button) itemView.findViewById(R.id.btnLlamar);
            btnBorrar = (Button) itemView.findViewById(R.id.btnBorrar);
        }
        public void onBind(final Alumno alumno){
            lblNombre.setText(alumno.getNombre());
            lblEdad.setText(alumno.getEdad()+ " años");
            //AQUIIIIIIIIIIIIIIIIII
            Picasso.with(lblNombre.getContext()).load(alumno.getFoto()).into(imgFoto);

            btnBorrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeItem(mDatos.indexOf(alumno));
                }
            });
            btnLlamar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+alumno.getTelefono()));
                    v.getContext().startActivity(intent);

                }
            });
        }
    }

    public void addItem(Alumno alumno) {
        notifyItemInserted(mDatos.size()-1);
        mDatos.add(alumno);
        checkIfEmpty();
    }


    public void removeItem(int position){
        notifyItemRemoved(position);
        mDatos.remove(position);
        // Si la lista ha quedado vacía se muestra la empty view.
        checkIfEmpty();
    }
    public boolean containsItem(Alumno alumno){
        return mDatos.contains(alumno);
    }

    private void checkIfEmpty(){
        if(emptyView!=null)
            emptyView.setVisibility(getItemCount() > 0 ? View.GONE : View.INVISIBLE);
    }
    // Establece la empty view para la lista.
    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
        // Muestra la empty view si  la lista está vacía.
        checkIfEmpty();
    }
    // Establece el listener a informar cuando se hace click sobre un ítem.
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }
}
