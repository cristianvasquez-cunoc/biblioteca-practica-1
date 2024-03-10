package backend;

import backend.enums.EstadoPrestamo;
import backend.interfaces.Identificable;
import backend.lectortxt.controllers.ControladorArchivoBinario;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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

    public String getCarnet() {
        return carnet;
    }

    public Date getFecha() {
        return fecha;
    }

    public void finalizar(int total, Estudiante estudiante, Libro libro) {
        fechaFin = new Date();
        estado = EstadoPrestamo.FINALIZADO;
        dineroTotal = total;
        libro.devolverLibro();
        estudiante.finalizarPrestamo(this);

        //actualizar archivos
        ControladorArchivoBinario cab = new ControladorArchivoBinario();
        cab.escribirObjeto("./libros/" + libro.getIdentificador() + ".bin", libro);
        cab.escribirObjeto("./estudiantes/" + estudiante.getIdentificador() + ".bin", estudiante);
    }

    public String getCodigoLibro() {
        return codigoLibro;
    }

    @Override
    public String getIdentificador() {
        return codigoLibro + carnet + fecha.getTime();
    }
}
