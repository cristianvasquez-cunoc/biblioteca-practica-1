package frontend.prestamos;

import backend.Biblioteca;
import backend.Estudiante;
import backend.Libro;
import backend.Prestamo;
import backend.enums.TipoRegistroFallido;
import backend.lectortxt.LectorTxt;
import backend.lectortxt.RegistroFallidoException;
import backend.lectortxt.controllers.ControladorArchivoBinario;
import frontend.registros.RegistroFallidoMensajeException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.regex.Pattern;

public class RealizarPrestamoPanel extends JPanel {

    private JLabel titulo;
    private JLabel carnetTexto;
    private JLabel carnetFormato;
    private JTextField carnetField;
    private JLabel codigoLibroTexto1;
    private JLabel codigoLibroTexto2;
    private JLabel codigoLibroTexto3;

    private JLabel codigoLibroFormato1;
    private JLabel codigoLibroFormato2;
    private JLabel codigoLibroFormato3;
    private JTextField codigoLibroField1;
    private JTextField codigoLibroField2;
    private JTextField codigoLibroField3;
    private JButton realizarPrestamoBoton;

    private JPanel textFieldsPane;
    private Biblioteca biblioteca;
    private LectorTxt lectorTxt;

    public RealizarPrestamoPanel(Biblioteca biblioteca, LectorTxt lectorTxt) throws HeadlessException {
        this.biblioteca = biblioteca;
        this.lectorTxt = lectorTxt;

        construirJPanel();

        realizarPrestamoBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    realizarPrestamos();
                    JOptionPane.showMessageDialog(getParent(), "Los prestamos se han agregado exitosamente", "Prestamos agregados", JOptionPane.INFORMATION_MESSAGE);
                } catch (RegistroFallidoMensajeException ex) {
                    JOptionPane.showMessageDialog(getParent(), ex.getMessage(), "Error al realizar prestamo", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    private void construirJPanel() {
        titulo = new JLabel("Realizar Prestamo");
        titulo.setFont(getFuenteConTamano(24));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 0));

        setLayout(new BorderLayout());
        add(titulo, BorderLayout.NORTH);

        JPanel borderTextFieldsPane = new JPanel();
        borderTextFieldsPane.setLayout(new BorderLayout());

        textFieldsPane = new JPanel();
        textFieldsPane.setLayout(new BoxLayout(textFieldsPane, BoxLayout.Y_AXIS));
        textFieldsPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        borderTextFieldsPane.add(textFieldsPane, BorderLayout.CENTER);


        codigoLibroFormato1 = new JLabel("Codigo de la forma 123-ABC");
        codigoLibroFormato1.setFont(getFuenteConTamano(12));
        codigoLibroTexto1 = new JLabel("Codigo del libro *");
        codigoLibroTexto1.setFont(getFuenteConTamano(14));
        codigoLibroField1 = new JTextField();
        codigoLibroField1.setBorder(BorderFactory.createCompoundBorder(
                codigoLibroField1.getBorder(),
                BorderFactory.createEmptyBorder(0, 10, 0, 0)));

        codigoLibroFormato2 = new JLabel("Codigo de la forma 123-ABC");
        codigoLibroFormato2.setFont(getFuenteConTamano(12));
        codigoLibroTexto2 = new JLabel("Codigo del libro");
        codigoLibroTexto2.setFont(getFuenteConTamano(14));
        codigoLibroField2 = new JTextField();
        codigoLibroField2.setBorder(BorderFactory.createCompoundBorder(
                codigoLibroField2.getBorder(),
                BorderFactory.createEmptyBorder(0, 10, 0, 0)));

        codigoLibroFormato3 = new JLabel("Codigo de la forma 123-ABC");
        codigoLibroFormato3.setFont(getFuenteConTamano(12));
        codigoLibroTexto3 = new JLabel("Codigo del libro");
        codigoLibroTexto3.setFont(getFuenteConTamano(14));
        codigoLibroField3 = new JTextField();
        codigoLibroField3.setBorder(BorderFactory.createCompoundBorder(
                codigoLibroField3.getBorder(),
                BorderFactory.createEmptyBorder(0, 10, 0, 0)));

        carnetTexto = new JLabel("Carnet *");
        carnetTexto.setFont(getFuenteConTamano(14));
        carnetField = new JTextField();
        carnetField.setBorder(BorderFactory.createCompoundBorder(
                carnetField.getBorder(),
                BorderFactory.createEmptyBorder(0, 10, 0, 0)));
        carnetFormato = new JLabel("El carnet es un numero");
        carnetFormato.setFont(getFuenteConTamano(12));

        realizarPrestamoBoton = new JButton("Realizar Prestamo");

        textFieldsPane.add(codigoLibroTexto1);
        textFieldsPane.add(Box.createRigidArea(new Dimension(0, 5)));
        textFieldsPane.add(codigoLibroField1);
        textFieldsPane.add(Box.createRigidArea(new Dimension(0, 5)));
        textFieldsPane.add(codigoLibroFormato1);
        gap(0, 16);

        textFieldsPane.add(codigoLibroTexto2);
        textFieldsPane.add(Box.createRigidArea(new Dimension(0, 5)));
        textFieldsPane.add(codigoLibroField2);
        textFieldsPane.add(Box.createRigidArea(new Dimension(0, 5)));
        textFieldsPane.add(codigoLibroFormato2);
        gap(0, 16);

        textFieldsPane.add(codigoLibroTexto3);
        textFieldsPane.add(Box.createRigidArea(new Dimension(0, 5)));
        textFieldsPane.add(codigoLibroField3);
        textFieldsPane.add(Box.createRigidArea(new Dimension(0, 5)));
        textFieldsPane.add(codigoLibroFormato3);
        gap(0, 16);

        textFieldsPane.add(carnetTexto);
        textFieldsPane.add(Box.createRigidArea(new Dimension(0, 5)));
        textFieldsPane.add(carnetField);
        textFieldsPane.add(Box.createRigidArea(new Dimension(0, 5)));
        textFieldsPane.add(carnetFormato);
        gap(0, 16);

        JPanel borderAgregarBoton = new JPanel(new BorderLayout());
        borderAgregarBoton.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        borderAgregarBoton.add(realizarPrestamoBoton, BorderLayout.CENTER);
        realizarPrestamoBoton.setPreferredSize(new Dimension(100, 45));
        add(borderTextFieldsPane, BorderLayout.CENTER);
        add(borderAgregarBoton, BorderLayout.SOUTH);
    }

    private void realizarPrestamos() throws RegistroFallidoMensajeException {
        String codigoLibro1 = codigoLibroField1.getText();
        String codigoLibro2 = codigoLibroField2.getText();
        String codigoLibro3 = codigoLibroField3.getText();
        String carnet = carnetField.getText();

        // validaciones carnet
        if (carnet.isBlank())
            throw new RegistroFallidoMensajeException("El carnet no puede estar vacio");
        if (!biblioteca.existeObjeto(biblioteca.getEstudiantes(), carnet))
            throw new RegistroFallidoMensajeException("El estudiante con carnet " + carnet + " no existe, por favor registrelo");
        try {
            Integer.parseInt(carnet);
        } catch (NumberFormatException e) {
            throw new RegistroFallidoMensajeException("El carnet solo puede incluir numeros");
        }

        // validaciones libro
        if (codigoLibro1.isBlank())
            throw new RegistroFallidoMensajeException("El codigo del primer libro no puede estar vacio");
        if (!Pattern.matches("\\d{3}-[A-Z]{3}", codigoLibro1))
            throw new RegistroFallidoMensajeException("El codigo de libro " + codigoLibro1 + " es invalido");
        if (!codigoLibro2.isBlank() && !Pattern.matches("\\d{3}-[A-Z]{3}", codigoLibro2))
            throw new RegistroFallidoMensajeException("El codigo de libro " + codigoLibro2 + " es invalido");
        if (!codigoLibro3.isBlank() && !Pattern.matches("\\d{3}-[A-Z]{3}", codigoLibro3))
            throw new RegistroFallidoMensajeException("El codigo de libro " + codigoLibro3 + " es invalido");
        if (!biblioteca.existeObjeto(biblioteca.getLibros(), codigoLibro1))
            throw new RegistroFallidoMensajeException("El libro con codigo " + codigoLibro1 + " no existe, por favor registrelo");
        if (!codigoLibro2.isBlank() && !biblioteca.existeObjeto(biblioteca.getLibros(), codigoLibro2))
            throw new RegistroFallidoMensajeException("El libro con codigo " + codigoLibro2 + " no existe, por favor registrelo");
        if (!codigoLibro3.isBlank() && !biblioteca.existeObjeto(biblioteca.getLibros(), codigoLibro3))
            throw new RegistroFallidoMensajeException("El libro con codigo " + codigoLibro3 + " no existe, por favor registrelo");

        ControladorArchivoBinario cab = new ControladorArchivoBinario();

        registrarPrestamo(carnet, codigoLibro1);
        if (!codigoLibro2.isBlank())
            registrarPrestamo(carnet, codigoLibro2);
        if (!codigoLibro3.isBlank()) registrarPrestamo(carnet, codigoLibro3);

    }

    public void registrarPrestamo(String carnet, String codigoLibro) throws RegistroFallidoMensajeException {
        try {
            Estudiante estudiante = null;
            for (Estudiante es : biblioteca.getEstudiantes()) {
                if (es.getIdentificador().equals(carnet)) estudiante = es;
            }
            Libro libro = null;
            for (Libro lb : biblioteca.getLibros()) {
                if (lb.getIdentificador().equals(codigoLibro)) libro = lb;
            }
            if(libro.getCopias() <= 0)
                throw new RegistroFallidoMensajeException("El libro con codigo " + codigoLibro + " ya no tiene existencias");

            if (estudiante.tienePrestamosDisponibles()) {
                Prestamo prestamo = new Prestamo(codigoLibro, carnet, LocalDate.now().toString());
                estudiante.agregarPrestamo(prestamo);


                String nombreArchivoPrestamo = "./prestamos/" + prestamo.getIdentificador() + ".bin";
                String nombreArchivoEstudiante = "./estudiantes/" + estudiante.getIdentificador() + ".bin";

                if (new File(nombreArchivoPrestamo).exists())
                    throw new RegistroFallidoException("El archivo " + nombreArchivoPrestamo + ".bin ya existe", prestamo.getIdentificador(), TipoRegistroFallido.PRESTAMO.getCodigo());

                libro.prestarLibro();

                ControladorArchivoBinario cab = new ControladorArchivoBinario();
                cab.escribirObjeto(nombreArchivoPrestamo, prestamo);
                cab.escribirObjeto(nombreArchivoEstudiante, estudiante);
            } else
                throw new RegistroFallidoMensajeException("El estudiante con carnet " + carnet + " ya tiene 3 prestamos activos");

        } catch (ParseException e) {
            throw new RegistroFallidoMensajeException("Hubo un problema obteniendo la fecha actual");
        } catch (RegistroFallidoException e) {
            throw new RegistroFallidoMensajeException(e.getMessage());
        }

    }

    private Font getFuenteConTamano(int tamano) {
        return new Font("Arial", Font.PLAIN, tamano);
    }

    private void gap(int w, int h) {
        textFieldsPane.add(Box.createRigidArea(new Dimension(w, h)));
    }


}
