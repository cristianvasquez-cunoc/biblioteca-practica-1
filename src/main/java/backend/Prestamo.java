package backend;

import backend.enums.EstadoPrestamo;
import backend.interfaces.Identificable;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Prestamo implements Serializable, Identificable {

    private String codigoLibro;
    private String carnet;

    private Date fechaFin;
    private Date fecha;
    private EstadoPrestamo estado;
    private double dineroTotal;


    public Prestamo(String codigoLibro, String carnet, String fecha) throws ParseException {
        // fecha en formato yyyy-mm-dd
        this.codigoLibro = codigoLibro;
        this.carnet = carnet;
        this.fecha = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);
        this.estado = EstadoPrestamo.PENDIENTE;
    }

    @Override
    public String getIdentificador() {
        return codigoLibro + carnet + fecha.getTime();
    }
}
