package frontend.prestamos;

import backend.Biblioteca;
import backend.Estudiante;
import backend.Prestamo;
import backend.interfaces.Identificable;
import backend.lectortxt.LectorTxt;
import frontend.registros.RegistroFallidoMensajeException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class FinalizarPrestamoPanel extends JPanel {

    private JLabel titulo;
    private JLabel carnetTexto;
    private JLabel carnetFormato;
    private JTextField carnetField;

    private JLabel codigoLibroTexto;
    private JLabel codigoLibroFormato;
    private JTextField codigoLibroField;

    private JButton finalizarPrestamoBoton;
    private JButton buscarPrestamoBoton;

    private JPanel textFieldsPane;

    private Biblioteca biblioteca;
    private LectorTxt lectorTxt;

    private JLabel cargoMoraText;
    private JLabel cargoDiasPrestadosText;
    private JLabel totalText;

    private int cargoTotalMora;
    private int cargoDiasPrestados;
    private int total;

    public FinalizarPrestamoPanel(Biblioteca biblioteca, LectorTxt lectorTxt) {
        this.biblioteca = biblioteca;
        this.lectorTxt = lectorTxt;

        crearPanel();

        buscarPrestamoBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    buscarInformacionPrestamo();
                } catch (RegistroFallidoMensajeException ex) {
                    JOptionPane.showMessageDialog(getParent(), ex.getMessage(), "Error al buscar prestamo", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

    }

    public void crearPanel () {
        titulo = new JLabel("Finalizar Prestamo");
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
        add(textFieldsPane, BorderLayout.CENTER);

        carnetTexto = new JLabel("Carnet *");
        carnetTexto.setFont(getFuenteConTamano(14));
        carnetField = new JTextField();
        carnetField.setBorder(BorderFactory.createCompoundBorder(
                carnetField.getBorder(),
                BorderFactory.createEmptyBorder(0, 10, 0, 0)));
        carnetFormato = new JLabel("El carnet es un numero");
        carnetFormato.setFont(getFuenteConTamano(12));

        codigoLibroFormato = new JLabel("Codigo de la forma 123-ABC");
        codigoLibroFormato.setFont(getFuenteConTamano(12));
        codigoLibroTexto = new JLabel("Codigo del libro *");
        codigoLibroTexto.setFont(getFuenteConTamano(14));
        codigoLibroField = new JTextField();
        codigoLibroField.setBorder(BorderFactory.createCompoundBorder(
                codigoLibroField.getBorder(),
                BorderFactory.createEmptyBorder(0, 10, 0, 0)));

        JPanel prestamoInformacionPanel = new JPanel();
        JLabel subtitulo = new JLabel("Informacion del prestamo: ");
        subtitulo.setFont(getFuenteConTamano(20));
        cargoMoraText = new JLabel("Cargo total de mora: ");
        cargoMoraText.setFont(getFuenteConTamano(14));
        cargoDiasPrestadosText = new JLabel("Cargos por dias prestados: ");
        cargoDiasPrestadosText.setFont(getFuenteConTamano(14));
        totalText = new JLabel("Total: ");
        totalText.setFont(getFuenteConTamano(14));

        borderTextFieldsPane.add(prestamoInformacionPanel, BorderLayout.SOUTH);

        finalizarPrestamoBoton = new JButton("Pago realizado, finalizar prestamo");
        buscarPrestamoBoton = new JButton("Buscar prestamo");

        textFieldsPane.add(codigoLibroTexto);
        textFieldsPane.add(Box.createRigidArea(new Dimension(0, 5)));
        textFieldsPane.add(codigoLibroField);
        textFieldsPane.add(Box.createRigidArea(new Dimension(0, 5)));
        textFieldsPane.add(codigoLibroFormato);
        gap(0, 16);

        textFieldsPane.add(carnetTexto);
        textFieldsPane.add(Box.createRigidArea(new Dimension(0, 5)));
        textFieldsPane.add(carnetField);
        textFieldsPane.add(Box.createRigidArea(new Dimension(0, 5)));
        textFieldsPane.add(carnetFormato);
        gap(0, 16);

        textFieldsPane.add(buscarPrestamoBoton);
        gap(0, 16);


        JPanel borderPanelPrestamoInformacion = new JPanel(new BorderLayout());
        subtitulo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 0));
        borderPanelPrestamoInformacion.add(subtitulo, BorderLayout.NORTH);

        add(borderPanelPrestamoInformacion, BorderLayout.SOUTH);

        prestamoInformacionPanel.setLayout(new BoxLayout(prestamoInformacionPanel, BoxLayout.Y_AXIS));
        prestamoInformacionPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        prestamoInformacionPanel.add(cargoMoraText);
        prestamoInformacionPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        prestamoInformacionPanel.add(cargoDiasPrestadosText);
        prestamoInformacionPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        prestamoInformacionPanel.add(totalText);
        prestamoInformacionPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        borderPanelPrestamoInformacion.add(prestamoInformacionPanel, BorderLayout.CENTER);


        JPanel borderAgregarBoton = new JPanel(new BorderLayout());
        borderAgregarBoton.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        borderAgregarBoton.add(finalizarPrestamoBoton, BorderLayout.CENTER);
        finalizarPrestamoBoton.setPreferredSize(new Dimension(100, 45));
        borderPanelPrestamoInformacion.add(borderAgregarBoton, BorderLayout.SOUTH);
    }

    public void buscarInformacionPrestamo() throws RegistroFallidoMensajeException {
        String codigo = codigoLibroField.getText();
        String carnet = carnetField.getText();

        if (carnet.isBlank())
            throw new RegistroFallidoMensajeException("El carnet no puede estar vacio");
        if (codigo.isBlank())
            throw new RegistroFallidoMensajeException("El codigo del libro no puede estar vacio");

        if(biblioteca.existeObjeto(biblioteca.getEstudiantes(), carnet)) {
            Estudiante estudiante = null;
            for(Estudiante est : biblioteca.getEstudiantes()) {
                if(est.getIdentificador().equals(carnet)) estudiante = est;
            }

            List<Prestamo> prestamos = estudiante.getPrestamos();
            Prestamo prestamo = null;
            for(Prestamo prest : prestamos) {
                if(prest.getCarnet().equals(codigo)) prestamo = prest;
            }

            if(prestamo == null)
                throw new RegistroFallidoMensajeException("El estudiante no ha prestado el libro con codigo: " + codigo);

            int diasExtras = (int) ((System.currentTimeMillis() - prestamo.getFecha().getTime()) / (1000 * 60 * 60 * 24));
            cargoTotalMora = diasExtras > 0 ? diasExtras * 10 : 0;
            cargoMoraText.setText("Cargo total de mora: " + cargoTotalMora);

            cargoDiasPrestados = diasExtras + 3;
            cargoDiasPrestadosText.setText("Cargos por dias prestados: " + cargoDiasPrestados * 5);
            totalText.setText("Total: " + cargoTotalMora );

        }else {
            throw new RegistroFallidoMensajeException("El estudiante con ese carnet no existe");
        }
    }

    private Font getFuenteConTamano(int tamano) {
        return new Font("Arial", Font.PLAIN, tamano);
    }

    private void gap(int w, int h) {
        textFieldsPane.add(Box.createRigidArea(new Dimension(w, h)));
    }
}
