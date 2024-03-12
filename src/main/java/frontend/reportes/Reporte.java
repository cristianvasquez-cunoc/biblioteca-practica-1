package frontend.reportes;

import backend.Biblioteca;
import backend.Prestamo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.List;

public abstract class Reporte extends JPanel{
    private JLabel titulo;
    protected JTable table;
    private JScrollPane jScrollPane;

    protected List<Prestamo> prestamos;
    protected Object[][] informacion;

    protected String[] columnas;

    public Reporte(Biblioteca biblioteca, String tituloTxt) {
        setLayout(new BorderLayout());

        titulo = new JLabel(tituloTxt);
        titulo.setFont(new Font("Arial", Font.PLAIN, 24));
        titulo.setBounds(20, 20, 100, 100);
        add(titulo, BorderLayout.NORTH);

        columnas = new String[]{"Fecha prestamo", "Estado", "Codigo del libro", "Carnet del estudiante"};
        prestamos = biblioteca.getPrestamos();
        table = new JTable();
        jScrollPane = new JScrollPane(table);
        add(jScrollPane, BorderLayout.CENTER);

    }



    public abstract Object[][] getInformacion();
}
