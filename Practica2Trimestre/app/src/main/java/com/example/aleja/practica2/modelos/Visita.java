package com.example.aleja.practica2.modelos;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;


public class Visita implements Parcelable {
    private int id;
    private int idAlumno;
    private Date dia;
    private Date horaInicio;
    private Date horaFin;
    private String resumen;

    public Visita(){

    }
    public Visita(int idAlumno, Date dia, Date horaInicio, Date horaFin, String resumen) {
        this.idAlumno = idAlumno;
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.resumen = resumen;
    }


    //GETTERS AND SETTERS

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAlumno() {
        return idAlumno;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public Date getDia() {
        return dia;
    }

    public void setDia(Date dia) {
        this.dia = dia;
    }

    public Date getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Date horaFin) {
        this.horaFin = horaFin;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    //PARCELABLE
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.idAlumno);
        dest.writeLong(dia != null ? dia.getTime() : -1);
        dest.writeLong(horaInicio != null ? horaInicio.getTime() : -1);
        dest.writeLong(horaFin != null ? horaFin.getTime() : -1);
        dest.writeString(this.resumen);
    }

    protected Visita(Parcel in) {
        this.id = in.readInt();
        this.idAlumno = in.readInt();
        long tmpDia = in.readLong();
        this.dia = tmpDia == -1 ? null : new Date(tmpDia);
        long tmpHoraInicio = in.readLong();
        this.horaInicio = tmpHoraInicio == -1 ? null : new Date(tmpHoraInicio);
        long tmpHoraFin = in.readLong();
        this.horaFin = tmpHoraFin == -1 ? null : new Date(tmpHoraFin);
        this.resumen = in.readString();
    }

    public static final Parcelable.Creator<Visita> CREATOR = new Parcelable.Creator<Visita>() {
        public Visita createFromParcel(Parcel source) {
            return new Visita(source);
        }

        public Visita[] newArray(int size) {
            return new Visita[size];
        }
    };
}
