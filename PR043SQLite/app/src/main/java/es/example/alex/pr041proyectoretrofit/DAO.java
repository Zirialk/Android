package es.example.alex.pr041proyectoretrofit;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DAO {

    //Patrón Singletone
    private static DAO mInstance;
    private static InstitutoSQLiteHelper mHelper;

    private DAO(Context context) {
        mHelper = new InstitutoSQLiteHelper(context, Contract.BD_NOMBRE, null, Contract.BD_VERSION);
    }

    public static synchronized DAO getDAO(Context context){
        if(mInstance == null)
            mInstance = new DAO(context);

        return mInstance;
    }


    // Abre la base de datos para escritura.
    public SQLiteDatabase openWritableDatabase() {
        return mHelper.getWritableDatabase();
    }

    // Cierra la base de datos.
    public void closeDatabase() {
        mHelper.close();
    }

    public long createAlumno(Alumno alumno) {
        // Se abre la base de datos.
        SQLiteDatabase bd = mHelper.getWritableDatabase();
        // Se crea la lista de pares campo-valor para realizar la inserción.
        ContentValues valores = new ContentValues();
        valores.put(Contract.Alumno.FOTO, alumno.getFoto());
        valores.put(Contract.Alumno.NOMBRE, alumno.getNombre());
        valores.put(Contract.Alumno.CURSO, alumno.getCurso());
        valores.put(Contract.Alumno.TELEFONO, alumno.getTelefono());
        valores.put(Contract.Alumno.DIRECCION, alumno.getDireccion());
        valores.put(Contract.Alumno.EDAD, alumno.getEdad());
        if(alumno.isRepetidor())
            valores.put(Contract.Alumno.REPETIDOR, 1);
        else
            valores.put(Contract.Alumno.REPETIDOR, 0);
        // Se realiza el insert
        long resultado = bd.insert(Contract.Alumno.TABLA, null, valores);
        // Se cierra la base de datos.
        mHelper.close();
        // Se retorna el _id del alumno insertado o -1 si error.
        return resultado;
    }
    public boolean deleteAlumno(long id) {
        // Se abre la base de datos.
        SQLiteDatabase bd = mHelper.getWritableDatabase();
        // Se realiza el delete.
        long resultado = bd.delete(Contract.Alumno.TABLA, Contract.Alumno._ID + " = " + id, null);
        // Se cierra la base de datos.
        mHelper.close();
        // Se retorna si se ha eliminado algún alumno.
        return resultado > 0;

    }
    public Cursor queryAllAlumnos(SQLiteDatabase bd) {
        // Se realiza la consulta y se retorna el cursor.
        return  bd.query(Contract.Alumno.TABLA, Contract.Alumno.TODOS, null,
                null, null, null, Contract.Alumno.NOMBRE);
    }
    public List<Alumno> getAllAlumnos() {
        // Se abre la base de datos.
        SQLiteDatabase bd = mHelper.getWritableDatabase();
        List<Alumno> lista = new ArrayList<>();
        // Se consultan todos los alumnos en la BD y obtiene un cursor.
        Cursor cursor = this.queryAllAlumnos(bd);
        // Se convierte cada registro del cursor en un elemento de la lista.
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Alumno alumno = cursorToAlumno(cursor);
            lista.add(alumno);
            cursor.moveToNext();
        }
        // Se cierra el cursor (IMPORTANTE).
        cursor.close();
        // Se cierra la base de datos.
        mHelper.close();
        // Se retorna la lista.
        return lista;
    }

    // Crea un objeto Alumno a partir del registro actual de un cursor. Recibe
    // el cursor y retorna un nuevo objeto Alumno cargado con los datos del
    // registro actual del cursor.
    public Alumno cursorToAlumno(Cursor cursorAlumno) {
        // Crea un objeto Alumno y guarda los valores provenientes
        // del registro actual del cursor.
        Alumno alumno = new Alumno();
        alumno.setId(cursorAlumno.getInt(
                cursorAlumno.getColumnIndexOrThrow(Contract.Alumno._ID)));
        alumno.setFoto(cursorAlumno.getString(
                cursorAlumno.getColumnIndexOrThrow(Contract.Alumno.FOTO)));
        alumno.setNombre(cursorAlumno.getString(
                cursorAlumno.getColumnIndexOrThrow(Contract.Alumno.NOMBRE)));
        alumno.setCurso(cursorAlumno.getString(
                cursorAlumno.getColumnIndexOrThrow(Contract.Alumno.CURSO)));
        alumno.setTelefono(cursorAlumno.getString(
                cursorAlumno.getColumnIndexOrThrow(Contract.Alumno.TELEFONO)));
        alumno.setDireccion(cursorAlumno.getString(
                cursorAlumno.getColumnIndexOrThrow(Contract.Alumno.DIRECCION)));
        alumno.setEdad(cursorAlumno.getInt(cursorAlumno.getColumnIndexOrThrow(Contract.Alumno.EDAD)));
        //Si la columno repetidor de la tabla devuelve 1, será True.
        if(cursorAlumno.getInt(cursorAlumno.getColumnIndexOrThrow(Contract.Alumno.REPETIDOR)) == 1)
            alumno.setRepetidor(true);
        else
            alumno.setRepetidor(false);

        // Se retorna el objeto Alumno.
        return alumno;
    }
    public boolean updateAlumno(Alumno alumno) {
        // Se abre la base de datos.
        SQLiteDatabase bd = mHelper.getWritableDatabase();
        // Se crea la lista de pares clave-valor con cada campo-valor.
        ContentValues valores = new ContentValues();
        valores.put(Contract.Alumno.FOTO, alumno.getFoto());
        valores.put(Contract.Alumno.NOMBRE, alumno.getNombre());
        valores.put(Contract.Alumno.CURSO, alumno.getCurso());
        valores.put(Contract.Alumno.TELEFONO, alumno.getTelefono());
        valores.put(Contract.Alumno.DIRECCION, alumno.getDireccion());
        valores.put(Contract.Alumno.EDAD, alumno.getEdad());
        if(alumno.isRepetidor())
            valores.put(Contract.Alumno.REPETIDOR, 1);
        else
            valores.put(Contract.Alumno.REPETIDOR, 0);

        // Se realiza el update.
        long resultado = bd.update(Contract.Alumno.TABLA, valores, Contract.Alumno._ID
                + " = " + alumno.getId(), null);
        // Se cierra la base de datos.
        mHelper.close();
        // Se retorna si ha actualizado algún alumno.
        return resultado > 0;

    }

}
