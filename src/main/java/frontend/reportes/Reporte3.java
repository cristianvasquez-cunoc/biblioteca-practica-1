package frontend.reportes;

import backend.Prestamo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

public class Reporte3 extends JPanel {

    private JLabel titulo;
    private JLabel textoIntervalo;
    private JButton buscarBoton;
    private JTextField fechaFinalField;
    private JTextField fechaInicioField;

    private JPanel contenido;
    private JTable tabla;
    private JScrollPane scrollPane;
    private JLabel totalDineroRecaudadoText;
    private JLabel totalMoraText;
    private JLabel totalPrestamosText;

    private List<Prestamo> prestamos;
    private List<Prestamo> prestamosFiltrados;
    private Object[][] informacion;
    private String[] columnas;


    public Reporte3(List<Prestamo> prestamos) {

        this.prestamos = prestamos;

        construirPanel();


        buscarBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    buscarTotal();
                } catch (RuntimeException exception) {
                    JOptionPane.showMessageDialog(getParent(), "Las fechas ingresadas no estan en el formato correcto", "Error realizar reporte", JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    public void buscarTotal() {

        String fechaInicio = fechaInicioField.getText();
        String fechaFinal = fechaFinalField.getText();

        if (!fechaInicio.isBlank() && !fechaFinal.isBlank()) {
            prestamosFiltrados = prestamos.stream().filter(prestamo -> {
                try {
                    return (prestamo.getFecha().after(new SimpleDateFormat("yyyy-MM-dd").parse(fechaInicio)) && new SimpleDateFormat("yyyy-MM-dd").parse(fechaFinal).after(prestamo.getFecha()));
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }).toList();
        } else {
            prestamosFiltrados = prestamos;
        }
        int totalMoras = 0;
        int totalPrestamos = 0;
        int totalRecaudado = 0;

        for (Prestamo prestamo : prestamosFiltrados) {

            int totalPrestamo = 0;
            int diasExtras = (int) ((System.currentTimeMillis() - prestamo.getFecha().getTime()) / (1000 * 60 * 60 * 24)) - 3;
            int cargoTotalMora = diasExtras > 0 ? diasExtras * 10 : 0;
            int cargoDiasPrestados = (diasExtras + 3) * 5;
            totalPrestamo = cargoTotalMora + cargoDiasPrestados;

            totalMoras += cargoTotalMora;
            totalPrestamos += cargoDiasPrestados;
            totalRecaudado += totalPrestamo;

        }

        totalDineroRecaudadoText.setText("Total dinero recaudado: Q" + totalRecaudado + ".00");
        totalMoraText.setText("Total dinero en mora: Q" + totalMoras + ".00");
        totalPrestamosText.setText("Total dinero en solo prestamos: Q" + totalPrestamos + ".00");

        columnas = new String[]{"Fecha prestamo", "Estado", "Codigo del libro", "Carnet del estudiante"};
        tabla = new JTable();
        scrollPane = new JScrollPane(tabla);
        contenido.add(scrollPane, BorderLayout.CENTER);

        informacion = getInformacion();
        DefaultTableModel model = new DefaultTableModel(informacion, columnas);
        tabla.setModel(model);

    }

    public Object[][] getInformacion() {

        Object[][] informacion = new Object[prestamosFiltrados.size()][columnas.length];
        for (int i = 0; i < prestamosFiltrados.size(); i++) {
            Prestamo prestamo = prestamosFiltrados.get(i);
            informacion[i][0] = new SimpleDateFormat("yyyy-MM-dd").format(prestamo.getFecha());
            informacion[i][1] = prestamo.getEstado();
            informacion[i][2] = prestamo.getCodigoLibro();
            informacion[i][3] = prestamo.getCarnet();
        }
        return informacion;

    }

    public void construirPanel() {
        setLayout(new BorderLayout());

        JPanel header = new JPanel(new BorderLayout());
        titulo = new JLabel("Total de dinero recaudado");
        titulo.setFont(new Font("Arial", Font.PLAIN, 24));

        buscarBoton = new JButton("Buscar prestamo");
        buscarBoton.setSize(100, 30);

        JPanel content = new JPanel(new BorderLayout());
        textoIntervalo = new JLabel("Ingrese un intervalo de fechas en formato yyyy-MM-dd");
        textoIntervalo.setFont(new Font("Arial", Font.PLAIN, 20));
        content.add(textoIntervalo, BorderLayout.NORTH);

        JPanel intervalos = new JPanel(new FlowLayout());

        JPanel fechaInicioPanel = new JPanel(new BorderLayout());
        JLabel fechaInicioTexto = new JLabel("Fecha inicio");
        fechaInicioPanel.add(fechaInicioTexto, BorderLayout.NORTH);
        fechaInicioField = new JTextField();
        fechaInicioPanel.add(fechaInicioField, BorderLayout.CENTER);
        intervalos.add(fechaInicioPanel);


        JPanel fechaFinalPanel = new JPanel(new BorderLayout());
        JLabel fechaFinalTexto = new JLabel("Fecha inicio");
        fechaFinalPanel.add(fechaFinalTexto, BorderLayout.NORTH);
        fechaFinalField = new JTextField();
        fechaFinalPanel.add(fechaFinalField, BorderLayout.CENTER);
        intervalos.add(fechaFinalPanel);

        content.add(intervalos, BorderLayout.CENTER);

        header.add(titulo, BorderLayout.NORTH);
        header.add(buscarBoton, BorderLayout.SOUTH);
        header.add(content, BorderLayout.CENTER);
        add(header, BorderLayout.NORTH);

        contenido = new JPanel(new BorderLayout());
        JPanel datos = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 16));
        totalDineroRecaudadoText = new JLabel("Total dinero recaudado: ");
        totalMoraText = new JLabel("Total dinero en mora: ");
        totalPrestamosText = new JLabel("Total dinero en solo prestamos");
        datos.add(totalDineroRecaudadoText);
        datos.add(totalMoraText);
        datos.add(totalPrestamosText);
        contenido.add(datos, BorderLayout.NORTH);
        add(contenido, BorderLayout.CENTER);
    }

}
