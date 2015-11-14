package es.iessaladillo.loqueosdelagana.pr013;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {



    private EditText txtMensaje;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        txtMensaje= (EditText)findViewById(R.id.txtMensaje);
        findViewById(R.id.btnEnviar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviar(txtMensaje.getText().toString());

            }
        });
        findViewById(R.id.btnMostrarEnOtra).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarEnOtra(txtMensaje.getText().toString());

            }
        });

    }

    private void mostrarEnOtra(String mensaje) {
        Intent intent = new Intent(this,OtraActivity.class);
        intent.putExtra(OtraActivity.EXTRA_TEXTO,mensaje);
        startActivity(intent);
    }

    private void enviar(String mensaje) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, mensaje);
        startActivity(intent);
    }


}
