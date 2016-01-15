package com.example.usuario.pr033recyclerview;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements Adapter.OnItemClickListener{

    private RecyclerView rvAlumnos;
    private Adapter mAdaptador;
    private LinearLayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        configRecyclerView();
    }

    private void configRecyclerView() {
        rvAlumnos = (RecyclerView) findViewById(R.id.rvAlumnos);
        rvAlumnos.setHasFixedSize(true);
        mAdaptador = new Adapter(DB.getListItems());
        mAdaptador.setOnItemClickListener(this);
        rvAlumnos.setAdapter(mAdaptador);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvAlumnos.setLayoutManager(mLayoutManager);
        rvAlumnos.setItemAnimator(new DefaultItemAnimator());
    }
    // Agrega un alumno a la lista.
    private void agregarAlumno(Alumno alumno) {
        // Se agrega el alumno.
        mAdaptador.addItem(alumno);
        rvAlumnos.scrollToPosition(mAdaptador.getItemCount() - 1);
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

    @Override
    public void onItemClick(View view, Alumno alumno, int position) {
        Snackbar.make(rvAlumnos, "Ha seleccionado el alumno "+ alumno.getNombre(),
                Snackbar.LENGTH_SHORT).show();
    }
}
