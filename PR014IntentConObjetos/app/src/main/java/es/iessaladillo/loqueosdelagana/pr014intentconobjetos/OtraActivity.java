package es.iessaladillo.loqueosdelagana.pr014intentconobjetos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class OtraActivity extends AppCompatActivity {


    public static String KEY_ALUMNO="Alumno";
    private Button btnEnviar;
    private TextView lblDni;
    private TextView txtNombre;
    private TextView txtEdad;
    private Spinner spGenero;
    private Alumno mAlumn;
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
        spGenero= (Spinner) findViewById(R.id.spGenero);
    }
    private void recibirDatos() {
        Intent intent = getIntent();
        mAlumn=intent.getParcelableExtra(KEY_ALUMNO);
        if(mAlumn!=null)
            lblDni.setText(mAlumn.getDni());
    }

    @Override
    public void finish() {
        Intent intent = new Intent();
        mAlumn.setNombre(txtNombre.getText().toString());
        mAlumn.setEdad(Integer.parseInt(txtEdad.getText().toString()));
        mAlumn.setSexo(getResources().getStringArray(R.array.genero)[spGenero.getSelectedItemPosition()]);
        intent.putExtra(KEY_ALUMNO,mAlumn);
        setResult(RESULT_OK,intent);
        super.finish();
    }
}
