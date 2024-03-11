package frontend.reportes;

import backend.Biblioteca;
import backend.Prestamo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

public class Reporte2 extends Reporte {


    public Reporte2(Biblioteca biblioteca) {
        super(biblioteca, "Listado de préstamos de libros que están en mora.");
        columnas = new String[]{"Fecha prestamo", "Fecha de entrega", "Estado", "Codigo del libro", "Carnet del estudiante"};
        informacion = getInformacion();
        DefaultTableModel model = new DefaultTableModel(informacion, columnas);
        table.setModel(model);
    }

    @Override
    public Object[][] getInformacion() {
        List<Prestamo> prestamosFiltrados;

        prestamosFiltrados = prestamos.stream().filter(prestamo -> {
            Date fechaActual = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date fechaEntrega = Date.from(prestamo.getFecha().toInstant().plus(Duration.ofDays(3)));

            return fechaActual.after(fechaEntrega);
        }).toList();

        Object[][] informacion = new Object[prestamosFiltrados.size()][columnas.length];
        for (int i = 0; i < prestamosFiltrados.size(); i++) {
            Prestamo prestamo = prestamosFiltrados.get(i);
            informacion[i][0] = new SimpleDateFormat("yyyy-MM-dd").format(prestamo.getFecha());
            informacion[i][1] = new SimpleDateFormat("yyyy-MM-dd").format(Date.from(prestamo.getFecha().toInstant().plus(Duration.ofDays(3))));
            informacion[i][2] = prestamo.getEstado();
            informacion[i][3] = prestamo.getCodigoLibro();
            informacion[i][4] = prestamo.getCarnet();
        }
        return informacion;
    }
}
