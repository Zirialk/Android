package es.pr009textinputlayout;

import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private TextInputLayout tilNombre;
    private TextInputLayout tilTelefono;
    private TextInputLayout tilEmail;
    private EditText txtNombre;
    private EditText txtEmail;
    private EditText txtTelefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        tilNombre= (TextInputLayout) findViewById(R.id.tilNombre);
        tilTelefono= (TextInputLayout) findViewById(R.id.tilTelefono);
        tilEmail= (TextInputLayout) findViewById(R.id.tilEmail);
        txtNombre= (EditText)findViewById(R.id.txtNombre);
        txtTelefono= (EditText)findViewById(R.id.txtTelefono);
        txtEmail= (EditText)findViewById(R.id.txtEmail);

        txtTelefono.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(!TextUtils.isEmpty(txtTelefono.getText().toString()))
                    if(!validateTlf(txtTelefono.getText().toString()))
                        tilTelefono.setError("No es un número de teléfono valido");
                    else
                        tilTelefono.setErrorEnabled(false);
                else
                    tilTelefono.setErrorEnabled(false);

            }
        });


    }
    public boolean validateTlf(String tlf){
        if(tlf.length()<9)return false;
        if(!tlf.startsWith("6") && !tlf.startsWith("7") && !tlf.startsWith("8") && !tlf.startsWith("9"))return false;

        return true;
    }





}
