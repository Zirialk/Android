package com.example.aleja.practica2.bdd;


import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;

import com.example.aleja.practica2.modelos.Alumno;
import com.example.aleja.practica2.modelos.Visita;
import com.example.aleja.practica2.utils.Constantes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DAO {
    //Patrón Singletone.
    private static DAO mInstance;
    private static SQLiteHelper mHelper;
    SharedPreferences preferences;

    private DAO(Context context){
        mHelper = new SQLiteHelper(context, BDDContract.BDD_NAME, null, BDDContract.BDD_VERSION);
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
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


    //      ALUMNO


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
    public int updateAlumno(Alumno alumno){
        int camposActualizados;
        //Se abre la base de datos
        SQLiteDatabase db = mHelper.getWritableDatabase();
        //Se crea la lista de pares clave-valor para realizar la actualización.
        ContentValues valores = new ContentValues();
        valores.put(BDDContract.Alumno.NOMBRE, alumno.getNombre());
        valores.put(BDDContract.Alumno.TELEFONO, alumno.getTelefono());
        valores.put(BDDContract.Alumno.EMAIL, alumno.getEmail());
        valores.put(BDDContract.Alumno.EMPRESA, alumno.getEmpresa());
        valores.put(BDDContract.Alumno.TUTOR, alumno.getTutor());
        valores.put(BDDContract.Alumno.HORARIO, alumno.getHorario());
        valores.put(BDDContract.Alumno.DIRECCION, alumno.getDireccion());
        valores.put(BDDContract.Alumno.FOTO, alumno.getFoto());

        camposActualizados = db.update(BDDContract.Alumno.TABLA, valores, BDDContract.Alumno._ID +"="+ alumno.getId(), null);
        db.close();
        return camposActualizados;
    }

    public boolean deleteAlumno(long id){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        //Sqlite no permite el DELETE ON CASCADE.
        //Forma manual.
        db.delete(BDDContract.Visita.TABLA, BDDContract.Visita.ID_ALUMNO + "=" + id, null);

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
    public Alumno getAlumno(long id){
        SQLiteDatabase bd = mHelper.getWritableDatabase();
        Cursor cursor = bd.query(BDDContract.Alumno.TABLA, BDDContract.Alumno.TODOS, BDDContract.Alumno._ID + "=" + id, null, null, null, null);
        cursor.moveToFirst();
        return cursorToAlumno(cursor);
    }


    //    VISITAS

    private String getCondicionVisita(Visita visita){
        return String.format("%s = %d AND %s >= %d AND %s <= %d"
                //%d  =  %s          En el mismo día
                ,BDDContract.Visita.DIA, visita.getDia().getTime()
                //                   La visita que termine despues de que empieze la nueva visita.
                , BDDContract.Visita.HORA_FIN, visita.getHoraInicio().getTime()
                //                   La visita que empieze antes de que termine la nueva visita.
                , BDDContract.Visita.HORA_INICIO, visita.getHoraFin().getTime());
    }

    public long createVisita(Visita visita){
        long idVisitaInsertado = -1;

        String condicion = getCondicionVisita(visita);

        //Se abre la base de datos
        SQLiteDatabase db = mHelper.getWritableDatabase();
        //Busca si existe alguna visita en el mismo intervalo de tiempo que la nueva.
        //Si existiera, no permitiría la creación de la nueva.
        Cursor cursor = db.query(BDDContract.Visita.TABLA,BDDContract.Visita.TODOS, condicion, null, null, null, null, null);
        if(cursor.getCount() == 0){
            //Se crea la lista de pares clave-valor para realizar la inserción.
            ContentValues valores = new ContentValues();
            valores.put(BDDContract.Visita.ID_ALUMNO, visita.getIdAlumno());
            valores.put(BDDContract.Visita.DIA, visita.getDia().getTime());
            valores.put(BDDContract.Visita.HORA_INICIO, visita.getHoraInicio().getTime());
            valores.put(BDDContract.Visita.HORA_FIN, visita.getHoraFin().getTime());
            valores.put(BDDContract.Visita.RESUMEN, visita.getResumen());

            //Se realiza la Insert y se cierra la base de datos.
            idVisitaInsertado = db.insert(BDDContract.Visita.TABLA, null, valores);
        }

        cursor.close();
        db.close();
        return idVisitaInsertado;
    }
    public int updateVisita(Visita visita){
        int camposActualizados = 0;

        SQLiteDatabase db = mHelper.getWritableDatabase();
        //Se crea la lista de pares clave-valor para realizar la actualización.
        ContentValues valores = new ContentValues();
        valores.put(BDDContract.Visita._ID, visita.getId());
        valores.put(BDDContract.Visita.ID_ALUMNO, visita.getIdAlumno());
        valores.put(BDDContract.Visita.DIA, visita.getDia().getTime());
        valores.put(BDDContract.Visita.HORA_INICIO, visita.getHoraInicio().getTime());
        valores.put(BDDContract.Visita.HORA_FIN, visita.getHoraFin().getTime());
        valores.put(BDDContract.Visita.RESUMEN, visita.getResumen());

        //Se realiza el update
        camposActualizados = db.update(BDDContract.Visita.TABLA, valores, BDDContract.Visita._ID + "=" + visita.getId(), null);

        db.close();
        return camposActualizados;
    }
    public boolean deleteVisita(int idVisita){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        long resultado = db.delete(BDDContract.Visita.TABLA, BDDContract.Visita._ID + " = " + idVisita, null);
        db.close();
        return resultado > 0;
    }


    //Obtiene el orderBy correspondiente dependiendo de las preferencias.
    private String orderByAscDesc(){
        String orderBy;

        //Si la preferencia de ordenar descendentemente, se especificará en el orderBy su orden descendente.
        if (preferences.getString(Constantes.PREF_ORDEN_VISITA, Constantes.ORDEN_ASCENDENTE).equals(Constantes.ORDEN_DESCENDENTE))
            orderBy = BDDContract.Visita.DIA + " DESC, " + BDDContract.Visita.HORA_INICIO + " DESC";
        else
            orderBy = BDDContract.Visita.DIA + ", " + BDDContract.Visita.HORA_INICIO;
        return orderBy;
    }
    public Cursor queryAlumnoVisitas(SQLiteDatabase bd, int idAlumno){


        return bd.query(BDDContract.Visita.TABLA, BDDContract.Visita.TODOS, BDDContract.Visita.ID_ALUMNO + "=" + idAlumno, null, null, null, orderByAscDesc());
    }
    public Cursor queryAllProxVisitas(SQLiteDatabase bd){
        //Devuelve las visitas posteriores al momento de ejecución de esta sentencia.
        return bd.rawQuery(String.format("SELECT a.%s, v.* FROM %s a LEFT OUTER JOIN %s v ON a.%s=%s AND v.%s = (SELECT %s FROM %s WHERE a.%s=%s ORDER BY dia DESC , 1 DESC LIMIT 1) ORDER BY %s",BDDContract.Alumno._ID, BDDContract.Alumno.TABLA, BDDContract.Visita.TABLA, BDDContract.Alumno._ID, BDDContract.Visita.ID_ALUMNO, BDDContract.Visita._ID, BDDContract.Visita._ID, BDDContract.Visita.TABLA, BDDContract.Alumno._ID, BDDContract.Visita.ID_ALUMNO, orderByAscDesc()), null);
    }

    public List<Visita> getAllProxVisitas(){
        SQLiteDatabase db = mHelper.getWritableDatabase();
        List<Visita> lista = new ArrayList<>();
        //Se obtiene el cursor con todos los alumnos.
        Cursor cursor = queryAllProxVisitas(db);
        cursor.moveToFirst();
        //Se traspasa todos los elementos del cursor a una lista.
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
    //Crea un objeto visita a partir del registro ACTUAL del cursor pasado por parámetro.
    public Visita cursorToVisita(Cursor cursorVisita){
        Visita visita = new Visita();
        visita.setId(cursorVisita.getInt(cursorVisita.getColumnIndexOrThrow(BDDContract.Visita._ID)));
        if(visita.getId() == 0)
            visita.setIdAlumno(cursorVisita.getInt(0));
        else
            visita.setIdAlumno(cursorVisita.getInt(cursorVisita.getColumnIndexOrThrow(BDDContract.Visita.ID_ALUMNO)));
        visita.setDia(new Date(cursorVisita.getLong(cursorVisita.getColumnIndexOrThrow(BDDContract.Visita.DIA))));
        visita.setHoraInicio(new Date(cursorVisita.getLong(cursorVisita.getColumnIndexOrThrow(BDDContract.Visita.HORA_INICIO))));
        visita.setHoraFin(new Date(cursorVisita.getLong(cursorVisita.getColumnIndexOrThrow(BDDContract.Visita.HORA_FIN))));
        visita.setResumen(cursorVisita.getString(cursorVisita.getColumnIndexOrThrow(BDDContract.Visita.RESUMEN)));
        //Se retorna la visita ya configurada.
        return visita;
    }
}
