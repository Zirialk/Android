package com.example.usuario.pr050pruebafirebase;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseListAdapter;
import com.firebase.ui.FirebaseRecyclerAdapter;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    Firebase myFirebaseRef;
    private EditText txtMensaje;
    private Firebase mensajeFireBaseRef;
    private RecyclerView rvMensajes;
    private CustomAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mensajeFireBaseRef = new Firebase("https://pruebaandroid.firebaseio.com/mensajes");
        txtMensaje = (EditText) findViewById(R.id.txtMensaje);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Mensaje m = new Mensaje("titulo", txtMensaje.getText().toString());
                mensajeFireBaseRef.push().setValue(m);
            }
        });
        rvMensajes = (RecyclerView) findViewById(R.id.rvMensajes);
        rvMensajes.setHasFixedSize(true);
        rvMensajes.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new CustomAdapter()
        rvMensajes.setAdapter(mAdapter);
        cofigurarSwiped();
    }

    private void cofigurarSwiped() {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(
                        ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                        ItemTouchHelper.RIGHT) {

                    // Cuando se detecta un gesto drag & drop.
                    @Override
                    public boolean onMove(RecyclerView recyclerView,
                                          RecyclerView.ViewHolder viewHolder,
                                          RecyclerView.ViewHolder target) {

                        return false;
                    }

                    // Cuando se detecta un gesto swipe to dismiss.
                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        Firebase refAlumno = mAdapter.getRef(viewHolder.getAdapterPosition());
                        refAlumno.removeValue();
                    }
                });
// Se enlaza con el RecyclerView.
        itemTouchHelper.attachToRecyclerView(rvMensajes);
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

}
