package backend.lectortxt;

import backend.Biblioteca;
import backend.Estudiante;
import backend.Libro;
import backend.Prestamo;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class LectorTxt {

    private List<RegistroFallido> registroFallidos;
    private Biblioteca biblioteca;


    public void leer(String rutaArchivo) throws IOException {

        File archivo = new File(rutaArchivo);
        FileReader fr = new FileReader(archivo);
        BufferedReader br = new BufferedReader(fr);

        String linea;

        List<Libro> libros = new LinkedList<>();
        List<Estudiante> estudiantes = new LinkedList<>();
        List<Prestamo> prestamos = new LinkedList<>();

        while ((linea = br.readLine()) != null) {

            try {
                if (linea.equals("LIBRO")) {
                    libros.add(crearLibro(br));
                } else if (linea.equals("ESTUDIANTE")) {
                    estudiantes.add(crearEstudiante(br));
                } else if (linea.equals("PRESTAMO")) {
                    prestamos.add(crearPrestamo(br));
                }

            } catch (IOException e) {
                // TODO: archivo no se pudo leer por alguna razon, vuelva a intentar
            } catch (Exception e) {
                // TODO: registros fallidos
            }
        }

        System.out.printf("hola");


    }

    ;

    public Estudiante crearEstudiante(BufferedReader br) throws IOException {
        String carnet = br.readLine().split(":")[1];
        String nombre = br.readLine().split(":")[1];
        int carrera = Integer.parseInt(br.readLine().split(":")[1]);

        return new Estudiante(carnet,nombre, carrera);
    }

    ;

    public Libro crearLibro(BufferedReader br) throws IOException {
        String titulo = br.readLine().split(":")[1];
        String autor = br.readLine().split(":")[1];;
        String codigo = br.readLine().split(":")[1];;
        int cantidad = Integer.parseInt(br.readLine().split(":")[1]);

        return new Libro(codigo, autor, titulo, cantidad);
    }

    ;

    public Prestamo crearPrestamo(BufferedReader br) throws IOException, ParseException {
        String codigoLibro = br.readLine().split(":")[1];
        String carnet = br.readLine().split(":")[1];
        String fecha = br.readLine().split(":")[1];

        return new Prestamo(codigoLibro, carnet, fecha);
    }

    ;

}
