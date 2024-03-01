package backend.lectortxt;

import backend.*;
import backend.lectortxt.controllers.ControladorArchivoBinario;

import java.io.*;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

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

        List<RegistroFallido> registroFallidos = new LinkedList<>();

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
            } catch (RegistroFallidoException e) {
                RegistroFallido rf = new RegistroFallido(e.getTextoError(), e.getMessage());
                registroFallidos.add(rf);
            }
        }


        guardarObjetos(estudiantes);
        guardarObjetos(libros);
        guardarObjetos(prestamos);
//        leerObjetos(estudiantes, 1);

    }

    // TODO: Agregar registros fallidos para los carnets y codigos de libro repetidos

    public Estudiante crearEstudiante(BufferedReader br) throws IOException, RegistroFallidoException {

        String textoCarnet = br.readLine();
        String carnet = textoCarnet.split(":")[1];

        String nombre = br.readLine().split(":")[1];

        String textoCarrera = br.readLine();
        int carrera = Integer.parseInt(textoCarrera.split(":")[1]);

        try {
            return new Estudiante(carnet, nombre, carrera);
        } catch (NumberFormatException e) {
            throw new RegistroFallidoException("El carnet solo puede incluir numeros", textoCarnet);
        } catch (IllegalArgumentException e) {
            throw new RegistroFallidoException("No existe carrera con el código: " + carrera, textoCarrera);
        }
    }

    public Libro crearLibro(BufferedReader br) throws IOException, RegistroFallidoException {
        String titulo = br.readLine().split(":")[1];
        String autor = br.readLine().split(":")[1];

        String textoCodigo = br.readLine();
        String codigo = textoCodigo.split(":")[1];

        String textoCantidad = br.readLine();
        int cantidad = Integer.parseInt(textoCantidad.split(":")[1]);

        if (!Pattern.matches("\\d{3}-[A-Z]{3}", codigo))
            throw new RegistroFallidoException("El codigo del libro es invalido", textoCodigo);
        // valida que el codigo sea valido ABC-123

        if (cantidad < 0)
            throw new RegistroFallidoException("La cantidad de libros no puede ser negativa", textoCantidad);

        return new Libro(codigo, autor, titulo, cantidad);
    }

    ;

    public Prestamo crearPrestamo(BufferedReader br) throws IOException, RegistroFallidoException {

        String textoCodigoLibro = br.readLine();
        String textoCarnet = br.readLine();
        String textoFecha = br.readLine();

        try {

            String codigoLibro = textoCodigoLibro.split(":")[1];
            String carnet = textoCarnet.split(":")[1];
            String fecha = textoFecha.split(":")[1];

            if (!Pattern.matches("\\d{3}-[A-Z]{3}", codigoLibro))
                throw new RegistroFallidoException("El codigo del libro es invalido", textoCodigoLibro);

            if (!Pattern.matches("\\d{4}-\\d{2}-\\d{2}", fecha))
                throw new RegistroFallidoException("La fecha ingresada no esta en formato yyyy-mm-dd", textoFecha);

            return new Prestamo(codigoLibro, carnet, fecha);
        } catch (ParseException e) {
            throw new RegistroFallidoException("La fecha ingresada es invalida", textoFecha);
        } catch (NumberFormatException e) {
            throw new RegistroFallidoException("El carnet solo puede incluir numeros", textoCarnet);
        }

        // TODO: Agregar registros fallidos para prestamos cuyo estudiante o libro no exista y para fechas que aun no han pasado

    }

    private void guardarObjetos(List<? extends Identificable> lista) {
        ControladorArchivoBinario cab = new ControladorArchivoBinario();
        for (Identificable objeto : lista) {

            String nombreCarpeta = "";
            String nombreArchivo = (objeto.getIdentificador() + ".bin");


            if (objeto instanceof Estudiante) {
                nombreCarpeta = "estudiantes";
            } else if (objeto instanceof Libro) {
                nombreCarpeta = "libros";
            } else if (objeto instanceof Prestamo) {
                nombreCarpeta = "prestamos";
            }


            File carpeta = new File(nombreCarpeta);
            if (!carpeta.exists()) {
                boolean creada = carpeta.mkdirs();
                if (creada) {
                    System.out.println("Carpeta creada exitosamente!");
                } else {
                    System.out.println("Error al crear carpeta.");
                    break;
                }
            }

            String rutaArchivo = nombreCarpeta + File.separator + nombreArchivo;
            File archivo = new File(rutaArchivo);

            if (!archivo.exists()) {

                try {
                    boolean creado = archivo.createNewFile();
                    if (creado) {
                        cab.escribirObjeto(rutaArchivo, objeto);
                    }
                } catch (IOException e) {
                    System.out.println("Error al crear archivo." + rutaArchivo);
                    e.printStackTrace();
                }
            } else {
                System.out.println("El archivo " + rutaArchivo + " ya existe");
            }
        }
    }

    private void leerObjetos (List<? extends Identificable> lista, int tipoObjeto) {
        ControladorArchivoBinario cab = new ControladorArchivoBinario();
        String nombreCarpeta = (tipoObjeto == 1) ? "./estudiantes/" : (tipoObjeto == 2) ? "./libros/" : (tipoObjeto == 3) ? "./prestamos/" : "./";

        for (Identificable objeto : lista) {
            cab.leerObjeto(nombreCarpeta + File.separator + objeto.getIdentificador() + ".bin");
        }
    }

}
