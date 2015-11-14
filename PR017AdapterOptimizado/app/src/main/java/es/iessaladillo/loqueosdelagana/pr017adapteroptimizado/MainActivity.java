package es.iessaladillo.loqueosdelagana.pr017adapteroptimizado;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<Alumno> listaAlumno= new ArrayList<Alumno>();
    private ListView lvPrincipal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listaAlumno.add(new Alumno("Álex", 21, "620657202"));
        listaAlumno.add(new Alumno("Ismaé",21,"666555666"));
        listaAlumno.add(new Alumno("Manué",28,"982989899"));
        listaAlumno.add(new Alumno("Gabrié",22,"654987321"));
        listaAlumno.add(new Alumno("Juan Pabló",28,"6123456789"));
        initViews();
    }

    private void initViews() {
        lvPrincipal= (ListView) findViewById(R.id.lvPrincipal);
        lvPrincipal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Alumno alumno = (Alumno) parent.getItemAtPosition(position);
                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("+34"+ alumno.getTelefono()));
                startActivity(intent);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return super.onPrepareOptionsMenu(menu);
    }
}
