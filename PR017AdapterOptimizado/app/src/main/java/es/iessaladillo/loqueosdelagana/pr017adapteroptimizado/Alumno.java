package es.iessaladillo.loqueosdelagana.pr017adapteroptimizado;


public class Alumno {
    private String nombre, telefono;
    private int edad;


    public Alumno(String nombre, int edad, String telefono){
        this.nombre=nombre;
        this.edad=edad;
    }

    public int getEdad() {
        return edad;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }
}
