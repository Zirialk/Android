package com.example.usuario.pr018listviewsimple;

import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
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
    private Adaptador adaptador;
    private ArrayList<Alumno> alumnos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState==null)
        alumnos = new ArrayList<Alumno>();
        else{
            //OnRestoreInstance manual, porque el onrestore original no funciona
            mEstadoLista= savedInstanceState.getParcelable(STATE_LISTA);
            alumnos= savedInstanceState.getParcelableArrayList(STATE_DATOS); //Se recupera el array.

        }


        initView();
    }

    private void initView() {
        lstAlumnos = (ListView) findViewById(R.id.lstAlumnos);
        btnAgregar = (Button) findViewById(R.id.btnAgregar);
        txtNombre = (EditText) findViewById(R.id.txtNombre);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                agregar(txtNombre.getText().toString());
            }
        });
        lstAlumnos.setEmptyView(findViewById(R.id.lblNoHayAlumnos));

        adaptador = new Adaptador(this, alumnos);
        lstAlumnos.setAdapter(adaptador);
        configurarLst();

        lstAlumnos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                adaptador.remove((Alumno) parent.getItemAtPosition(position));
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

    private void configurarLst() {
        lstAlumnos.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        lstAlumnos.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                mode.setTitle(lstAlumnos.getCheckedItemCount()+"/"+lstAlumnos.getCount());
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {

                mode.getMenuInflater().inflate(R.menu.menu_main, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_delete:
                        ArrayList<Alumno> alumnosSeleccionados= getAlumnosSeleccionados(lstAlumnos,true);
                        for(Alumno alumno : alumnosSeleccionados)
                            adaptador.remove(alumno);

                        break;
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
            }
        });
    }
    private ArrayList<Alumno> getAlumnosSeleccionados(ListView lstAlumnos, boolean uncheck){
        ArrayList<Alumno> alumnos = new ArrayList<>();
        SparseBooleanArray selec = lstAlumnos.getCheckedItemPositions();
        int position;

        for (int i = 0; i < selec.size(); i++) {
            if(selec.valueAt(i)){
                position=selec.keyAt(i);

                if(uncheck)
                    lstAlumnos.setItemChecked(position,false);

                alumnos.add((Alumno)lstAlumnos.getItemAtPosition(selec.keyAt(i)));
            }

        }

        return alumnos;
    }




    public void agregar(String nombre) {
        adaptador.add(new Alumno(nombre));
        txtNombre.setText("");
    }

    public void checkDatos(String nombre) {
        btnAgregar.setEnabled(!TextUtils.isEmpty(nombre));
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mEstadoLista= lstAlumnos.onSaveInstanceState();
        outState.putParcelable(STATE_LISTA,mEstadoLista);
        outState.putParcelableArrayList(STATE_DATOS, alumnos); //Guarda el array en el Bundle para despues recuperarlo
    }



    @Override
    protected void onResume() {
        super.onResume();
        if(mEstadoLista!=null)
            lstAlumnos.onRestoreInstanceState(mEstadoLista);
    }
        }

