package es.pr005linearlayout;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnAceptar;
    private Button btnCancelar;
    private EditText txtUsuario;
    private EditText txtClave;
    private TextView lblUsuario;
    private TextView lblClave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

    }

    private void initViews() {
        btnAceptar= (Button) findViewById(R.id.btnAceptar);
        btnAceptar.setOnClickListener(this);
        btnCancelar= (Button) findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(this);
        lblUsuario= (TextView)findViewById(R.id.lblUsuario);
        lblClave= (TextView)findViewById(R.id.lblClave);
        txtUsuario = (EditText) findViewById(R.id.txtUsuario);
        txtClave = (EditText) findViewById(R.id.txtClave);

        //Cuando uno de estos dos EditText obtienen el FOCUS
        txtUsuario.setOnFocusChangeListener(new OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                cambiarColor(lblUsuario,hasFocus);
            }
        });
        txtClave.setOnFocusChangeListener(new OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                cambiarColor(lblClave, hasFocus);
            }
        });

        txtUsuario.addTextChangedListener(new TextWatcher() {

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            //Despues de escribir en el Usuario
            public void afterTextChanged(Editable s) {
                activarAceptar();
                lblVisible(txtUsuario, lblUsuario);
            }
        });
        txtClave.addTextChangedListener(new TextWatcher() {

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            //Despues de escribir en la Clave
            public void afterTextChanged(Editable s) {
                activarAceptar();
                lblVisible(txtClave, lblClave);
            }
        });
    }
    public void onClick(View v){
        //Depende del boton que inicie el método se ejecutara un caso del switch()
        switch (v.getId()){
            case R.id.btnCancelar:
                resetTxt();
                break;
            case R.id.btnAceptar:
                Toast.makeText(this,getString(R.string.conectando)+" "+ txtUsuario.getText()+"...",Toast.LENGTH_SHORT).show();
                break;
        }
    }
    private void resetTxt(){
        txtUsuario.setText("");
        txtClave.setText("");
        btnAceptar.setEnabled(false);
    }
    private void activarAceptar(){
        //Si ni el Usuario ni la Clave están vacias, se podrá usar el botón Aceptar
        if(!TextUtils.isEmpty(txtUsuario.getText()) && !TextUtils.isEmpty(txtClave.getText()))
            btnAceptar.setEnabled(true);

    }
    private void lblVisible(EditText txt, TextView lbl){
        if(TextUtils.isEmpty(txt.getText()))
            lbl.setVisibility(View.INVISIBLE);
        else
            lbl.setVisibility(View.VISIBLE);
    }
    private void cambiarColor(TextView lbl, boolean focus){
        if(focus)
            lbl.setTextColor(Color.GREEN);

    }
}
