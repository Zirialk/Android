package es.iessaladillo.loqueosdelagana.pr014intentresult;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class OtraActivity extends AppCompatActivity {

    public static String KEY_DNI="DNI";
    public static String KEY_NOMBRE="Nombre";
    public static String KEY_EDAD="Edad";
    private Button btnEnviar;
    private TextView lblDni;
    private TextView txtNombre;
    private TextView txtEdad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otra);
        initView();
        recibirDatos();
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



    private void initView() {
        btnEnviar= (Button) findViewById(R.id.btnEnviar);
        lblDni= (TextView)findViewById(R.id.lblDni);
        txtNombre= (TextView) findViewById(R.id.txtNombre);
        txtEdad= (TextView) findViewById(R.id.txtEdad);
    }
    private void recibirDatos() {
        Intent intent = getIntent();
        Bundle extra= intent.getExtras();

        if(extra!=null)
            if(intent.hasExtra(KEY_DNI))
                lblDni.setText(extra.getString(KEY_DNI));
    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        intent.putExtra(KEY_NOMBRE,txtNombre.getText().toString());
        intent.putExtra(KEY_EDAD,txtEdad.getText().toString());
        setResult(RESULT_OK,intent);
        super.finish();
    }
}
