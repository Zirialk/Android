package com.example.usuario.pr025dialog;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;

public class MainActivity extends AppCompatActivity implements MyFragmentDialog.MiDialogListener, MyFragmentDialog2.MiDialogListener2,
        MyFragmentDialog3.MiDialogListener, FragmentDialogSeleccionSimple.FragmentDialogListener, FragmentDialogMultiple.FragmentDialogListener
        , FragmentDialogPersonalizado.PersonalizadoListener, DatePickerDialog.OnDateSetListener {

    @Bind(R.id.btnPulsame)
    Button btnPulsame;
    @Bind(R.id.btnSiNo)
    Button btnSiNo;
    @Bind(R.id.btnEquipo)
    Button btnEquipo;
    @Bind(R.id.btnSeleccionSimple)
    Button btnSeleccionSimple;
    @Bind(R.id.btnMultiple)
    Button btnMultiple;
    @Bind(R.id.btnLayoutPersonalizado)
    EditText btnLayoutPersonalizado;
    @Bind(R.id.txtDatePicker)
    EditText txtDatePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        btnMultiple.requestFocus();


    }

    @OnClick(R.id.btnPulsame)
    public void pulsame() {
        new MyFragmentDialog().show(getSupportFragmentManager(), "Lo que quiera TAG");
    }

    @OnClick(R.id.btnSiNo)
    public void pulsame2() {
        new MyFragmentDialog2().show(getSupportFragmentManager(), "Lo que quiera TAG2");
    }

    @OnClick(R.id.btnEquipo)
    public void pulsame3() {
        new MyFragmentDialog3().show(getSupportFragmentManager(), "Lo que quiera TAG3");
    }

    @OnClick(R.id.btnSeleccionSimple)
    public void pulsameSeleccionSimple() {
        new FragmentDialogSeleccionSimple().show(getSupportFragmentManager(), "Lo que quiera TAGSimple");
    }

    @OnClick(R.id.btnMultiple)
    public void pulsameSeleccionMultiple() {
        new FragmentDialogMultiple().show(getSupportFragmentManager(), "Lo que quiera TAGSimple");
    }

    @OnFocusChange(R.id.btnLayoutPersonalizado)
    public void focusPersonalizado() {
        if (btnLayoutPersonalizado.hasFocus())
            new FragmentDialogPersonalizado().show(getSupportFragmentManager(), "Jaja");
    }
    @OnFocusChange(R.id.txtDatePicker)
    public void focusDatePicker() {
        if (txtDatePicker.hasFocus())
            new FragmentDialogDataPicker().show(getSupportFragmentManager(), "JajaDate");
    }

    @Override
    public void onNeutralButtonClick(DialogFragment dialog) {
        if (dialog instanceof MyFragmentDialog)
            Toast.makeText(this, "Ha aceptado", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPositiveButtonClick(DialogFragment dialog) {
        Toast.makeText(this, "Se ha borrado el alumno", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNegativeButtonClick(DialogFragment dialog) {
        Toast.makeText(this, "No se ha borrado ningún alumno.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onItemClick(DialogFragment dialog, String elegido) {
        Toast.makeText(this, elegido + " seleccionado", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMultipleItemClick(DialogFragment dialog, boolean[] equiposCheckeds) {
        for (int i = 0; i < equiposCheckeds.length; i++)
            if (equiposCheckeds[i])
                Toast.makeText(this, getResources().getTextArray(R.array.equipos)[i] + " seleccionado", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPersonalizadoButtonClick(DialogFragment dialog, String texto) {
        btnLayoutPersonalizado.setText(texto);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            txtDatePicker.setText(year+"/"+(monthOfYear+1)+"/"+dayOfMonth);
    }
}
