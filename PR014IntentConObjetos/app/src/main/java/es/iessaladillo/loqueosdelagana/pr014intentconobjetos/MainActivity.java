package es.iessaladillo.loqueosdelagana.pr014intentconobjetos;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static es.iessaladillo.loqueosdelagana.pr014intentconobjetos.OtraActivity.KEY_ALUMNO;

public class MainActivity extends AppCompatActivity {
    public static int CODE_RC=1;
    private EditText txtDni;
    private Button btnObtener;
    private TextView lblResultadoEdad;
    private TextView lblResultadoNombre;
    private TextView lblResultadoGenero;
    private Alumno mAlumno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        if (savedInstanceState != null) {
            mAlumno = savedInstanceState.getParcelable(KEY_ALUMNO);
            alumnoToViews();
        }else {
            mAlumno = new Alumno();
        }



    }

    private void initViews() {
        txtDni = (EditText)findViewById(R.id.txtDni);
        lblResultadoNombre= (TextView) findViewById(R.id.lblResultadoNombre);
        lblResultadoEdad= (TextView) findViewById(R.id.lblResultadoEdad);
        lblResultadoGenero= (TextView) findViewById(R.id.lblResultadoGenero);
        btnObtener= (Button) findViewById(R.id.btnObtener);
        btnObtener.setEnabled(false);
        btnObtener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarAlumno();
            }
        });
        txtDni.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                btnObtener.setEnabled(!TextUtils.isEmpty(txtDni.getText().toString()));
            }
        });
    }

    private void alumnoToViews() {
        lblResultadoNombre.setText(mAlumno.getNombre());
        lblResultadoEdad.setText(String.valueOf(mAlumno.getEdad()));
        lblResultadoGenero.setText(mAlumno.getSexo());
    }
    private void viewToAlumno() {
        mAlumno.setDni(txtDni.getText().toString());
    }

    private void enviarAlumno() {
        Intent intent = new Intent(this, OtraActivity.class);
        intent.putExtra(KEY_ALUMNO, mAlumno);
        startActivityForResult(intent, CODE_RC);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode==RESULT_OK && requestCode==CODE_RC){
            mAlumno=data.getParcelableExtra(KEY_ALUMNO);
            alumnoToViews();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(KEY_ALUMNO,mAlumno);
        super.onSaveInstanceState(outState);
    }
}
