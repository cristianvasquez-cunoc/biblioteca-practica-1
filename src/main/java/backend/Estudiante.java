package backend;

import backend.enums.Carrera;
import backend.interfaces.Identificable;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;


public class Estudiante implements Serializable, Identificable {

    private String carnet;
    private String nombre;
    private Carrera carrera;
    private List<Prestamo> prestamos;
    private int noPrestamosActivos;

    public Estudiante(String carnet, String nombre, int codigoCarrera) {
        this.carnet = carnet;
        this.nombre = nombre;
        this.carrera = Carrera.getCarreraPorCodigo(codigoCarrera);
        this.noPrestamosActivos = 0;
        this.prestamos = new LinkedList<>();
    }

    public boolean tienePrestamosDisponibles() {
        return noPrestamosActivos < Biblioteca.DIAS_PRESTAMO_MAXIMO;
    }

    public String getNombre() {
        return nombre;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    @Override
    public String getIdentificador() {
        return carnet;
    }

    public void agregarPrestamo(Prestamo prestamo) {
        prestamos.add(prestamo);
        noPrestamosActivos++;
    }
}
