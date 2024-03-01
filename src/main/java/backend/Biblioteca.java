package backend;

import backend.interfaces.Identificable;

import java.util.List;

public class Biblioteca {

    private List<Estudiante> estudiantes;
    private List<Libro> libros;
    private List<Prestamo> prestamos;
    private double dinero;

    public static final int DIAS_PRESTAMO_MAXIMO = 3;



    private void registrarEstudiante() {}
    private void registrarLibro() {}
    private void registrarPrestamo() {}
    private void calcularDineroPrestado() {}


    public boolean existeObjeto(List<? extends Identificable> lista, String identificador) {
        for(Identificable objeto : lista) {
            if(objeto.getIdentificador().equals(identificador)) return true;
        }
        return false;
    }

    public Estudiante getEstudianteByCarnet(String carnet) {
        for(Estudiante estudiante : estudiantes) {
            if(estudiante.getIdentificador().equals(carnet)) return estudiante;
        }
        return null;
    }
    public void setEstudiantes(List<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    public void setPrestamos(List<Prestamo> prestamos) {
        this.prestamos = prestamos;
    }

    public List<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public List<Prestamo> getPrestamos() {
        return prestamos;
    }

    public double getDinero() {
        return dinero;
    }


}
