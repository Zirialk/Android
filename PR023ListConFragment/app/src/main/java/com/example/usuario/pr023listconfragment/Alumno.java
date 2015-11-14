package com.example.usuario.pr023listconfragment;

import android.os.Parcel;
import android.os.Parcelable;


public class Alumno implements Parcelable{
    private String nombre;
    private int edad;
    private String localidad;
    private String calle;
    private String avatar;

    public Alumno(String nombre, int edad, String localidad,String calle, String rutaAvatar){
        this.nombre=nombre;
        this.edad=edad;
        this.localidad=localidad;
        this.calle=calle;
        avatar= rutaAvatar;

    }

    protected Alumno(Parcel in) {
        readFromParcel(in);
    }
    //Lee desde el Parcel los atributos del Alumno y se las asigna.
    private void readFromParcel(Parcel in) {
        nombre = in.readString();
        edad = in.readInt();
        localidad = in.readString();
        avatar = in.readString();
        calle = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nombre);
        dest.writeInt(edad);
        dest.writeString(localidad);
        dest.writeString(avatar);
        dest.writeString(calle);
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public String getLocalidad() {
        return localidad;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getCalle() {
        return calle;
    }


    @Override
    public int describeContents() {
        return 0;
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
}
