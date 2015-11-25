package com.example.usuario.pr023listconfragment;


import android.graphics.BitmapFactory;
import android.widget.ImageView;

public class Escalador implements Runnable {

    private String realPathImage;
    private ImageView imgView;

    public Escalador(String realPathImage,ImageView imgView){
        this.realPathImage=realPathImage;
        this.imgView=imgView;
    }
    @Override
    public void run() {
        // Se obtiene el tamaño de la vista de destino.
        int anchoImageView = imgView.getWidth();
        int altoImageView = imgView.getHeight();

        // Se obtiene el tamaño de la imagen.
        BitmapFactory.Options opciones = new BitmapFactory.Options();
        opciones.inJustDecodeBounds = true; // Solo para cálculo.
        BitmapFactory.decodeFile(realPathImage, opciones);
        int anchoFoto = opciones.outWidth;
        int altoFoto = opciones.outHeight;
        // Se obtiene el factor de escalado para la imagen.
        int  factorEscalado = Math.min(anchoFoto/anchoImageView, altoFoto/altoImageView);

        // Se escala la imagen con dicho factor de escalado.
        opciones.inJustDecodeBounds = false; // Se escalará.
        opciones.inSampleSize = factorEscalado;

        imgView.setImageBitmap(BitmapFactory.decodeFile(realPathImage, opciones));

    }
}
