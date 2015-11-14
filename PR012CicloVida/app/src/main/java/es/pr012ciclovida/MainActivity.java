package es.pr012ciclovida;

import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String STATE_CONTADOR = "estadoContador";
    private int mContador=0;
    private TextView lblContador;
    private Button btnNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Si hay un estado guardado, se restablecen los valores salvados introduciendo su Key.
        if(savedInstanceState!=null)
            mContador=savedInstanceState.getInt(STATE_CONTADOR);
        setContentView(R.layout.activity_main);
        initViews();

    }

    private void initViews() {

        lblContador= (TextView) findViewById(R.id.lblNum);
        btnNum= (Button) findViewById(R.id.btnNum);
        lblContador.setText(mContador+"");
        btnNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContador++;
                lblContador.setText(mContador+"");
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_CONTADOR,mContador);
    }

}
