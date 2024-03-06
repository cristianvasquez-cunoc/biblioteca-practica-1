package frontend.registros;

import javax.swing.*;
import java.awt.*;

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

    public RegistrarLibroPanel() {
        construirJPanel();

        agregarBoton.addActionListener(e -> System.out.println("Boton presionado"));
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
        gap(0,16);

        textFieldsPane.add(autorTexto);
        textFieldsPane.add(autorField);
        gap(0,16);

        textFieldsPane.add(tituloTexto);
        textFieldsPane.add(tituloField);
        gap(0,16);

        textFieldsPane.add(cantidadTexto);
        textFieldsPane.add(cantidadField);
        textFieldsPane.add(cantidadFormato);
        gap(0,16);

        textFieldsPane.add(fechaPublicacionTexto);
        textFieldsPane.add(fechaPublicacionField);
        textFieldsPane.add(fechaPublicacionFormato);
        gap(0,16);

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