package com.example.usuario.pr033recyclerview;

import android.os.Parcel;


public class Alumno extends ListItem{



    private String nombre;
    public Alumno(String nombre){
        this.nombre=nombre;
    }


    @Override
    public int getType() {
        return ListItem.TYPE_CHILD;
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nombre);
    }

    protected Alumno(Parcel in) {
        this.nombre = in.readString();
    }

    public static final Creator<Alumno> CREATOR = new Creator<Alumno>() {
        public Alumno createFromParcel(Parcel source) {
            return new Alumno(source);
        }

        public Alumno[] newArray(int size) {
            return new Alumno[size];
        }
    };
}
