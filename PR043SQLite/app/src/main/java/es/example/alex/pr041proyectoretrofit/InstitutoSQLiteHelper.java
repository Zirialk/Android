package es.example.alex.pr041proyectoretrofit;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class InstitutoSQLiteHelper extends SQLiteOpenHelper {
    private static final String SQL_CREATE_ALUMNOS =
            "CREATE TABLE " + Contract.Alumno.TABLA + " (" +
                    Contract.Alumno._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Contract.Alumno.NOMBRE + " TEXT, " +
                    Contract.Alumno.CURSO + " TEXT," +
                    Contract.Alumno.TELEFONO + " TEXT, " +
                    Contract.Alumno.EDAD + " INTEGER," +
                    Contract.Alumno.DIRECCION + " TEXT, " +
                    Contract.Alumno.REPETIDOR + " INTEGER," +
                    Contract.Alumno.FOTO + " TEXT " +
                    " );";


    public InstitutoSQLiteHelper(Context context, String BDName, SQLiteDatabase.CursorFactory factory, int DBVersion) {
        super(context, BDName, factory, DBVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ALUMNOS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Por simplicidad, se eliminan las tablas existentes y se vuelven a crear,
        db.execSQL("DROP TABLE IF EXISTS " + Contract.Alumno.TABLA);
        db.execSQL(SQL_CREATE_ALUMNOS);
    }
}
