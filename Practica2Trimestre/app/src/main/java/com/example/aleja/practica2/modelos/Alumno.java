package com.example.aleja.practica2.modelos;

import android.os.Parcel;
import android.os.Parcelable;

public class Alumno implements Parcelable {
    private int id;
    private String nombre;
    private String telefono;
    private String email;
    private String empresa;
    private String tutor;
    private String horario;
    private String direccion;
    private String foto;

    public Alumno(){

    }

    public Alumno(String nombre, String telefono, String email, String empresa, String tutor, String horario, String direccion, String foto) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.empresa = empresa;
        this.tutor = tutor;
        this.horario = horario;
        this.direccion = direccion;
        this.foto = foto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getTutor() {
        return tutor;
    }

    public void setTutor(String tutor) {
        this.tutor = tutor;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.nombre);
        dest.writeString(this.telefono);
        dest.writeString(this.email);
        dest.writeString(this.empresa);
        dest.writeString(this.tutor);
        dest.writeString(this.horario);
        dest.writeString(this.direccion);
        dest.writeString(this.foto);
    }

    protected Alumno(Parcel in) {
        this.id = in.readInt();
        this.nombre = in.readString();
        this.telefono = in.readString();
        this.email = in.readString();
        this.empresa = in.readString();
        this.tutor = in.readString();
        this.horario = in.readString();
        this.direccion = in.readString();
        this.foto = in.readString();
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
