package es.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private RadioGroup rgEstaciones;
    private ImageView imgEstacion;
    private Button btnMeGusta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        rgEstaciones.setOnCheckedChangeListener(this);
        rgEstaciones.check(R.id.rbPrimavera); // Por defecto
        btnMeGusta.setOnClickListener(this);
    }

    private void initViews() {
        rgEstaciones= (RadioGroup) findViewById(R.id.rgEstaciones);
        imgEstacion= (ImageView) findViewById(R.id.imgEstacion);
        btnMeGusta= (Button) findViewById(R.id.btnMeGusta);
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.rbPrimavera:
                imgEstacion.setImageResource(R.drawable.primavera);
                break;
            case R.id.rbVerano:
                imgEstacion.setImageResource(R.drawable.verano);
                break;
            case R.id.rbOtono:
                imgEstacion.setImageResource(R.drawable.otono);
                break;
            case R.id.rbInvierno:
                imgEstacion.setImageResource(R.drawable.a);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        String mensaje= getString(R.string.meGusta);

        //Ejecuta un bloque de código dependiendo del botón pulsado
        switch(v.getId()){
            case R.id.btnMeGusta:
                //Muestra un Toast diferente dependiendo del RadioButton marcado.
                switch (rgEstaciones.getCheckedRadioButtonId()) {
                    case R.id.rbPrimavera:
                        mensaje+=" la "+getString(R.string.primavera);
                        break;
                    case R.id.rbVerano:
                        mensaje+=" el "+ getString(R.string.verano);
                        break;
                    case R.id.rbOtono:
                        mensaje+=" el "+ getString(R.string.otono);
                        break;
                    case R.id.rbInvierno:
                        mensaje+=" el "+ getString(R.string.invierno);
                        break;
                }
                Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
