package com.example.aleja.practica2.bdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.aleja.practica2.bdd.BDDContract;


public class SQLiteHelper extends SQLiteOpenHelper {

    private static final String SQL_CREATE_ALUMNO = "CREATE TABLE " + BDDContract.Alumno.TABLA + " (" +
            BDDContract.Alumno._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            BDDContract.Alumno.NOMBRE + " TEXT, " +
            BDDContract.Alumno.TELEFONO + " TEXT, " +
            BDDContract.Alumno.EMAIL + " TEXT," +
            BDDContract.Alumno.EMPRESA + " TEXT," +
            BDDContract.Alumno.TUTOR + " TEXT," +
            BDDContract.Alumno.HORARIO + " TEXT," +
            BDDContract.Alumno.DIRECCION + " TEXT," +
            BDDContract.Alumno.FOTO + " TEXT" +
            " );";
    private static final String SQL_CREATE_VISITA = "CREATE TABLE " + BDDContract.Visita.TABLA + " (" +
            BDDContract.Visita._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            BDDContract.Visita.DIA + " TEXT, " +
            BDDContract.Visita.HORA_INICIO + " TEXT, " +
            BDDContract.Visita.HORA_FIN + " TEXT," +
            BDDContract.Visita.RESUMEN + " TEXT" +
            " );";

    public SQLiteHelper(Context context, String BDName, SQLiteDatabase.CursorFactory factory, int DBVersion) {
        super(context, BDName, factory, DBVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ALUMNO);
        db.execSQL(SQL_CREATE_VISITA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Por simplicidad, se eliminan las tablas existentes y se vuelven a crear,
        db.execSQL("DROP TABLE IF EXISTS " + BDDContract.Alumno.TABLA);
        db.execSQL(SQL_CREATE_ALUMNO);
        db.execSQL("DROP TABLE IF EXISTS " + BDDContract.Visita.TABLA);
        db.execSQL(SQL_CREATE_VISITA);
    }
}
