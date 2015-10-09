package es.pr008calculopropina;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private final float defaultFloat= 0.00f;
    private final int defaultPorcentajePropina=0;
    private final int defaultComensales=1;
    private final String uno="1";

    private EditText txtCuenta;
    private EditText txtPropina;
    private EditText txtPorcentajePropina;
    private EditText txtTotal;
    private EditText txtComensales;
    private EditText txtPorComensal;
    private TextView lblCuenta;
    private TextView lblPorcentajePropina;
    private TextView lblComensales;
    private Button btnCalcular;
    private Button btnRedondear;
    private Button btnRedondear2;
    private Button btnLimpiar;
    private Button btnLimpiar2;
    private String simboloDecimal;
    private NumberFormat formateador;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        DecimalFormatSymbols decimalSymbols = new DecimalFormatSymbols();
        simboloDecimal = decimalSymbols.getDecimalSeparator() + "";
        formateador = NumberFormat.getInstance(Locale.getDefault());

    }

    private void initViews() {
        btnCalcular= (Button) findViewById(R.id.btnCalcular);
        btnRedondear= (Button) findViewById(R.id.btnRedondear);
        btnRedondear2= (Button) findViewById(R.id.btnRedondear2);
        btnLimpiar= (Button) findViewById(R.id.btnLimpiar);
        btnLimpiar2= (Button) findViewById(R.id.btnLimpiar2);
        lblCuenta= (TextView) findViewById(R.id.lblCuenta);
        lblPorcentajePropina= (TextView) findViewById(R.id.lblPorcentajePropina);
        lblComensales= (TextView) findViewById(R.id.lblComensales);
        txtCuenta= (EditText) findViewById(R.id.txtCuenta);
        txtPropina= (EditText) findViewById(R.id.txtPropina);
        txtPorcentajePropina= (EditText) findViewById(R.id.txtPorcentajePropina);
        txtTotal= (EditText) findViewById(R.id.txtTotal);
        txtComensales= (EditText) findViewById(R.id.txtComensales);
        txtPorComensal= (EditText) findViewById(R.id.txtPorComensal);
        txtCuenta.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                cambiarColorLbl(lblCuenta,hasFocus);
            }
        });
        txtPorcentajePropina.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                cambiarColorLbl(lblPorcentajePropina, hasFocus);

            }
        });
        txtComensales.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                cambiarColorLbl(lblComensales,hasFocus);
            }
        });
        btnCalcular.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                calcularCuenta();
            }
        });
        btnRedondear.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                redondear();
            }
        });
        btnRedondear2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                redondearComensal();
            }
        });
        btnLimpiar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                txtCuenta.setText(Float.toString(defaultFloat));
                txtPropina.setText(Float.toString(defaultFloat));
                txtTotal.setText(Float.toString(defaultFloat));
                txtPorComensal.setText(Float.toString(defaultFloat));
                txtPorcentajePropina.setText(Integer.toString(defaultPorcentajePropina));
            }
        });
        btnLimpiar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtComensales.setText(Integer.toString(defaultComensales));
                txtPorComensal.setText(Float.toString(defaultFloat));
            }
        });

    }

    private void redondear(){
        try {
            float comensales,porComensal;
            float total = (formateador.parse(txtTotal.getText().toString()).floatValue());
            float nuevoTotal = (float) Math.floor(total);
            if(nuevoTotal != total)
                nuevoTotal++;

            comensales=(formateador.parse(txtComensales.getText().toString()).floatValue());
            porComensal= nuevoTotal/comensales;

            txtTotal.setText(String.format(Locale.getDefault(), "%.2f",
                    nuevoTotal));
            txtPorComensal.setText(String.format(Locale.getDefault(), "%.2f",
                    porComensal));

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    private void redondearComensal(){
        try {
            float porComensal = (formateador.parse(txtPorComensal.getText().toString())).floatValue();
            float nuevoPorComensal = (float) Math.floor(porComensal);
            if (nuevoPorComensal != porComensal) 
                nuevoPorComensal++;
            
            txtPorComensal.setText(String.format(Locale.getDefault(), "%.2f", nuevoPorComensal));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    private void calcularCuenta(){
        float cuenta;
        int porcentaje;
        float propina,total,propina2;

        txtTotal.setText(uno);
        txtPropina.setText(uno);
        txtPorComensal.setText(uno);


        cuenta = Float.parseFloat(txtCuenta.getText().toString());
        porcentaje= Integer.parseInt(txtPorcentajePropina.getText().toString());
        propina = Float.parseFloat(txtPropina.getText().toString());
        propina2= (cuenta * porcentaje) / 100;
        total=cuenta + propina2;
        txtPropina.setText(String.format("%.2f",propina2));
        txtTotal.setText(String.format("%.2f",total));
        txtPorComensal.setText(String.format("%.2f", total/Float.parseFloat(txtComensales.getText().toString())));
    }
    private void cambiarColorLbl(TextView lbl, boolean hasFocus){
        if(hasFocus)
            lbl.setTextColor(getResources().getColor(R.color.accent));
        else
            lbl.setTextColor(getResources().getColor(R.color.primary_text));
    }


}
