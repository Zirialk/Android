package com.example.usuario.pr045palette;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private ImageView imgFoto;
    private Palette mPaleta;
    private Toolbar toolbar;
    private Button btnCambiar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initViews();

    }

    private void initViews() {
        imgFoto = (ImageView) findViewById(R.id.imgFoto);
        btnCambiar =(Button) findViewById(R.id.btnCambiar);
        btnCambiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarFoto();
            }
        });
        cargarFoto();
    }

    private void cargarFoto(){
        Picasso.with(this).load("http://lorempixel.com/200/200/").into(imgFoto, new Callback() {
            @Override
            public void onSuccess() {
                obtenerPaleta();
            }

            @Override
            public void onError() {

            }
        });
    }

    private void obtenerPaleta() {
        Bitmap bitmap = ((BitmapDrawable) imgFoto.getDrawable()).getBitmap();
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                toolbar.setBackgroundColor(palette.getVibrantColor(0));
                toolbar.setTitleTextColor(palette.getVibrantSwatch().getTitleTextColor());
            }
        });
    }
}
