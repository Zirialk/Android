package com.example.usuario.pr020menu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView txtNombre;
    private int mItemSeleccionado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        txtNombre= (TextView) findViewById(R.id.txtNombre);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.mnuAprobar:
                aprobar();
                break;
            case R.id.mnuSuspender:
                suspender();
                break;
            default:
                return super.onOptionsItemSelected(item);

        }
        mItemSeleccionado= item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        for(int i=0;i<menu.size();i++)
                menu.getItem(i).setEnabled(true);

        if(mItemSeleccionado!=0)
            menu.findItem(mItemSeleccionado).setEnabled(false);


        return super.onPrepareOptionsMenu(menu);
    }

    private void suspender() {
        Toast.makeText(this, String.format("%s está suspendido", txtNombre.getText().toString()), Toast.LENGTH_LONG).show();
    }

    private void aprobar() {
        Toast.makeText(this, String.format("%s está aprobado", txtNombre.getText().toString()), Toast.LENGTH_LONG).show();
    }
}
