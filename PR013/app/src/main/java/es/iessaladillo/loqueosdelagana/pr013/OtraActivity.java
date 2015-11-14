package es.iessaladillo.loqueosdelagana.pr013;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class OtraActivity extends AppCompatActivity {

    public static final String EXTRA_TEXTO = "extraTexto";
    private TextView lblMensaje;
    String mTexto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otra);
        intent= getIntent();
        mTexto= intent.getStringExtra(EXTRA_TEXTO);
        initViews();

    }

    private void initViews() {
        lblMensaje= (TextView) findViewById(R.id.lblMensaje);
        lblMensaje.setText(mTexto);
    }


}
