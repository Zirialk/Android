package com.example.usuario.pr023listconfragment;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class AgregarContactoActivity extends AppCompatActivity {

    private int RC_SELECCIONAR_FOTO=1;
    private EditText txtNombre;
    private EditText txtLocalidad;
    private EditText txtCalle;
    private EditText txtTelefono;
    private ImageView imgAvatar;
    String imagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_contacto);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        initViews();
    }

    private void initViews() {
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtLocalidad = (EditText) findViewById(R.id.txtLocalidad);
        txtCalle = (EditText) findViewById(R.id.txtCalle);
        txtTelefono = (EditText) findViewById(R.id.txtTlf);
        imgAvatar = (ImageView) findViewById(R.id.imgAvatar);
        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarImagen();
            }
        });
    }

    private void seleccionarImagen(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, RC_SELECCIONAR_FOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK)
            switch (requestCode){
                case 1:
                    Uri uriGaleria = data.getData();
                    cargarImagen(getRealPath(uriGaleria));
                    break;
            }
        super.onActivityResult(requestCode, resultCode, data);
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
    private void cargarImagen(String realPathImage){
        Toast.makeText(this,realPathImage,Toast.LENGTH_SHORT).show();

    }

    public static void startForResult(Activity activity, int requestCode){
        Intent intent = new Intent(activity,AgregarContactoActivity.class);

        activity.startActivityForResult(intent,requestCode);

    }
}
