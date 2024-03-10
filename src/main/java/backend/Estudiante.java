package backend;

import backend.enums.Carrera;
import backend.interfaces.Identificable;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


public class Estudiante implements Serializable, Identificable {

    private String carnet;
    private String nombre;
    private Carrera carrera;
    private List<Prestamo> prestamos;
    private int noPrestamosActivos;
    private Date fechaNacimiento;

    public Estudiante(String carnet, String nombre, int codigoCarrera) {
        this.carnet = carnet;
        this.nombre = nombre;
        this.carrera = Carrera.getCarreraPorCodigo(codigoCarrera);
        this.noPrestamosActivos = 0;
        this.prestamos = new LinkedList<>();
    }

    public Estudiante(String carnet, String nombre, int codigoCarrera, String fechaNacimiento) throws ParseException {
        this.carnet = carnet;
        this.nombre = nombre;
        this.carrera = Carrera.getCarreraPorCodigo(codigoCarrera);
        this.noPrestamosActivos = 0;
        this.prestamos = new LinkedList<>();
        this.fechaNacimiento = new SimpleDateFormat("yyyy-MM-dd").parse(fechaNacimiento);
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

    public int getNoPrestamosActivos() {
        return noPrestamosActivos;
    }

    public List<Prestamo> getPrestamos() {
        return prestamos;
    }

    @Override
    public String getIdentificador() {
        return carnet;
    }

    public void agregarPrestamo(Prestamo prestamo) {
        prestamos.add(prestamo);
        noPrestamosActivos++;
    }

    public void finalizarPrestamo(Prestamo prestamo) {
        noPrestamosActivos--;
        prestamos.remove(prestamo);
    }
}
