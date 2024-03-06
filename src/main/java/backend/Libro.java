package backend;

import backend.interfaces.Identificable;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Libro implements Serializable, Identificable {

    private String codigo;
    private String autor;
    private String titulo;
    private int copias;
    private Date fechaPublicacion;
    private String editorial;


    public Libro(String codigo, String autor, String titulo, int copias) {
        this.codigo = codigo;
        this.autor = autor;
        this.titulo = titulo;
        this.copias = copias;
    }

    public Libro(String codigo, String autor, String titulo, int copias, String fechaPublicacion, String editorial) throws ParseException {
        this.codigo = codigo;
        this.autor = autor;
        this.titulo = titulo;
        this.copias = copias;
        if (!fechaPublicacion.isEmpty())
            this.fechaPublicacion = new SimpleDateFormat("yyyy-MM-dd").parse(fechaPublicacion);
        if (!editorial.isBlank())
            this.editorial = editorial;

    }

    @Override
    public String getIdentificador() {
        return codigo;
    }
}
