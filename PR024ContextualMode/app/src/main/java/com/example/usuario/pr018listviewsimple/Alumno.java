package com.example.usuario.pr018listviewsimple;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Usuario on 20/11/2015.
 */
public class Alumno implements Parcelable{
    String nombre;
    public Alumno(String nombre){
        this.nombre=nombre;
    }

    protected Alumno(Parcel in) {
        nombre = in.readString();
    }

    public static final Creator<Alumno> CREATOR = new Creator<Alumno>() {
        @Override
        public Alumno createFromParcel(Parcel in) {
            return new Alumno(in);
        }

        @Override
        public Alumno[] newArray(int size) {
            return new Alumno[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
    }
}
