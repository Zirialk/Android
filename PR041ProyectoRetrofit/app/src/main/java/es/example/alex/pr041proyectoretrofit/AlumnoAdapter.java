package es.example.alex.pr041proyectoretrofit;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import it.sephiroth.android.library.picasso.Picasso;
import retrofit2.Callback;
import retrofit2.Response;


public class AlumnoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public interface OnItemClickListener{
        void onItemClick(View view, Alumno alumno, int position);
    }

    private final ArrayList<Alumno> mDatos;
    private OnItemClickListener onItemClickListener;
    private View emptyView;

    public AlumnoAdapter(){mDatos = new ArrayList<>();}
    public AlumnoAdapter(ArrayList<Alumno> datos){
        mDatos=datos;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //Se infla el layout
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alumno, parent, false);
        //Se crea el contenedor de vistas para la fila
        final RecyclerView.ViewHolder viewHolder = new AlumnoViewHolder(itemView);

        //Cuando se hace click en un elemento de la lista.
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null)
                    onItemClickListener.onItemClick(v, (Alumno) mDatos.get(viewHolder.getAdapterPosition()), viewHolder.getAdapterPosition());
            }
        });
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ((AlumnoViewHolder)viewHolder).onBind((Alumno)mDatos.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatos.size();
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
            if(!alumno.getFoto().isEmpty())
                Picasso.with(itemView.getContext()).load(alumno.getFoto()).error(R.drawable.icon_user_default).into(imgFoto);
            else
                imgFoto.setImageResource(R.drawable.icon_user_default);

            btnBorrar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Se comprueba que exista antes de borrarlo para que cuando le de dos veces rapido no sale ninguna excepción.
                    if(mDatos.contains(alumno))
                        Bdd.getServicio().borrarAlumno(alumno.getId()).enqueue(new Callback<Alumno>() {
                            @Override
                            public void onResponse(Response<Alumno> response) {
                                //Si ha conseguido borrarlo de la Bdd, lo borrará de la lista.
                                removeAlumno(mDatos.indexOf(alumno));
                            }

                            @Override
                            public void onFailure(Throwable t) {
                            }
                        });

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

    public void addAlumno(Alumno alumno) {
        mDatos.add(alumno);
        notifyItemInserted(mDatos.size()-1);
        checkIfEmpty();
    }
    public void addAlumnos(List<Alumno> alumnos) {
        mDatos.addAll(alumnos);
        notifyDataSetChanged();
        checkIfEmpty();
    }

    public void removeAlumno(int position){
        mDatos.remove(position);
        notifyItemRemoved(position);
        // Si la lista ha quedado vacía se muestra la empty view.
        checkIfEmpty();
    }
    public void updateAlumno(int idAlumno, Alumno newAlumno){
        int index = -1;

        for (Alumno a : mDatos)
            if(a.getId() == idAlumno)
                index = mDatos.indexOf(a);

        //Reemplaza el antiguo alumno por el actualizado.
        mDatos.set(index,newAlumno);
        notifyItemChanged(index);
    }
    //Reemplaza los alumnos existentes por otros nuevos.
    public void replaceAlumnos(List<Alumno> alumnos){
        mDatos.clear();
        mDatos.addAll(alumnos);
        notifyDataSetChanged();
    }
    public boolean containsAlumno(Alumno alumno){
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
