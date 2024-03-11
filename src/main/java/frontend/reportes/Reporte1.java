package frontend.reportes;

import backend.Biblioteca;
import backend.Prestamo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

public class Reporte1 extends Reporte{

    public Reporte1(Biblioteca biblioteca) {
        super(biblioteca, "Listado de préstamos de libros que deben ser entregados el día actual.");
        informacion = getInformacion();
        DefaultTableModel model = new DefaultTableModel(informacion, columnas);
        table.setModel(model);
    }

    @Override
    public Object[][] getInformacion() {

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
