package com.example.usuario.pr033recyclerview;

import java.util.ArrayList;


public class DB {
    private static ArrayList<ListItem> listItems = new ArrayList<>();

    public static ArrayList<ListItem> getListItems() {
        return listItems;
    }

    static{
        listItems.add(new Grupo("Android"));
        listItems.add(new Alumno("Juan"));
        listItems.add(new Alumno("Pepe"));
        listItems.add(new Alumno("Pepe2"));
        listItems.add(new Alumno("Pepe3"));
        listItems.add(new Alumno("Pepe4"));
        listItems.add(new Alumno("Pepe5"));
        listItems.add(new Grupo("DI"));
        listItems.add(new Alumno("Pepe6"));
        listItems.add(new Alumno("Pepe7"));
        listItems.add(new Alumno("Pepe8"));
        listItems.add(new Alumno("Pepe9"));
        listItems.add(new Alumno("Pepe10"));
        listItems.add(new Alumno("Fin"));

    }
}
