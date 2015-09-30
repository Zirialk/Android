package es.saludo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {

    private CheckBox chkEducado;
    private EditText txtNombre;
    private Button btnSaludar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

    }

    private void initViews() {
        btnSaludar = (Button) findViewById(R.id.btnSaludar);
        txtNombre= (EditText) findViewById(R.id.txtNombre);
        chkEducado= (CheckBox)findViewById(R.id.chkEducado);
        chkEducado.setOnCheckedChangeListener(this);
    }
    public void saludar(View v){
        String mensaje = getString(R.string.saludo) +" "+ txtNombre.getText().toString();;

        if(chkEducado.isChecked())
            mensaje=getString(R.string.saludoEducado) +" "+ txtNombre.getText().toString();

        Toast.makeText(this,mensaje,Toast.LENGTH_LONG).show();




    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        String mensaje= getString(R.string.ModoNoEducado);

        if(chkEducado.isChecked())
            mensaje= getString(R.string.ModoEducado);

        Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
    }
}
