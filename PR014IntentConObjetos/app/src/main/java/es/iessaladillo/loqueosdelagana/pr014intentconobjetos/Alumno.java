package es.iessaladillo.loqueosdelagana.pr014intentconobjetos;


import android.os.Parcel;
import android.os.Parcelable;

public class Alumno implements Parcelable{


    private String dni;
    private String nombre;

    private String sexo;

    private int edad;
    public Alumno(String dni, String nombre, int edad) {
        this.dni=dni;
        this.nombre=nombre;
        this.edad=edad;
    }

    public Alumno(){
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.dni);
        dest.writeString(this.nombre);
        dest.writeInt(this.edad);
        dest.writeString(sexo);
    }

    protected Alumno(Parcel in) {
        this.dni = in.readString();
        this.nombre = in.readString();
        this.edad = in.readInt();
        this.sexo = in.readString();
    }

    public static final Creator<Alumno> CREATOR = new Creator<Alumno>() {
        public Alumno createFromParcel(Parcel source) {
            return new Alumno(source);
        }

        public Alumno[] newArray(int size) {
            return new Alumno[size];
        }
    };

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }
    public int getEdad() {
        return edad;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getSexo() {
        return sexo;
    }

    public String getDni() {

        return dni;
    }

    public void setNombre(String nombre){
        this.nombre=nombre;
    }
    public void setEdad(int edad){
        this.edad=edad;
    }
}
