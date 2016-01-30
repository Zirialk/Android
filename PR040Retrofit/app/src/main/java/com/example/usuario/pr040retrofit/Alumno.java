package com.example.usuario.pr040retrofit;

public class Alumno {

    private int id;
    private Boolean repetidor;
    private Integer edad;
    private String direccion;
    private String telefono;
    private String curso;
    private String nombre;
    private String foto;

    public Alumno( Boolean repetidor, Integer edad, String direccion, String telefono, String curso, String nombre, String foto) {
        this.repetidor = repetidor;
        this.edad = edad;
        this.direccion = direccion;
        this.telefono = telefono;
        this.curso = curso;
        this.nombre = nombre;
        this.foto = foto;
    }

    public Boolean getRepetidor() {
        return repetidor;
    }

    public void setRepetidor(Boolean repetidor) {
        this.repetidor = repetidor;
    }


    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getDireccion() {
        return direccion;
    }


    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }


    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getFoto() {
        return foto;
    }


    public void setFoto(String foto) {
        this.foto = foto;
    }
    @Override
    public String toString() {
        return "Alumno{" +
                "repetidor=" + repetidor +
                ", edad=" + edad +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", curso='" + curso + '\'' +
                ", nombre='" + nombre + '\'' +
                ", foto='" + foto + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}