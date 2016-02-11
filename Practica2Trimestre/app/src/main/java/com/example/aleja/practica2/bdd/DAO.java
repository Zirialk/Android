package com.example.aleja.practica2.bdd;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.aleja.practica2.modelos.Alumno;

import java.util.ArrayList;
import java.util.List;

public class DAO {
    //Patrón Singletone.
    private static DAO mInstance;
    private static SQLiteHelper mHelper;

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

}
