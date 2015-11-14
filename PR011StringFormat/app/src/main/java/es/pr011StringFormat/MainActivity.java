package es.pr011StringFormat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText txtSuspensos;
    private Button btnVer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        txtSuspensos=(EditText) findViewById(R.id.txtSuspensos);
        btnVer=(Button) findViewById(R.id.btnVer);
        btnVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createToast();
            }
        });
    }
    private void createToast(){
        int num= Integer.parseInt(txtSuspensos.getText().toString());
        String mensaje=getResources().getQuantityString(R.plurals.suspensos,num,num);
        Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
    }

}
