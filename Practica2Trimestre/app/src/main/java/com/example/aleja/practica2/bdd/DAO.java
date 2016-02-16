package com.example.aleja.practica2.bdd;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.aleja.practica2.modelos.Alumno;
import com.example.aleja.practica2.modelos.Visita;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DAO {
    //Patrón Singletone.
    private static DAO mInstance;
    private static SQLiteHelper mHelper;

    public enum SelectVisitas {
        TODAS,ID_ALUMNO
    }
    private DAO(Context context){
        mHelper = new SQLiteHelper(context, BDDContract.BDD_NAME, null, BDDContract.BDD_VERSION);
    }

    public static synchronized DAO getInstance(Context context){
        if( mInstance == null)
            mInstance = new DAO(context);

        return mInstance;
    }
    //Abre la base de datos con opción a escritura.
    public SQLiteDatabase openWritableDB(){
        return mHelper.getWritableDatabase();
    }
    //Cierra la base de datos.
    public void closeDB(){
        mHelper.close();
    }

    public long createAlumno(Alumno alumno){
        long idAlumnoInsertado;

        //Se abre la base de datos
        SQLiteDatabase db = mHelper.getWritableDatabase();
        //Se crea la lista de pares clave-valor para realizar la inserción.
        ContentValues valores = new ContentValues();
        valores.put(BDDContract.Alumno.NOMBRE, alumno.getNombre());
        valores.put(BDDContract.Alumno.TELEFONO, alumno.getTelefono());
        valores.put(BDDContract.Alumno.EMAIL, alumno.getEmail());
        valores.put(BDDContract.Alumno.EMPRESA, alumno.getEmpresa());
        valores.put(BDDContract.Alumno.TUTOR, alumno.getTutor());
        valores.put(BDDContract.Alumno.HORARIO, alumno.getHorario());
        valores.put(BDDContract.Alumno.DIRECCION, alumno.getDireccion());
        valores.put(BDDContract.Alumno.FOTO, alumno.getFoto());
        //Se realiza la Insert y se cierra la base de datos.
        idAlumnoInsertado = db.insert(BDDContract.Alumno.TABLA, null, valores);
        db.close();
        return idAlumnoInsertado;
    }

    public boolean deleteAlumno(long id){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        long resultado = db.delete(BDDContract.Alumno.TABLA, BDDContract.Alumno._ID + " = " + id, null);
        db.close();
        return resultado > 0;
    }

    public Cursor queryAllAlumnos(SQLiteDatabase bd){
        //Devuelve todos los alumnos ordenados por el nombre.
        return bd.query(BDDContract.Alumno.TABLA, BDDContract.Alumno.TODOS, null, null, null, null, BDDContract.Alumno.NOMBRE);
    }
    public List<Alumno> getAllAlumnos(){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        List<Alumno> lista = new ArrayList<>();
        //Se obtiene el cursor con todos los alumnos.
        Cursor cursor = queryAllAlumnos(db);
        cursor.moveToFirst();
        //Se traspasa todos los elementos del cursor hacia una lista.
        while (!cursor.isAfterLast()){
            lista.add(cursorToAlumno(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return lista;

    }
    //Crea un objeto alumno a partir del registro ACTUAL del cursor pasado por parámetro.
    public Alumno cursorToAlumno(Cursor cursorAlumno){
        Alumno alumno = new Alumno();
        alumno.setId(cursorAlumno.getInt(cursorAlumno.getColumnIndexOrThrow(BDDContract.Alumno._ID)));
        alumno.setNombre(cursorAlumno.getString(cursorAlumno.getColumnIndexOrThrow(BDDContract.Alumno.NOMBRE)));
        alumno.setTelefono(cursorAlumno.getString(cursorAlumno.getColumnIndexOrThrow(BDDContract.Alumno.TELEFONO)));
        alumno.setEmail(cursorAlumno.getString(cursorAlumno.getColumnIndexOrThrow(BDDContract.Alumno.EMAIL)));
        alumno.setEmpresa(cursorAlumno.getString(cursorAlumno.getColumnIndexOrThrow(BDDContract.Alumno.EMPRESA)));
        alumno.setTutor(cursorAlumno.getString(cursorAlumno.getColumnIndexOrThrow(BDDContract.Alumno.TUTOR)));
        alumno.setHorario(cursorAlumno.getString(cursorAlumno.getColumnIndexOrThrow(BDDContract.Alumno.HORARIO)));
        alumno.setDireccion(cursorAlumno.getString(cursorAlumno.getColumnIndexOrThrow(BDDContract.Alumno.DIRECCION)));
        alumno.setFoto(cursorAlumno.getString(cursorAlumno.getColumnIndexOrThrow(BDDContract.Alumno.FOTO)));
        //Se retorna el alumno ya configurado.
        return alumno;
    }


    //    VISITAS


    public long createVisita(Visita visita){
        long idVisitaInsertado;

        //Se abre la base de datos
        SQLiteDatabase db = mHelper.getWritableDatabase();
        //Se crea la lista de pares clave-valor para realizar la inserción.
        ContentValues valores = new ContentValues();
        valores.put(BDDContract.Visita.ID_ALUMNO, visita.getIdAlumno());
        valores.put(BDDContract.Visita.DIA, visita.getDia().getTime());
        valores.put(BDDContract.Visita.HORA_INICIO, visita.getHoraInicio().getTime());
        valores.put(BDDContract.Visita.HORA_FIN, visita.getHoraFin().getTime());
        valores.put(BDDContract.Visita.RESUMEN, visita.getResumen());

        //Se realiza la Insert y se cierra la base de datos.
        idVisitaInsertado = db.insert(BDDContract.Visita.TABLA, null, valores);
        db.close();
        return idVisitaInsertado;
    }
    public Cursor queryAlumnoVisitas(SQLiteDatabase bd, int idAlumno){
        return bd.query(BDDContract.Visita.TABLA, BDDContract.Visita.TODOS, BDDContract.Visita.ID_ALUMNO+ "=" + idAlumno, null, null, null, BDDContract.Visita.DIA);
    }
    public Cursor queryAllVisitas(SQLiteDatabase bd){
        //Devuelve todos los alumnos ordenados por el nombre.
        return bd.query(BDDContract.Visita.TABLA, BDDContract.Visita.TODOS, null, null, null, null, BDDContract.Visita.DIA);
    }
    public List<Visita> getAllVisitas(){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        List<Visita> lista = new ArrayList<>();
        //Se obtiene el cursor con todos los alumnos.
        Cursor cursor = queryAllVisitas(db);
        cursor.moveToFirst();
        //Se traspasa todos los elementos del cursor hacia una lista.
        while (!cursor.isAfterLast()){
            lista.add(cursorToVisita(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return lista;
    }
    //Devuelve una lista con las visitas, del alumno propiertario de idAlumno.
    public List<Visita> getAlumnoVisitas(int idAlumno){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        List<Visita> lista = new ArrayList<>();
        //Se obtiene el cursor con todos los alumnos.
        Cursor cursor = queryAlumnoVisitas(db, idAlumno);
        cursor.moveToFirst();
        //Se traspasa todos los elementos del cursor hacia una lista.
        while (!cursor.isAfterLast()){
            lista.add(cursorToVisita(cursor));
            cursor.moveToNext();
        }
        cursor.close();
        db.close();
        return lista;

    }
    //Crea un objeto alumno a partir del registro ACTUAL del cursor pasado por parámetro.
    public Visita cursorToVisita(Cursor cursorVisita){
        Visita visita = new Visita();
        visita.setId(cursorVisita.getInt(cursorVisita.getColumnIndexOrThrow(BDDContract.Visita._ID)));
        visita.setIdAlumno(cursorVisita.getInt(cursorVisita.getColumnIndexOrThrow(BDDContract.Visita.ID_ALUMNO)));
        visita.setDia(new Date(cursorVisita.getLong(cursorVisita.getColumnIndexOrThrow(BDDContract.Visita.DIA))));
        visita.setHoraInicio(new Date(cursorVisita.getLong(cursorVisita.getColumnIndexOrThrow(BDDContract.Visita.HORA_INICIO))));
        visita.setHoraFin(new Date(cursorVisita.getLong(cursorVisita.getColumnIndexOrThrow(BDDContract.Visita.HORA_FIN))));
        visita.setResumen(cursorVisita.getString(cursorVisita.getColumnIndexOrThrow(BDDContract.Visita.RESUMEN)));

        //Se retorna el alumno ya configurado.
        return visita;
    }
}