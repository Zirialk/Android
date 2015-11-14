package es.pr015listview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> nombres = new ArrayList<String>();
    private ListView lstNombres;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nombres.add("Ismaé");
        nombres.add("Álex");
        nombres.add("Manué");
        initViews();

    }

    private void initViews() {
        final ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, nombres);
        lstNombres= (ListView) findViewById(R.id.lstNombres);
        lstNombres.setAdapter(adaptador);
        lstNombres.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> lst, View view, int position, long id) {
                String nombre= (String) lst.getItemAtPosition(position);
                enviarDatos(nombre);


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    private void enviarDatos(String nombre) {
        Intent intent = new Intent(this,OtraActivity.class);
        intent.putExtra(OtraActivity.KEY_NOMBRE,nombre);

    }


}
