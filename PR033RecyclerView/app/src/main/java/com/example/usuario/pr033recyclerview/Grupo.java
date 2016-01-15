package com.example.usuario.pr033recyclerview;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Usuario on 15/01/2016.
 */
public class Grupo extends ListItem{



    private String nombre;


    public Grupo(String nombre) {
        this.nombre=nombre;
    }

    protected Grupo(Parcel in) {
        this.nombre = in.readString();
    }

    public static final Creator<Grupo> CREATOR = new Creator<Grupo>() {
        public Grupo createFromParcel(Parcel source) {
            return new Grupo(source);
        }

        public Grupo[] newArray(int size) {
            return new Grupo[size];
        }
    };
    @Override
    public int getType() {
        return ListItem.TYPE_HEADER;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nombre);
    }



    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
