package backend;

import java.io.Serializable;
import java.util.Date;

public class Libro implements Serializable, Identificable{

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

    @Override
    public String getIdentificador() {
        return codigo;
    }
}
