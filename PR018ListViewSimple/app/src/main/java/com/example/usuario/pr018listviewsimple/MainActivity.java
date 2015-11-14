package com.example.usuario.pr018listviewsimple;

import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String STATE_LISTA = "estadoLista";
    private static final String STATE_DATOS = "estadoDatos";
    private Parcelable mEstadoLista;
    private ListView lstAlumnos;
    private Button btnAgregar;
    private EditText txtNombre;
    private ArrayAdapter<String> adaptador;
    private ArrayList<String> alumnos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState==null)
            alumnos= new ArrayList<String>();
        else{
            //OnRestoreInstance manual, porque el onrestore original no funciona
            mEstadoLista= savedInstanceState.getParcelable(STATE_LISTA);
            alumnos= savedInstanceState.getStringArrayList(STATE_DATOS); //Se recupera el array.
        }


        initView();
    }

    private void initView() {
        lstAlumnos= (ListView) findViewById(R.id.lstAlumnos);
        btnAgregar= (Button) findViewById(R.id.btnAgregar);
        txtNombre= (EditText) findViewById(R.id.txtNombre);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                agregar(txtNombre.getText().toString());
            }
        });
        lstAlumnos.setEmptyView(findViewById(R.id.lblNoHayAlumnos));

        adaptador=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,alumnos);
        lstAlumnos.setAdapter(adaptador);

        lstAlumnos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                adaptador.remove((String) parent.getItemAtPosition(position));
                return true;
            }
        });
        txtNombre.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkDatos(s.toString());
            }

        });

    }
    public void agregar(String nombre){
        adaptador.add(nombre);
        txtNombre.setText("");
    }
    public void checkDatos(String nombre){
        btnAgregar.setEnabled(!TextUtils.isEmpty(nombre));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mEstadoLista= lstAlumnos.onSaveInstanceState();
        outState.putParcelable(STATE_LISTA,mEstadoLista);
        outState.putStringArrayList(STATE_DATOS,alumnos); //Guarda el array en el Bundle para despues recuperarlo
    }



    @Override
    protected void onResume() {
        super.onResume();
        if(mEstadoLista!=null)
            lstAlumnos.onRestoreInstanceState(mEstadoLista);
    }
}
