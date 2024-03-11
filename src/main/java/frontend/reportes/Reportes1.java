package frontend.reportes;

import backend.Biblioteca;
import backend.Prestamo;
import backend.lectortxt.RegistroFallido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Reportes1 extends JPanel{

    private JLabel titulo;
    private JTable table;
    private JScrollPane jScrollPane;

    private List<Prestamo> prestamos;
    private Object[][] informacion;

    private String[] columnas;

    public Reportes1(Biblioteca biblioteca) {
        setLayout(new BorderLayout());

        titulo = new JLabel("Listado de préstamos de libros que deben ser entregados el día actual.");
        titulo.setFont(new Font("Arial", Font.PLAIN, 24));
        titulo.setBounds(20, 20, 100, 100);
        add(titulo, BorderLayout.NORTH);

        columnas = new String[]{"Fecha prestamo", "Estado", "Codigo del libro", "Carnet del estudiante"};
        prestamos = biblioteca.getPrestamos();
        informacion = getInformacion();
        DefaultTableModel model = new DefaultTableModel(informacion, columnas);
        table = new JTable(model);
        TableColumnModel tableColumnModel = table.getColumnModel();
        jScrollPane = new JScrollPane(table);
        add(jScrollPane, BorderLayout.CENTER);

    }

    private Object[][] getInformacion() {

        List<Prestamo> prestamosFiltrados;

        prestamosFiltrados = prestamos.stream().filter(prestamo -> {
            String fechaActual = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String fechaEntrega = new SimpleDateFormat("yyyy-MM-dd").format(Date.from(prestamo.getFecha().toInstant().plus(Duration.ofDays(3))));

            return fechaActual.equals(fechaEntrega);
        }).toList();

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
}
