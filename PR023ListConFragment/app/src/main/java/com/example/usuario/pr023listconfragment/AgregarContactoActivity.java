package com.example.usuario.pr023listconfragment;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class AgregarContactoActivity extends AppCompatActivity {

    public static final int MODO_CREAR = 123;
    public static final int MODO_EDITAR = 888;
    private static final String INTENT_EDITAR = "Editando";
    private final int RC_SELECCIONAR_FOTO=1;
    private EditText txtNombre;
    private EditText txtEdad;
    private EditText txtLocalidad;
    private EditText txtCalle;
    private EditText txtPrefijo;
    private EditText txtTlf;
    private ImageView mImgAvatar;
    private Menu mMenu;
    private Alumno newAlumno;
    private String mImgGaleriaPath;
    private Bitmap mBitMapFoto;
    private int modoActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_contacto);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        initViews();
        //Cuando vengo desde el botón de menú: itemEditar
        //Si devuelve un indice de la lista de alumnos que no sea -1
        if(getIntent().getIntExtra(INTENT_EDITAR,-1)!=-1){
            //newAlumno pasa a ser el alumno a editar, en vez de uno nuevo
            newAlumno= ListaFragment.listaAlumnos.get(getIntent().getIntExtra(INTENT_EDITAR,-1));
            modoActual=MODO_EDITAR;
        }else{
            newAlumno = new Alumno();
            modoActual=MODO_CREAR;
        }
    }


    public static void startForResult(Activity activity, int indiceContacto){
        //Crea un intent, que contendrá el índice del alumno a editar de la lista de Alumnos,
        Intent intent = new Intent(activity,AgregarContactoActivity.class);
        intent.putExtra(INTENT_EDITAR, indiceContacto);
        activity.startActivityForResult(intent, MODO_EDITAR);
    }
    //Dota a los EditText con los datos del alumno que se quiere editar.
    private void recuperarContactoExistente(){

        txtNombre.setText(newAlumno.getNombre());
        txtEdad.setText(String.valueOf(newAlumno.getEdad()));
        txtLocalidad.setText(newAlumno.getLocalidad());
        txtCalle.setText(newAlumno.getCalle());
        txtTlf.setText(newAlumno.getTlf().substring(3));
        txtPrefijo.setText(newAlumno.getTlf().substring(0, 3));
        Picasso.with(this).load(new File(newAlumno.getAvatar())).into(mImgAvatar);
    }
    private void initViews() {
        final ImageView imgIconContacto = (ImageView) findViewById(R.id.imgIconContacto);
        final ImageView imgIconTlf = (ImageView) findViewById(R.id.imgIconTlf);
        final ImageView imgIconLocalidad = (ImageView) findViewById(R.id.imgIconLocalidad);
        final ImageView imgIconCalle = (ImageView) findViewById(R.id.imgIconCalle);
        final int COLOR_EN_FOCO = getResources().getColor(R.color.accent_oscuro);
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtEdad = (EditText) findViewById(R.id.txtEdad);
        txtLocalidad = (EditText) findViewById(R.id.txtLocalidad);
        txtCalle = (EditText) findViewById(R.id.txtCalle);
        txtPrefijo = (EditText) findViewById(R.id.txtPrefijo);
        txtTlf = (EditText) findViewById(R.id.txtTlf);
        mImgAvatar = (ImageView) findViewById(R.id.imgAvatar);

        txtNombre.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    //Resalta con otro color el icono correspondiente al txt.
                    imgIconContacto.setColorFilter(COLOR_EN_FOCO);
                else
                    //Devuelve al color original el imageView cuando el txt pierde el foco.
                    imgIconContacto.clearColorFilter();
            }
        });
        txtEdad.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    //Resalta con otro color el icono correspondiente al txt.
                    imgIconContacto.setColorFilter(COLOR_EN_FOCO);
                else
                    //Devuelve al color original el imageView cuando el txt pierde el foco.
                    imgIconContacto.clearColorFilter();
            }
        });
        txtTlf.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    //Resalta con otro color el icono correspondiente al txt.
                    imgIconTlf.setColorFilter(COLOR_EN_FOCO);
                else
                    //Devuelve al color original el imageView cuando el txt pierde el foco.
                    imgIconTlf.clearColorFilter();
            }
        });
        txtPrefijo.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    //Resalta con otro color el icono correspondiente al txt.
                    imgIconTlf.setColorFilter(COLOR_EN_FOCO);
                else
                    //Devuelve al color original el imageView cuando el txt pierde el foco.
                    imgIconTlf.clearColorFilter();
            }
        });
        txtLocalidad.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    //Resalta con otro color el icono correspondiente al txt.
                    imgIconLocalidad.setColorFilter(COLOR_EN_FOCO);
                else
                    //Devuelve al color original el imageView cuando el txt pierde el foco.
                    imgIconLocalidad.clearColorFilter();
            }
        });
        txtCalle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    //Resalta con otro color el icono correspondiente al txt.
                    imgIconCalle.setColorFilter(COLOR_EN_FOCO);
                else
                    //Devuelve al color original el imageView cuando el txt pierde el foco.
                    imgIconCalle.clearColorFilter();
            }
        });

        mImgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarImagen();
            }
        });

        txtNombre.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                //Solo se podrá guardar un alumno cuando tengan mínimo un nombre y un teléfono.
                if (TextUtils.isEmpty(s) || TextUtils.isEmpty(txtTlf.getText()))
                    mMenu.findItem(R.id.itemNewContact).setVisible(false);
                else
                    mMenu.findItem(R.id.itemNewContact).setVisible(true);
            }
        });
        txtTlf.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            public void afterTextChanged(Editable s) {
                //Solo se podrá guardar un alumno cuando tengan mínimo un nombre y un teléfono.
                if (TextUtils.isEmpty(s) || TextUtils.isEmpty(txtNombre.getText()))
                    mMenu.findItem(R.id.itemNewContact).setVisible(false);
                else
                    mMenu.findItem(R.id.itemNewContact).setVisible(true);
            }
        });
    }







    @Override
    public void finish() {
        setResult(RESULT_OK);
        super.finish();
    }
//                           -- MENÚ --
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.mMenu =menu;
        getMenuInflater().inflate(R.menu.menu_agregar_contacto, menu);
        //El item de menú Aceptar contacto aparecerá invisible por defecto.
        menu.findItem(R.id.itemNewContact).setVisible(false);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //Obligatoriamente tiene que hacerse tras haberse creado el menú, sino
        //al restablecer los datos del alumno, saltará el evento de textChanged
        //y accedera al menú, que hubiera lanzado una excepción.
        if(modoActual==MODO_EDITAR)
            recuperarContactoExistente();
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.itemNewContact:
                crearAlumno();
                //Se sale del creador de alumnos.
                finish();
            case R.id.itemCancelContact:
                //Se sale del creador de alumnos sin añadir ningún alumno.
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void crearAlumno(){
        int edad=0;
        File archivoFoto;
        String prefijo = getString(R.string.hintPrefijo);

        //Si no ha introducido ninguna edad el alumno tendrá 0 años.
        if(!TextUtils.isEmpty(txtEdad.getText()))
            edad=Integer.parseInt(txtEdad.getText().toString());
        //Si ha introducido prefijo se usará el introducido, mientras que si no se ha introducido se usará el de por defecto,
        //marcado en el hint del txtPrefijo.
        if(!TextUtils.isEmpty(txtPrefijo.getText()))
            prefijo=txtPrefijo.getText().toString();

        //Se configura el nuevo alumno y se envia.
        newAlumno.setNombre(txtNombre.getText().toString());
        newAlumno.setEdad(edad);
        newAlumno.setLocalidad(txtLocalidad.getText().toString());
        newAlumno.setCalle(txtCalle.getText().toString());
        newAlumno.setTlf(prefijo + txtTlf.getText().toString());
        if(mBitMapFoto!=null) {
            //Se guarda la imagen en un archivo con el hashCode del alumno
            //para que haya imagenes diferentes con el mismo nombre
            archivoFoto = crearArchivo(String.valueOf(newAlumno.hashCode()));
            if (archivoFoto != null) {
                guardarImgEnArchivo(mBitMapFoto, archivoFoto);
                //Se guarda en el alumno la ruta de ese nuevo archivo.
                newAlumno.setAvatar(archivoFoto.getAbsolutePath());
            }

        }
        //Solo guardará en el array cuando es un alumno nuevo.
        if(modoActual==MODO_CREAR)
            ListaFragment.listaAlumnos.add(newAlumno);

    }

    //    -----   PROCESAMIENTO DE FOTOGRAFÍA  -----
    private void seleccionarImagen(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, RC_SELECCIONAR_FOTO);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK)
            switch (requestCode){
                case RC_SELECCIONAR_FOTO:
                    Uri uriGaleria = data.getData();
                    //Se guarda el path de la foto de la galería para la posterior creación de un archivo con esa foto.
                    mImgGaleriaPath = getRealPath(uriGaleria);
                    //Se escala la imagen y se guarda en una variable.
                    mBitMapFoto = escalar(mImgGaleriaPath);
                    //Se va mostrando la preview de la imagen elegida.
                    mImgAvatar.setImageBitmap(mBitMapFoto);
                    break;

            }
        super.onActivityResult(requestCode, resultCode, data);
    }
    private File crearArchivo(String nombreArchivo){
        Random rnd = new Random();
        //Se renombra el archivo ya que no se puede sobreescribir.
        nombreArchivo+=rnd.nextInt(500);
        //Se obtiene el directorio interno.
        File directorio = getFilesDir();

        //Si el directorio aun no existe, se crea.
        if(!directorio.exists())
            if (!directorio.mkdirs()) {
                Log.d(getString(R.string.app_name), "error al crear el directorio");
                return null;
            }


        //Se crea el archivo con el nombre pasado por parámetro
        return new File(directorio.getPath() + File.separator + nombreArchivo+".jpg");
    }

    private boolean guardarImgEnArchivo(Bitmap bitmapFoto, File file){
        try {
            FileOutputStream stream = new FileOutputStream(file);
            bitmapFoto.compress(Bitmap.CompressFormat.JPEG,100,stream);
            stream.flush();
            stream.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    private String getRealPath(Uri uriGaleria) {

        // Se consulta en el content provider de la galería el path real del archivo de la foto.
        String[] filePath = {MediaStore.Images.Media.DATA};
        Cursor c = getContentResolver().query(uriGaleria, filePath, null, null, null);
        c.moveToFirst();
        int columnIndex = c.getColumnIndex(filePath[0]);
        String path = c.getString(columnIndex);
        c.close();
        return path;
    }
    private Bitmap escalar(String realPathImage){
        // Se obtiene el tamaño de la vista de destino.
        //int anchoImageView = mImgAvatar.getWidth();
        //int altoImageView = mImgAvatar.getHeight();

        int anchoImageView=150;
        int altoImageView=150;

        // Se obtiene el tamaño de la imagen.
        BitmapFactory.Options opciones = new BitmapFactory.Options();
        opciones.inJustDecodeBounds = true; // Solo para cálculo.
        BitmapFactory.decodeFile(realPathImage, opciones);
        int anchoFoto = opciones.outWidth;
        int altoFoto = opciones.outHeight;
        // Se obtiene el factor de escalado para la imagen.
        int  factorEscalado = Math.min(anchoFoto/anchoImageView, altoFoto/altoImageView);

        // Se escala la imagen con dicho factor de escalado.
        opciones.inJustDecodeBounds = false; // Se escalará.
        opciones.inSampleSize = factorEscalado;
        //Se guarda en la variable de la clase contenedora el BitMap

        return BitmapFactory.decodeFile(realPathImage, opciones);
    }



}
