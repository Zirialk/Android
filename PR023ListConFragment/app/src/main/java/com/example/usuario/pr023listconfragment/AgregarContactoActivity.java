package com.example.usuario.pr023listconfragment;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class AgregarContactoActivity extends AppCompatActivity {

    private int RC_SELECCIONAR_FOTO=1;
    private EditText txtNombre;
    private EditText txtEdad;
    private EditText txtLocalidad;
    private EditText txtCalle;
    private EditText txtPrefijo;
    private EditText txtTlf;
    private ImageView imgAvatar;
    private Menu menu;
    String imagePath;
    Alumno newAlumno= new Alumno();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_contacto);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        initViews();
    }

    private void initViews() {
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtEdad = (EditText) findViewById(R.id.txtEdad);
        txtLocalidad = (EditText) findViewById(R.id.txtLocalidad);
        txtCalle = (EditText) findViewById(R.id.txtCalle);
        txtPrefijo = (EditText) findViewById(R.id.txtPrefijo);
        txtTlf = (EditText) findViewById(R.id.txtTlf);
        imgAvatar = (ImageView) findViewById(R.id.imgAvatar);
        imgAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarImagen();
            }
        });

        txtNombre.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            public void afterTextChanged(Editable s) {
                //Solo se podrá guardar un alumno cuando tengan mínimo un nombre y un teléfono.
                if (TextUtils.isEmpty(s) || TextUtils.isEmpty(txtTlf.getText()))
                    menu.findItem(R.id.itemNewContact).setVisible(false);
                else
                    menu.findItem(R.id.itemNewContact).setVisible(true);
            }
        });
        txtTlf.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {    }
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            public void afterTextChanged(Editable s) {
                //Solo se podrá guardar un alumno cuando tengan mínimo un nombre y un teléfono.
                if(TextUtils.isEmpty(s) || TextUtils.isEmpty(txtNombre.getText()))
                    menu.findItem(R.id.itemNewContact).setVisible(false);
                else
                    menu.findItem(R.id.itemNewContact).setVisible(true);
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
        newAlumno.setAvatar(realPathImage);
        Picasso.with(this).load(realPathImage).into(imgAvatar);
        Toast.makeText(this,realPathImage,Toast.LENGTH_SHORT).show();
    }

    public static void startForResult(Activity activity, int requestCode){
        Intent intent = new Intent(activity,AgregarContactoActivity.class);

        activity.startActivityForResult(intent,requestCode);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu=menu;
        getMenuInflater().inflate(R.menu.menu_agregar_contacto,menu);
        //El item de menú Aceptar contacto aparecerá invisible por defecto.
        menu.findItem(R.id.itemNewContact).setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int edad=0;
        String prefijo = getString(R.string.hintPrefijo);
        switch (item.getItemId()){
            //Creación del alumno.
            case R.id.itemNewContact:
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
                ListaFragment.listaAlumnos.add(newAlumno);
                //Se sale del creador de alumnos.
                onBackPressed();
            case R.id.itemCancelContact:
                //Se sale del creador de alumnos sin añadir ningún alumno.
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
