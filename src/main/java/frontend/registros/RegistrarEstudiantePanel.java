package frontend.registros;

import backend.Biblioteca;
import backend.Estudiante;
import backend.enums.TipoRegistroFallido;
import backend.lectortxt.LectorTxt;
import backend.lectortxt.RegistroFallidoException;
import backend.lectortxt.controllers.ControladorArchivoBinario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class RegistrarEstudiantePanel extends JPanel {
    private JLabel titulo;
    private JLabel carnetTexto;
    private JLabel carnetFormato;
    private JTextField carnetField;
    private JLabel nombreTexto;
    private JTextField nombreField;
    private JLabel codigoCarreraTexto;
    private JTextField codigoCarreraField;
    private JLabel codigoCarreraFormato;
    private JLabel fechaNacimientoTexto;
    private JTextField fechaNacimientoField;
    private JLabel fechaNacimientoFormato;
    private JPanel borderTextFieldsPane;
    private JPanel textFieldsPane;
    private JButton agregarBoton;

    private Biblioteca biblioteca;

    public RegistrarEstudiantePanel(Biblioteca biblioteca, LectorTxt lectorTxt) {

        this.biblioteca = biblioteca;

        construirJPanel();

        agregarBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ControladorArchivoBinario cab = new ControladorArchivoBinario();
                    Estudiante estudiante = registrarEstudiante();
                    lectorTxt.guardarObjeto(estudiante, cab);
                    JOptionPane.showMessageDialog(getParent(), "El estudiante se agrego exitosamente", "Estudiante agregado", JOptionPane.INFORMATION_MESSAGE);
                    carnetField.setText("");
                    nombreField.setText("");
                    codigoCarreraField.setText("");
                    fechaNacimientoField.setText("");
                } catch (RegistroFallidoMensajeException ex) {
                    JOptionPane.showMessageDialog(getParent(), ex.getMessage(), "Error al agregar estudiante", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    private void construirJPanel() {
        titulo = new JLabel("Registrar Estudiante");
        titulo.setFont(getFuenteConTamano(24));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 0));

        setLayout(new BorderLayout());
        add(titulo, BorderLayout.NORTH);

        borderTextFieldsPane = new JPanel();
        borderTextFieldsPane.setLayout(new BorderLayout());

        textFieldsPane = new JPanel();
        textFieldsPane.setLayout(new BoxLayout(textFieldsPane, BoxLayout.Y_AXIS));
        textFieldsPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        borderTextFieldsPane.add(textFieldsPane, BorderLayout.CENTER);

        carnetTexto = new JLabel("Carnet *");
        carnetTexto.setFont(getFuenteConTamano(14));
        carnetField = new JTextField();
        carnetField.setBorder(BorderFactory.createCompoundBorder(
                carnetField.getBorder(),
                BorderFactory.createEmptyBorder(0, 10, 0, 0)));
        carnetFormato = new JLabel("El carnet es un numero");
        carnetFormato.setFont(getFuenteConTamano(12));

        nombreTexto = new JLabel("Nombre *");
        nombreTexto.setFont(getFuenteConTamano(14));
        nombreField = new JTextField();
        nombreField.setBorder(BorderFactory.createCompoundBorder(
                nombreField.getBorder(),
                BorderFactory.createEmptyBorder(0, 10, 0, 0)));

        codigoCarreraTexto = new JLabel("Codigo carrera *");
        codigoCarreraTexto.setFont(getFuenteConTamano(14));
        codigoCarreraField = new JTextField();
        codigoCarreraField.setBorder(BorderFactory.createCompoundBorder(
                codigoCarreraField.getBorder(),
                BorderFactory.createEmptyBorder(0, 10, 0, 0)));
        codigoCarreraFormato = new JLabel("Debe ser un numero entre 1 a 6");
        codigoCarreraFormato.setFont(getFuenteConTamano(12));

        fechaNacimientoTexto = new JLabel("Fecha de nacimiento");
        fechaNacimientoTexto.setFont(getFuenteConTamano(14));
        fechaNacimientoField = new JTextField();
        fechaNacimientoField.setBorder(BorderFactory.createCompoundBorder(
                fechaNacimientoField.getBorder(),
                BorderFactory.createEmptyBorder(0, 10, 0, 0)));
        fechaNacimientoFormato = new JLabel("Ingrese la fecha en formato yyyy-mm-dd");
        fechaNacimientoFormato.setFont(getFuenteConTamano(12));

        agregarBoton = new JButton("Agregar estudiante");

        textFieldsPane.add(carnetTexto);
        textFieldsPane.add(Box.createRigidArea(new Dimension(0, 5)));
        textFieldsPane.add(carnetField);
        textFieldsPane.add(Box.createRigidArea(new Dimension(0, 5)));
        textFieldsPane.add(carnetFormato);
        gap(0, 16);

        textFieldsPane.add(nombreTexto);
        textFieldsPane.add(Box.createRigidArea(new Dimension(0, 5)));
        textFieldsPane.add(nombreField);
        gap(0, 16);

        textFieldsPane.add(codigoCarreraTexto);
        textFieldsPane.add(Box.createRigidArea(new Dimension(0, 5)));
        textFieldsPane.add(codigoCarreraField);
        textFieldsPane.add(Box.createRigidArea(new Dimension(0, 5)));
        textFieldsPane.add(codigoCarreraFormato);
        gap(0, 16);

        textFieldsPane.add(fechaNacimientoTexto);
        textFieldsPane.add(Box.createRigidArea(new Dimension(0, 5)));
        textFieldsPane.add(fechaNacimientoField);
        textFieldsPane.add(Box.createRigidArea(new Dimension(0, 5)));
        textFieldsPane.add(fechaNacimientoFormato);
        gap(0, 16);

        JPanel borderAgregarBoton = new JPanel(new BorderLayout());
        borderAgregarBoton.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        borderAgregarBoton.add(agregarBoton, BorderLayout.CENTER);
        agregarBoton.setPreferredSize(new Dimension(100, 45));
        add(borderTextFieldsPane, BorderLayout.CENTER);
        add(borderAgregarBoton, BorderLayout.SOUTH);
    }

    private Estudiante registrarEstudiante() throws RegistroFallidoMensajeException {

        String carnet = carnetField.getText();
        String nombre = nombreField.getText();
        String codigoCarreraTexto = codigoCarreraField.getText();
        String fechaNacimiento = fechaNacimientoField.getText();

        if (carnet.isBlank())
            throw new RegistroFallidoMensajeException("El carnet no puede estar vacio");
        if (nombre.isBlank())
            throw new RegistroFallidoMensajeException("El nombre no puede estar vacio");

        if (biblioteca.existeObjeto(biblioteca.getEstudiantes(), carnet))
            throw new RegistroFallidoMensajeException("El estudiante con carnet " + carnet + " ya existe, por favor ingrese otro");

        int codigoCarrera;
        try {
            codigoCarrera = Integer.parseInt(codigoCarreraTexto);

        } catch (NumberFormatException e) {
            throw new RegistroFallidoMensajeException("El codigo de carrera no puede estar vacio");
        }


        try {
            Estudiante estudiante;

            if (fechaNacimiento.isBlank())
                estudiante = new Estudiante(carnet, nombre, codigoCarrera);
            else {
                if (LocalDate.parse(fechaNacimiento).isAfter(LocalDate.now()))
                    throw new RegistroFallidoMensajeException("La fecha ingresada aun no ha pasado");
                estudiante = new Estudiante(carnet, nombre, codigoCarrera, fechaNacimiento);
            }

            Integer.parseInt(carnet);

            return estudiante;

        } catch (NumberFormatException e) {
            throw new RegistroFallidoMensajeException("El carnet solo puede incluir numeros");
        } catch (IllegalArgumentException e) {
            throw new RegistroFallidoMensajeException("No existe carrera con el código: " + codigoCarreraTexto);
        } catch (DateTimeParseException | ParseException e) {
            throw new RegistroFallidoMensajeException("La fecha ingresada no es valida, por favor ingrese una en formato yyyy-mm-dd");
        }


    }

    private void gap(int w, int h) {
        textFieldsPane.add(Box.createRigidArea(new Dimension(w, h)));
    }

    ;

    private Font getFuenteConTamano(int tamano) {
        return new Font("Arial", Font.PLAIN, tamano);
    }
}
