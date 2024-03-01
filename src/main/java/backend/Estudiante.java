package backend;

import backend.enums.Carrera;

import java.io.Serializable;
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
}
