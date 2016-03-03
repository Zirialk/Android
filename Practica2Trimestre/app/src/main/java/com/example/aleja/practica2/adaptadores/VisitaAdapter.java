package com.example.aleja.practica2.adaptadores;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.example.aleja.practica2.R;
import com.example.aleja.practica2.bdd.DAO;
import com.example.aleja.practica2.modelos.Alumno;
import com.example.aleja.practica2.modelos.Visita;
import com.example.aleja.practica2.utils.Constantes;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

public class VisitaAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final boolean isProxVisita;
    private List<Visita> mDatos;
    private OnVisitaClickListener mOnVisitaClickListener;
    private View emptyView;

    public interface OnVisitaClickListener{
        void onVisitaClick(View view, Visita visita, int position);
    }

    public VisitaAdapter(List<Visita> datos, boolean isProxVisita){
        mDatos=datos;
        this.isProxVisita = isProxVisita;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        //Se infla el item de Visita
        View visitaView;
        final RecyclerView.ViewHolder viewHolder;

        //Se inflará el item visita_prox o visita, según se haya especificado al construir el adaptador.
        if(isProxVisita){
            visitaView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_visita_prox, parent, false);
            viewHolder = new ProxVisitaViewHolder(visitaView);
        }else{
            visitaView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_visita, parent, false);
            viewHolder = new VisitaViewHolder(visitaView);
        }



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
        if(isProxVisita)
            ((ProxVisitaViewHolder)holder).onBind(mDatos.get(position));
        else
            ((VisitaViewHolder)holder).onBind(mDatos.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatos.size();
    }

    //VIEWHOLDERS
    // VISITAS DEL ALUMNO
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
    //VISITAS PRÓXIMAS
    class ProxVisitaViewHolder extends RecyclerView.ViewHolder{

        private final TextView lblNombre;
        private final TextView lblDia;
        private final ImageView imgAvatar;

        public ProxVisitaViewHolder(View itemView) {
            super(itemView);
            lblDia = (TextView) itemView.findViewById(R.id.lblDia);
            lblNombre = (TextView) itemView.findViewById(R.id.lblNombre);
            imgAvatar = (ImageView) itemView.findViewById(R.id.imgAvatar);
        }
        public void onBind(Visita visita){
            SimpleDateFormat formatHora = new SimpleDateFormat("HH:mm", Locale.getDefault());
            Date fechaActual = new Date();

            //Si existe esta visita
            if(visita.getId() != 0){
                //Aparecerá como fecha
                lblDia.setText(new SimpleDateFormat("dd/MM/yy", Locale.getDefault()).format(new Date(visita.getDia().getTime() + TimeUnit.DAYS.toMillis(Constantes.DIAS_PROX_VISITAS))));

            //Si el alumno no ha realizado ninguna visita, le aparecerá un mensaje al usuario informandole de que debe ir cuanto antes.
            }else
                lblDia.setText(R.string.cuantoAntes);

            //Se obtiene el alumno dueño de la visita.
            Alumno alumno = DAO.getInstance(itemView.getContext()).getAlumno(visita.getIdAlumno());
            lblNombre.setText(alumno.getNombre());

            //Si el alumno no contiene imagen o no la encuentra cargará su primera letras más un fondo de color
            Drawable drawable = TextDrawable.builder()
                    .beginConfig()
                    .width(100)
                    .height(100)
                    .toUpperCase()
                    .endConfig()
                    .rect().build(alumno.getNombre().substring(0, 1), ColorGenerator.MATERIAL.getColor(alumno.getNombre()));
            if(alumno.getFoto().isEmpty())
                    imgAvatar.setImageDrawable(drawable);
            else {
                Bitmap thumbnail = BitmapFactory.decodeFile(alumno.getFoto(), new BitmapFactory.Options());
                imgAvatar.setImageBitmap(ThumbnailUtils.extractThumbnail(thumbnail, imgAvatar.getLayoutParams().width, imgAvatar.getLayoutParams().height));
            }
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
        checkIfEmpty();
    }

    public boolean isProxVisita() {
        return isProxVisita;
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
