package com.example.usuario.pr051seleccionarimggaleria;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.File;
import java.io.FileOutputStream;

import jp.wasabeef.picasso.transformations.BlurTransformation;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;
import jp.wasabeef.picasso.transformations.CropSquareTransformation;

public class MainActivity extends AppCompatActivity {

    private static final int RC_SELECCIONAR_FOTO = 54;
    private static final String STATE_PATH = "PATH_IMG__FILE";
    private static final String PATH_PREFERENCE = "preference_path";
    private String mNombreArchivo;
    private ImageView imgFoto;
    private String mPathFotoOriginal;
    private String sNombreArchivo = "nombreArchivoFoto";
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        imgFoto = (ImageView) findViewById(R.id.imgFoto);
        setSupportActionBar(toolbar);
        preferences = getSharedPreferences("preferences", MODE_PRIVATE);
        mPathFotoOriginal = preferences.getString(PATH_PREFERENCE, "");
        if(!TextUtils.isEmpty(mPathFotoOriginal))
            //imgFoto.setImageURI(Uri.parse(mPathFotoOriginal));
            Picasso.with(this).load(new File(mPathFotoOriginal)).transform(new CropCircleTransformation() ).into(imgFoto);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seleccionarFoto("nombreFoto");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Envía un intent implícito para seleccionar una foto de la galería.
// Recibe el nombre que debe tomar el archivo con la foto escalada y guardada en privado.
    private void seleccionarFoto(String nombreArchivoPrivado) {
        // Se guarda el nombre para uso posterior.
        mNombreArchivo = nombreArchivoPrivado;
        // Se seleccionará un imagen de la galería.
        // (el segundo parámetro es el Data, que corresponde a la Uri de la galería.)
        Intent i = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        i.setType("image/*");
        startActivityForResult(i, RC_SELECCIONAR_FOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case RC_SELECCIONAR_FOTO:
                    // Se obtiene el path real a partir de la uri retornada por la galería.
                    Uri uriGaleria = data.getData();
                    mPathFotoOriginal = getRealPath(uriGaleria);
                    Bitmap bitmap = escalarFoto();


                    // Se guarda la copia propia de la imagen.
                    File archivo = crearArchivoFoto(sNombreArchivo, false);
                    if (archivo != null) {
                        if (guardarBitmapEnArchivo(bitmap, archivo)) {
                            // Se almacena el path de la foto a mostrar en el ImageView.
                            mPathFotoOriginal = archivo.getAbsolutePath();
                            imgFoto.setImageBitmap(bitmap);
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString(PATH_PREFERENCE, mPathFotoOriginal);
                            editor.apply();
                        }
                        break;
                    }

            }
        }
    }

    // Guarda el bitamp de la foto en un archivo. Retorna si ha ido bien.
    private boolean guardarBitmapEnArchivo(Bitmap bitmapFoto, File archivo) {
        try {
            FileOutputStream flujoSalida = new FileOutputStream(
                    archivo);
            bitmapFoto.compress(Bitmap.CompressFormat.JPEG, 100, flujoSalida);
            flujoSalida.flush();
            flujoSalida.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private Bitmap escalarFoto() {
        // Se obtiene el tamaño de la vista de destino.
        int anchoImageView = imgFoto.getWidth();
        int altoImageView = imgFoto.getHeight();
        // Se obtiene el tamaño de la imagen.
        BitmapFactory.Options opciones = new BitmapFactory.Options();
        opciones.inJustDecodeBounds = true; // Solo para cálculo.
        BitmapFactory.decodeFile(mPathFotoOriginal, opciones);
        int anchoFoto = opciones.outWidth;
        int altoFoto = opciones.outHeight;
        // Se obtiene el factor de escalado para la imagen.
        int factorEscalado = Math.min(anchoFoto / anchoImageView,
                altoFoto / altoImageView);
        // Se escala la imagen con dicho factor de escalado.
        opciones.inJustDecodeBounds = false; // Se escalará.
        opciones.inSampleSize = factorEscalado;
        Bitmap foto = BitmapFactory.decodeFile(mPathFotoOriginal, opciones);

        return foto;
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

    // Crea un archivo de foto con el nombre indicado en almacenamiento externo si es posible, o si
// no en almacenamiento interno, y lo retorna. Retorna null si fallo.
// Si publico es true -> en la carpeta pública de imágenes.
// Si publico es false, en la carpeta propia de imágenes.
    private File crearArchivoFoto(String nombre, boolean publico) {
        // Se obtiene el directorio en el que almacenarlo.
        File directorio;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            if (publico) {
                // En el directorio público para imágenes del almacenamiento externo.
                directorio = Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            } else {
                directorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
            }
        } else {
            // En almacenamiento interno.
            directorio = getFilesDir();
        }
        // Su no existe el directorio, se crea.
        if (directorio != null && !directorio.exists()) {
            if (!directorio.mkdirs()) {
                Log.d(getString(R.string.app_name), "error al crear el directorio");
                return null;
            }
        }
        // Se crea un archivo con ese nombre y la extensión jpg en ese
        // directorio.
        File archivo = null;
        if (directorio != null) {
            archivo = new File(directorio.getPath() + File.separator +
                    nombre);
            Log.d(getString(R.string.app_name), archivo.getAbsolutePath());
        }
        // Se retorna el archivo creado.
        return archivo;
    }

   
}
