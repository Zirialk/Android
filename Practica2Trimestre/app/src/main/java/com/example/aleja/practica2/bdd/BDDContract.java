package com.example.aleja.practica2.bdd;


import android.provider.BaseColumns;

public class BDDContract {
    //Constantes generales de la BDD
    public static final String BDD_NAME = "FCT";
    public static final int BDD_VERSION = 1;

    //Constructor privado para que no pueda instanciarse.
    private BDDContract(){

    }
    //Tabla Alumno
    public static abstract class Alumno implements BaseColumns {
        public static final String TABLA = "alumno";
        public static final String NOMBRE = "nombre";
        public static final String TELEFONO = "telefono";
        public static final String EMAIL = "email";
        public static final String EMPRESA = "empresa";
        public static final String TUTOR = "tutor";
        public static final String HORARIO = "horario";
        public static final String DIRECCION = "direccion";
        public static final String FOTO = "foto";
        public static final String[] TODOS = new String[] { _ID, NOMBRE, TELEFONO,
                EMAIL, EMPRESA, TUTOR, HORARIO, DIRECCION, FOTO };
    }
    //Tabla Visitas
    public static abstract class Visita implements BaseColumns {
        public static final String TABLA = "visita";
        public static final String ID_ALUMNO = "idAlumn";
        public static final String DIA = "dia";
        public static final String HORA_INICIO = "horaInicio";
        public static final String HORA_FIN = "horaFin";
        public static final String RESUMEN = "resumen";
        public static final String[] TODOS = new String[] { _ID, ID_ALUMNO, DIA,
                HORA_INICIO, HORA_FIN, RESUMEN };
    }
}
