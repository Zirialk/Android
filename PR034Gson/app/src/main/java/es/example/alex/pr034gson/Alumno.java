package es.example.alex.pr034gson;

import android.os.Parcel;


public class Alumno extends ListItem{

    private String nombre;
    private String curso;
    private String telefono;
    private int edad;
    private String direccion;
    private boolean repetidor;
    private String foto;

    public Alumno(String nombre, String curso, String telefono, int edad, String direccion, boolean repetidor, String foto) {
        this.nombre = nombre;
        this.curso = curso;
        this.telefono = telefono;
        this.edad = edad;
        this.direccion = direccion;
        this.repetidor = repetidor;
        this.foto = foto;
    }



    //GETTERS && SETTERS

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCurso() {
        return curso;
    }

    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public boolean isRepetidor() {
        return repetidor;
    }

    public void setRepetidor(boolean repetidor) {
        this.repetidor = repetidor;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.nombre);
        dest.writeString(this.curso);
        dest.writeString(this.telefono);
        dest.writeInt(this.edad);
        dest.writeString(this.direccion);
        dest.writeByte(repetidor ? (byte) 1 : (byte) 0);
        dest.writeString(this.foto);
    }

    protected Alumno(Parcel in) {
        this.nombre = in.readString();
        this.curso = in.readString();
        this.telefono = in.readString();
        this.edad = in.readInt();
        this.direccion = in.readString();
        this.repetidor = in.readByte() != 0;
        this.foto = in.readString();
    }

    public static final Creator<Alumno> CREATOR = new Creator<Alumno>() {
        public Alumno createFromParcel(Parcel source) {
            return new Alumno(source);
        }

        public Alumno[] newArray(int size) {
            return new Alumno[size];
        }
    };

    @Override
    public int getType() {
        return ListItem.TYPE_CHILD;
    }
}
