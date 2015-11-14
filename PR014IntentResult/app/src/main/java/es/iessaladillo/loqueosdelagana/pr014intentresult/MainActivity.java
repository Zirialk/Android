package es.iessaladillo.loqueosdelagana.pr014intentresult;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public static int CODE_RC=1;
    private EditText txtDni;
    private Button btnObtener;
    private TextView lblResultadoEdad;
    private TextView lblResultadoNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

    }

    private void initViews() {
        txtDni = (EditText)findViewById(R.id.txtDni);
        lblResultadoNombre= (TextView) findViewById(R.id.lblResultadoNombre);
        lblResultadoEdad= (TextView) findViewById(R.id.lblResultadoEdad);
        btnObtener= (Button) findViewById(R.id.btnObtener);
        btnObtener.setEnabled(false);
        btnObtener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enviarDni();
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
    private void enviarDni() {
        Intent intent = new Intent(this, OtraActivity.class);

            intent.putExtra(OtraActivity.KEY_DNI,txtDni.getText().toString());
            startActivityForResult(intent, CODE_RC);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK && requestCode==CODE_RC){
            lblResultadoNombre.setText(data.getStringExtra(OtraActivity.KEY_NOMBRE).toString());
            lblResultadoEdad.setText(data.getStringExtra(OtraActivity.KEY_EDAD).toString());
        }

    }
}
