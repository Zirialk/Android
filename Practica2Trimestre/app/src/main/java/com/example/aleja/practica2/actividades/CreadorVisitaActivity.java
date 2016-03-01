package com.example.aleja.practica2.actividades;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.aleja.practica2.R;
import com.example.aleja.practica2.bdd.DAO;
import com.example.aleja.practica2.modelos.Visita;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class CreadorVisitaActivity extends AppCompatActivity {

    public static final String INTENT_ID_ALUMNO = "intent_alumno";
    public static final int RC_CREADOR_VISITA = 233;
    private static final String INTENT_VISITA = "Edicion visita";
    private Visita mVisita;
    private int mIdAlumno;

    private Toolbar toolbar;
    private EditText txtDia;
    private EditText txtHoraInicio;
    private EditText txtHoraFin;
    private EditText txtResumen;

    public static void startActivityForResult(Activity activity, Visita visita, int requestCode){
        Intent intent = new Intent(activity, CreadorVisitaActivity.class);
        intent.putExtra(INTENT_VISITA,visita);

        activity.startActivityForResult(intent, requestCode);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creador_visita);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        initViews();
        //Modo Edición
        if( (mVisita = getIntent().getParcelableExtra(INTENT_VISITA)) != null){
            mIdAlumno = mVisita.getIdAlumno();
            rellenarCampos();
        }
        //Modo Creación
        else
            mIdAlumno = getIntent().getIntExtra(INTENT_ID_ALUMNO, -1);
    }

    private void rellenarCampos() {
        SimpleDateFormat formatHoras = new SimpleDateFormat("HH:mm", Locale.getDefault());
        txtDia.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(mVisita.getDia()));
        txtHoraInicio.setText(formatHoras.format(mVisita.getHoraInicio()));
        txtHoraFin.setText(formatHoras.format(mVisita.getHoraFin()));
        txtResumen.setText(mVisita.getResumen());
    }

    private void initViews() {
        txtResumen = (EditText) findViewById(R.id.txtResumen);
        txtDia = (EditText) findViewById(R.id.txtDia);
        txtHoraInicio = (EditText) findViewById(R.id.txtHoraInicio);
        txtHoraFin = (EditText) findViewById(R.id.txtHoraFin);
        confPicker(txtDia, getString(R.string.labelPickerFecha), InputType.TYPE_DATETIME_VARIATION_DATE);
        confPicker(txtHoraInicio, getString(R.string.labelpickerHoraInicio), InputType.TYPE_DATETIME_VARIATION_TIME);
        confPicker(txtHoraFin, getString(R.string.labelPickerHoraFin), InputType.TYPE_DATETIME_VARIATION_TIME);
    }

    //Comprueba que los campos mínimos estén rellenos.
    private boolean comprobarDatos(){
        if( !txtDia.getText().toString().isEmpty() && !txtHoraInicio.getText().toString().isEmpty() && !txtHoraFin.getText().toString().isEmpty())
            return true;
        else
            return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_creador_visita, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_save:
                boolean confirmado = false;
                if(comprobarDatos()){
                    //Modo Crear
                    if(mVisita == null){
                        if(DAO.getInstance(this).createVisita(createVisitaFromForms()) != -1);
                            confirmado = true;
                    //Modo Edición
                    }else
                        if(DAO.getInstance(this).updateVisita(createVisitaFromForms()) > 0)
                            confirmado = true;

                    //Si se ha conseguido crear o actualizar se saldrá del creador
                    //sino le mostrará un Toast mostrandole que no es posible crear la visita en ese intervalo.
                    if(confirmado)
                        onBackPressed();
                    else
                        Toast.makeText(this, "Ya existe una visita en este intervalo", Toast.LENGTH_SHORT).show();
                }
                break;
        }

        return true;
    }
    private Visita createVisitaFromForms() {
        Visita visita = null;
        try {
            SimpleDateFormat formatHora = new SimpleDateFormat("HH:mm", Locale.getDefault());
            Date dateDia = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(txtDia.getText().toString());
            Date dateHoraInicio = formatHora.parse(txtHoraInicio.getText().toString());
            Date dateHoraFin = formatHora.parse(txtHoraFin.getText().toString());
            visita = new Visita(mIdAlumno, dateDia, dateHoraInicio, dateHoraFin, txtResumen.getText().toString());
            //Si se está editando.
            if(mVisita != null)
                visita.setId(mVisita.getId());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return visita;
    }

    //Permite que los editText muestren un dialogo al ser pulsados para establecer la hora o la fecha.
    public void confPicker(final EditText txt, final String titulo, final int inputType){

        txt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View v) {
                Calendar currentDate=Calendar.getInstance();

                //Si el editText es de tipo fecha.
                if(inputType == InputType.TYPE_DATETIME_VARIATION_DATE){
                    int year, mont, day;
                    String[] fechaRecuperada;

                    //Si el txt está vacio se mostrará por defecto la fecha actual.
                    if(txt.getText().toString().isEmpty()){
                        year = currentDate.get(Calendar.YEAR);
                        mont = currentDate.get(Calendar.MONTH);
                        day = currentDate.get(Calendar.DAY_OF_MONTH);
                    }else{
                        //Por el contrario, si ya contiene una fecha, mostrará esa por defecto para elegir a partir de esa.
                        fechaRecuperada = txt.getText().toString().split("/");
                        year = Integer.valueOf( fechaRecuperada[2] );
                        mont = Integer.valueOf( fechaRecuperada[1] )-1;
                        day = Integer.valueOf( fechaRecuperada[0] );
                    }

                    DatePickerDialog datePicker=new DatePickerDialog(CreadorVisitaActivity.this, new DatePickerDialog.OnDateSetListener() {
                        public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                            txt.setText(String.format("%d/%d/%d", selectedday, selectedmonth+1, selectedyear));
                        }
                    },year, mont, day);
                    datePicker.setTitle(titulo);
                    datePicker.show();

                }else if(inputType == InputType.TYPE_DATETIME_VARIATION_TIME){
                    int hora;
                    int minutos;

                    //Si el txt está vacio se mostrará por defecto la hora actual.
                    if(txt.getText().toString().isEmpty()){
                        hora = currentDate.get(Calendar.HOUR_OF_DAY);
                        minutos = currentDate.get(Calendar.MINUTE);
                    }else{
                        //Por el contrario, si ya contiene una hora, mostrará esa por defecto para elegir a partir de esa.
                        hora = Integer.valueOf( txt.getText().toString().split(":")[0] );
                        minutos = Integer.valueOf( txt.getText().toString().split(":")[1] );
                    }

                    TimePickerDialog timePicker = new TimePickerDialog(CreadorVisitaActivity.this, new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            //Si los minutos son de 1 sola cifra, le añadirá un 0 antes del número.
                            txt.setText(String.format("%d:%s", hourOfDay, minute<10 ? "0"+minute : minute));
                        }
                    },hora, minutos, true);

                    timePicker.setTitle(titulo);
                    timePicker.show();
                }


            }
        });
    }
    public void finish() {
        setResult(RESULT_OK, new Intent());
        super.finish();
    }

}
