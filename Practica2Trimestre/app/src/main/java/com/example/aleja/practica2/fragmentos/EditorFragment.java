package com.example.aleja.practica2.fragmentos;

import android.animation.Animator;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.graphics.drawable.DrawerArrowDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.aleja.practica2.R;
import com.example.aleja.practica2.actividades.MainActivity;
import com.example.aleja.practica2.bdd.DAO;
import com.example.aleja.practica2.modelos.Alumno;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;


public class EditorFragment extends Fragment {

    private static final String ARG_ALUMNO = "alumno_a_editar";
    public static final int RC_SELECCIONAR_FOTO = 8;
    private TextView txtEmail;
    private TextView txtNombre;
    private TextView txtTelefono;
    private TextView txtEmpresa;
    private TextView txtTutor;
    private TextView txtHorario;
    private TextView txtDireccion;
    private ImageView imgFoto;
    private Alumno mAlumno;
    private FloatingActionButton fab;
    private String mPathFotoOriginal;

    private Bitmap mBitmapEscalado;

    public static EditorFragment newInstance(Alumno alumno) {

        Bundle args = new Bundle();
        args.putParcelable(ARG_ALUMNO, alumno);
        EditorFragment fragment = new EditorFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_editor_alumno, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
        //Si se ha entrado a editar un mAlumno, se cargará sus datos en los editText.
        if(getArguments()!=null)
            if((mAlumno = getArguments().getParcelable(ARG_ALUMNO)) != null)
                cargarDatosAlumno();

    }

    private void initViews() {
        imgFoto = (ImageView) getActivity().findViewById(R.id.imgFoto);
        txtNombre = (TextView) getActivity().findViewById(R.id.txtNombre);
        txtTelefono = (TextView) getActivity().findViewById(R.id.txtTelefono);
        txtEmail = (TextView) getActivity().findViewById(R.id.txtEmail);
        txtEmpresa = (TextView) getActivity().findViewById(R.id.txtEmpresa);
        txtTutor = (TextView) getActivity().findViewById(R.id.txtTutor);
        txtHorario = (TextView) getActivity().findViewById(R.id.txtHorario);
        txtDireccion = (TextView) getActivity().findViewById(R.id.txtDireccion);
        configFab();
    }

    private void configFab() {
        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        moverFabDebajoImgFoto();
    }
    private void moverFabDebajoImgFoto(){
        imgFoto.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //Remove the listener before proceeding
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                    imgFoto.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                else
                    imgFoto.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                //PosiciónY donde termina el ImageView imgFoto.
                final int posFinalImgFoto = getPosDebajoImgFoto();
                //Colocará el centro del FAB en el final del imageView
                if (fab.getY() != posFinalImgFoto)
                    //Se mueve a la misma posición adonde estaba para que su getY se actualize, porque sino fab.getY no se actualizará se moverá mal
                    fab.hide(new FloatingActionButton.OnVisibilityChangedListener() {
                        @Override
                        public void onHidden(final FloatingActionButton fab) {
                            fab.animate().translationY(0).setDuration(0).setListener(new Animator.AnimatorListener() {
                                @Override
                                public void onAnimationStart(Animator animation) {
                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    MainActivity.translateFab(fab, 0, -(fab.getY() - posFinalImgFoto), R.drawable.ic_photo_camera_white_24dp);
                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {
                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {
                                }
                            });
                        }
                    });


            }
        });
    }
    //Obtiene la posición exacta del final de imgFoto respecto a la pantalla.
    public int getPosDebajoImgFoto(){
        int[] posImgFoto = new int[2];
        imgFoto.getLocationInWindow(posImgFoto);
        return (posImgFoto[1]+ imgFoto.getLayoutParams().height/2)+ fab.getWidth()/3;
    }



    //Rellena los EditText con los datos del mAlumno.
    private void cargarDatosAlumno() {
        txtNombre.setText(mAlumno.getNombre());
        txtTelefono.setText(mAlumno.getTelefono());
        txtEmail.setText(mAlumno.getEmail());
        txtEmpresa.setText(mAlumno.getEmpresa());
        txtTutor.setText(mAlumno.getTutor());
        txtHorario.setText(mAlumno.getHorario());
        txtDireccion.setText(mAlumno.getDireccion());
        if(mAlumno.getFoto().isEmpty())
            imgFoto.setImageResource(R.drawable.icon_user_default);
        else
            imgFoto.setImageURI(Uri.fromFile(new File(mAlumno.getFoto())));
    }
    private boolean crearAlumno(){
        Alumno alumno = getAlumnoFromForms();
        //Si se han rellenado todos los campos y el alumno se ha creado correctamente
        //Se guardará en la BDD
        if(alumno != null){
            DAO.getInstance(getContext()).createAlumno(alumno);
            return true;
        }else
            return false;
    }
    private boolean actualizarAlumno(){
        Alumno alumno = getAlumnoFromForms();
        //Si se han rellenado todos los campos y el alumno se ha creado correctamente
        //Se actualizará en la BDD
        if(alumno != null){
            DAO.getInstance(getContext()).updateAlumno(alumno);
            return true;
        }else
            return false;
    }
    //Comprueba que esten rellenos los campos mínimos.
    //Si alguno los mínimos no lo está, se le indicará al usuario.
    private boolean comprobarCamposRellenos(){
        boolean rellenos = true;

        if(txtNombre.getText().toString().isEmpty()){
            txtNombre.setError(getActivity().getString(R.string.errorNombre));
            rellenos = false;
        }
        if(txtTutor.getText().toString().isEmpty()){
            txtTutor.setError(getActivity().getString(R.string.errorTutor));
            rellenos = false;
        }
        if(txtEmpresa.getText().toString().isEmpty()){
            txtEmpresa.setError(getActivity().getString(R.string.errorEmpresa));
            rellenos = false;
        }
        if(txtHorario.getText().toString().isEmpty()){
            txtHorario.setError(getActivity().getString(R.string.errorHorario));
            rellenos = false;
        }
        if(txtTelefono.getText().toString().isEmpty()){
            txtTelefono.setError(getActivity().getString(R.string.errorTelefono));
            rellenos = false;
        }
        if(txtDireccion.getText().toString().isEmpty()){
            txtDireccion.setError(getActivity().getString(R.string.errorDireccion));
            rellenos = false;
        }
        return rellenos;
    }
    //Extrae un objeto alumno de a partir de los editText.
    @Nullable
    private Alumno getAlumnoFromForms(){
        //Si no hay ningú campo vacío.
        if(comprobarCamposRellenos()){
            String pathFotoEscalada = "";
            //Si ha escogido una imagen se guardará en un archivo para no tener que volver a escalarla
            //Si no ha escogido ninguna, en el atributo foto constará una cadena vacía (no tiene foto).
            if(mBitmapEscalado != null){
                // Se guarda en un archivo para que no se tenga que volver a escalar.
                File archivo = crearArchivoFoto(txtNombre.getText().toString(),false);
                guardarBitmapEnArchivo(mBitmapEscalado,archivo);
                pathFotoEscalada = archivo.getAbsolutePath();
            }
            Alumno alumnoAux = new Alumno(txtNombre.getText().toString(), txtTelefono.getText().toString(), txtEmail.getText().toString(), txtEmpresa.getText().toString(), txtTutor.getText().toString(), txtHorario.getText().toString(), txtDireccion.getText().toString(), pathFotoEscalada);

            //Si se está editando, al alumnoAux se le establecerá el mismo id del alumno que estamos editando.
            if(mAlumno != null){
                alumnoAux.setId(mAlumno.getId());
                //Si no se ha escogido una imagen nueva, el alumno editado seguirá con la misma que tenía hasta el momento.
                if(pathFotoEscalada.isEmpty())
                    alumnoAux.setFoto(mAlumno.getFoto());
            }

            return alumnoAux;
        }
        else
            return null;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_creador_visita, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_save:
                //Modo Crear
                if(mAlumno == null){
                    //Si se ha creado el alumno con éxito, se volvera a la pantalla anterior.
                    if(crearAlumno())
                        getActivity().onBackPressed();

                }
                //Modo Actualizar
                else
                    //Si se ha actualizado el alumno con éxito, se volvera a la pantalla anterior.
                    if(actualizarAlumno())
                        getActivity().onBackPressed();

                break;
        }
        return true;
    }



    //                  ------------------------- MULTIMEDIA --------------------------------
    //                  ---------------------------------------------------------------------
    public void buscarFotoEnGaleria(){
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("image/*");
        getActivity().startActivityForResult(i, RC_SELECCIONAR_FOTO);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == getActivity().RESULT_OK){
            switch (requestCode){
                case RC_SELECCIONAR_FOTO:
                    // Se obtiene el path real a partir de la uri retornada por la galería.
                    Uri uriGaleria = data.getData();
                    mPathFotoOriginal = getRealPath(uriGaleria);
                    // Se escala la foto, se almacena en archivo propio y se muestra en ImageView.
                    new Escalador().execute(imgFoto.getWidth(),imgFoto.getHeight());
                    break;
            }
        }
    }


    // Obtiene el path real de una imagen a partir de la URI de Galería obtenido con ACTION_PICK.
    private String getRealPath(Uri uriGaleria) {
        // Se consulta en el content provider de la galería el path real del archivo de la foto.
        String[] filePath = {MediaStore.Images.Media.DATA};
        Cursor c = getActivity().getContentResolver().query(uriGaleria, filePath, null, null, null);
        c.moveToFirst();
        int columnIndex = c.getColumnIndex(filePath[0]);
        String path = c.getString(columnIndex);
        c.close();
        return path;

    }

    class Escalador extends AsyncTask<Integer, Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(Integer... params) {
            // Se obtiene el tamaño de la vista de destino.
            int anchoImageView = params[0];
            int altoImageView = params[1];
            // Se obtiene el tamaño de la imagen.
            BitmapFactory.Options opciones = new BitmapFactory.Options();
            opciones.inJustDecodeBounds = true; // Solo para cálculo.
            BitmapFactory.decodeFile(mPathFotoOriginal, opciones);
            int anchoFoto = opciones.outWidth;
            int altoFoto = opciones.outHeight;
            // Se obtiene el factor de escalado para la imagen.
            int factorEscalado = Math.min(anchoFoto/anchoImageView,
                    altoFoto/altoImageView);
            // Se escala la imagen con dicho factor de escalado.
            opciones.inJustDecodeBounds = false; // Se escalará.
            opciones.inSampleSize = factorEscalado;
            return BitmapFactory.decodeFile(mPathFotoOriginal, opciones);

        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            // Se asigna la foto al ImageView.
            imgFoto.setImageBitmap(bitmap);
            mBitmapEscalado = bitmap;
        }

    }

    private boolean guardarBitmapEnArchivo(Bitmap bitmapFoto, File archivo) {
        try {
            FileOutputStream flujoSalida = new FileOutputStream(archivo);
            bitmapFoto.compress(Bitmap.CompressFormat.JPEG, 100, flujoSalida);
            flujoSalida.flush();
            flujoSalida.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    // Crea un archivo de foto con el nombre indicado en almacenamiento externo si es posible, o si
    // no en almacenamiento interno, y lo retorna. Retorna null si fallo.
    // Si publico es true -> en la carpeta pública de imágenes.
    // Si publico es false, en la carpeta propia de imágenes.
    private File crearArchivoFoto(String nombre, boolean publico) {
        // Se obtiene el directorio en el que almacenarlo.
        File directorio;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            if (publico)
                // En el directorio público para imágenes del almacenamiento externo.
                directorio = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            else
                directorio = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        } else {
            // En almacenamiento interno.
            directorio = getActivity().getFilesDir();
        }
        // Su no existe el directorio, se crea.
        if (directorio != null && !directorio.exists())
            if (!directorio.mkdirs()) {
                Log.d(getString(R.string.app_name), "error al crear el directorio");
                return null;
            }

        // Se crea un archivo con ese nombre y la extensión jpg en ese
        // directorio.
        File archivo = null;
        if (directorio != null) {
            archivo = new File(directorio.getPath() + File.separator + nombre);
            Log.d(getString(R.string.app_name), archivo.getAbsolutePath());
        }
        // Se retorna el archivo creado.
        return archivo;
    }


}
