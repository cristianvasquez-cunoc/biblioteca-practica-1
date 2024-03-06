package frontend.registros;

import backend.Biblioteca;
import backend.Estudiante;
import backend.Libro;
import backend.enums.TipoRegistroFallido;
import backend.lectortxt.LectorTxt;
import backend.lectortxt.RegistroFallidoException;
import backend.lectortxt.controllers.ControladorArchivoBinario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;

public class RegistrarLibroPanel extends JPanel {
    private JLabel titulo;
    private JLabel codigoTexto;
    private JLabel codigoFormato;
    private JTextField codigoField;
    private JLabel autorTexto;
    private JTextField autorField;
    private JLabel tituloTexto;
    private JTextField tituloField;
    private JLabel cantidadTexto;
    private JTextField cantidadField;
    private JLabel cantidadFormato;
    private JLabel fechaPublicacionTexto;
    private JTextField fechaPublicacionField;
    private JLabel fechaPublicacionFormato;
    private JLabel editorialTexto;
    private JTextField editorialField;
    private JPanel borderTextFieldsPane;
    private JPanel textFieldsPane;
    private JButton agregarBoton;

    private Biblioteca biblioteca;

    public RegistrarLibroPanel(Biblioteca biblioteca, LectorTxt lectorTxt) {
        this.biblioteca = biblioteca;

        construirJPanel();

        agregarBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ControladorArchivoBinario cab = new ControladorArchivoBinario();
                    Libro libro = registrarLibro();
                    lectorTxt.guardarObjeto(libro, cab);
                    JOptionPane.showMessageDialog(getParent(), "El libro se agrego exitosamente", "Libro agregado", JOptionPane.INFORMATION_MESSAGE);
                    codigoField.setText("");
                    autorField.setText("");
                    tituloField.setText("");
                    cantidadField.setText("");
                    fechaPublicacionField.setText("");
                    editorialField.setText("");
                } catch (RegistroFallidoMensajeException ex) {
                    JOptionPane.showMessageDialog(getParent(), ex.getMessage(), "Error al agregar libro", JOptionPane.WARNING_MESSAGE);
                }

            }
        });
    }

    private Libro registrarLibro() throws RegistroFallidoMensajeException {

        String codigo = codigoField.getText();
        String autor = autorField.getText();
        String titulo = tituloField.getText();
        String cantidad = cantidadField.getText();
        String fechaPublicacion = fechaPublicacionField.getText();
        String editorial = editorialField.getText();


        if (codigo.isBlank())
            throw new RegistroFallidoMensajeException("El codigo no puede estar vacio");
        if (autor.isBlank())
            throw new RegistroFallidoMensajeException("El autor no puede estar vacio");
        if (titulo.isBlank())
            throw new RegistroFallidoMensajeException("El titulo no puede estar vacio");
        if (cantidad.isBlank())
            throw new RegistroFallidoMensajeException("La cantidad de copias no puede estar vacio");
        if (biblioteca.existeObjeto(biblioteca.getLibros(), codigo))
            throw new RegistroFallidoMensajeException("El libro con codigo " + codigo + " ya existe, por favor ingrese otro");
        if (!Pattern.matches("\\d{3}-[A-Z]{3}", codigo))
            throw new RegistroFallidoMensajeException("El codigo del libro es invalido");

        int cantidadNumero;
        try {
            cantidadNumero = Integer.parseInt(cantidad);
            if (cantidadNumero < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            throw new RegistroFallidoMensajeException("La cantidad debe ser un numero positivo");
        }


        try {
            Libro libro;

            if (fechaPublicacion.isBlank() && editorial.isBlank())
                libro = new Libro(codigo, autor, titulo, cantidadNumero);
            else {
                if (!fechaPublicacion.isBlank() && LocalDate.parse(fechaPublicacion).isAfter(LocalDate.now()))
                    throw new RegistroFallidoMensajeException("La fecha ingresada aun no ha pasado");
                libro = new Libro(codigo, autor, titulo, cantidadNumero, fechaPublicacion, editorial);
            }

            return libro;

        } catch (DateTimeParseException | ParseException e) {
            throw new RegistroFallidoMensajeException("La fecha ingresada no es valida, por favor ingrese una en formato yyyy-mm-dd");
        }


    }

    private void construirJPanel() {
        titulo = new JLabel("Registrar Libro");
        titulo.setFont(getFuenteConTamano(24));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 0));

        setLayout(new BorderLayout());
        add(titulo, BorderLayout.NORTH);

        borderTextFieldsPane = new JPanel();
        borderTextFieldsPane.setLayout(new BorderLayout());

        textFieldsPane = new JPanel();
        textFieldsPane.setLayout(new BoxLayout(textFieldsPane, BoxLayout.Y_AXIS));
        textFieldsPane.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        borderTextFieldsPane.add(textFieldsPane, BorderLayout.CENTER);

        codigoTexto = new JLabel("Código *");
        codigoTexto.setFont(getFuenteConTamano(14));
        codigoField = new JTextField();
        codigoField.setBorder(BorderFactory.createCompoundBorder(
                codigoField.getBorder(),
                BorderFactory.createEmptyBorder(5, 10, 5, 0)));
        codigoFormato = new JLabel("Codigo de la forma 123-ABC");
        codigoFormato.setFont(getFuenteConTamano(12));

        autorTexto = new JLabel("Autor *");
        autorTexto.setFont(getFuenteConTamano(14));
        autorField = new JTextField();
        autorField.setBorder(BorderFactory.createCompoundBorder(
                autorField.getBorder(),
                BorderFactory.createEmptyBorder(5, 10, 5, 0)));

        tituloTexto = new JLabel("Título *");
        tituloTexto.setFont(getFuenteConTamano(14));
        tituloField = new JTextField();
        tituloField.setBorder(BorderFactory.createCompoundBorder(
                tituloField.getBorder(),
                BorderFactory.createEmptyBorder(5, 10, 5, 0)));

        cantidadTexto = new JLabel("Cantidad de Copias *");
        cantidadTexto.setFont(getFuenteConTamano(14));
        cantidadField = new JTextField();
        cantidadField.setBorder(BorderFactory.createCompoundBorder(
                cantidadField.getBorder(),
                BorderFactory.createEmptyBorder(5, 10, 5, 0)));
        cantidadFormato = new JLabel("Debe ser un numero positivo");
        cantidadFormato.setFont(getFuenteConTamano(12));

        fechaPublicacionTexto = new JLabel("Fecha de Publicación");
        fechaPublicacionTexto.setFont(getFuenteConTamano(14));
        fechaPublicacionField = new JTextField();
        fechaPublicacionField.setBorder(BorderFactory.createCompoundBorder(
                fechaPublicacionField.getBorder(),
                BorderFactory.createEmptyBorder(5, 10, 5, 0)));
        fechaPublicacionFormato = new JLabel("Fecha en formato yyyy-mm-dd");
        fechaPublicacionFormato.setFont(getFuenteConTamano(12));

        editorialTexto = new JLabel("Editorial");
        editorialTexto.setFont(getFuenteConTamano(14));
        editorialField = new JTextField();
        editorialField.setBorder(BorderFactory.createCompoundBorder(
                editorialField.getBorder(),
                BorderFactory.createEmptyBorder(5, 10, 5, 0)));

        agregarBoton = new JButton("Agregar Libro");

        textFieldsPane.add(codigoTexto);
        textFieldsPane.add(codigoField);
        textFieldsPane.add(codigoFormato);
        gap(0, 16);

        textFieldsPane.add(autorTexto);
        textFieldsPane.add(autorField);
        gap(0, 16);

        textFieldsPane.add(tituloTexto);
        textFieldsPane.add(tituloField);
        gap(0, 16);

        textFieldsPane.add(cantidadTexto);
        textFieldsPane.add(cantidadField);
        textFieldsPane.add(cantidadFormato);
        gap(0, 16);

        textFieldsPane.add(fechaPublicacionTexto);
        textFieldsPane.add(fechaPublicacionField);
        textFieldsPane.add(fechaPublicacionFormato);
        gap(0, 16);

        textFieldsPane.add(editorialTexto);
        textFieldsPane.add(editorialField);

        JPanel borderAgregarBoton = new JPanel(new BorderLayout());
        borderAgregarBoton.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        borderAgregarBoton.add(agregarBoton, BorderLayout.CENTER);
        agregarBoton.setPreferredSize(new Dimension(150, 45));
        add(borderTextFieldsPane, BorderLayout.CENTER);
        add(borderAgregarBoton, BorderLayout.SOUTH);
    }

    private Font getFuenteConTamano(int tamano) {
        return new Font("Arial", Font.PLAIN, tamano);
    }

    private void gap(int w, int h) {
        textFieldsPane.add(Box.createRigidArea(new Dimension(w, h)));
    }
}