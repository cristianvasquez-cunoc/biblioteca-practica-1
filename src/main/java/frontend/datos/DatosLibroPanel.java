package frontend.datos;

import backend.Estudiante;
import backend.Libro;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

public class DatosLibroPanel extends JPanel {
    private JLabel titulo;
    private JLabel filtrarTexto;
    private JTextField filtrarTextField;
    private Button filtrarBoton;
    private JComboBox filtrarCombobox;
    private JList<String> listaLibrosJList;
    private List<Libro> libros;


    public DatosLibroPanel(List<Libro> libros) {
        this.libros = libros;
        construirPanel();
    }

    private void construirPanel() {
        setLayout(new BorderLayout());

        titulo = new JLabel("Listado de Libros");
        titulo.setFont(getFuenteConTamano(24));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 0));

        setLayout(new BorderLayout());
        add(titulo, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout());
        add(center, BorderLayout.CENTER);

        JPanel filtrarPanel = new JPanel(new FlowLayout());
        center.add(filtrarPanel, BorderLayout.NORTH);

        filtrarTexto = new JLabel("Filtrar por: ");
        String[] opcionesFiltrar = new String[]{"Codigo", "Autor", "Titulo", "Todos"};
        filtrarCombobox = new JComboBox(opcionesFiltrar);
        filtrarCombobox.setSelectedIndex(3);
        filtrarTextField = new JTextField();
        filtrarTextField.setPreferredSize(new Dimension(300, 32));
        filtrarBoton = new Button("Filtrar");

        filtrarPanel.add(filtrarTexto);
        filtrarPanel.add(filtrarCombobox);
        filtrarPanel.add(filtrarTextField);
        filtrarPanel.add(filtrarBoton);

        listaLibrosJList = new JList<>();
        listaLibrosJList.setVisibleRowCount(15);
        JScrollPane scrollPane = new JScrollPane(listaLibrosJList);
        JPanel listaPanel = new JPanel();
        scrollPane.setPreferredSize(new Dimension(780, 400));
        listaPanel.setPreferredSize(new Dimension(780, 400));
        listaPanel.add(scrollPane);

        center.add(listaPanel, BorderLayout.CENTER);

        filtrarLibros();

        filtrarBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filtrarLibros();
            }
        });

    }

    private void filtrarLibros() {

        int i = filtrarCombobox.getSelectedIndex();
        String textoAfiltrar = filtrarTextField.getText();

        List<Libro> libros = new LinkedList<>();

        if (i == 0)
            libros.addAll(this.libros.stream().filter(libro -> libro.getIdentificador().contains(textoAfiltrar)).toList());
        if (i == 1)
            libros.addAll(this.libros.stream().filter(libro -> libro.getAutor().contains(textoAfiltrar)).toList());
        if (i == 2)
            libros.addAll(this.libros.stream().filter(libro -> libro.getTitulo().contains(textoAfiltrar)).toList());
        if (i == 3)
            libros.addAll(this.libros);

        mostrarLibrosSegunFiltro(libros);
    }

    private void mostrarLibrosSegunFiltro(List<Libro> libros) {
        listaLibrosJList.removeAll();
        listaLibrosJList.setListData(libros.stream()
                .map(libro -> "codigo: " + libro.getIdentificador() + ", autor: " + libro.getAutor() + ", titulo: " + libro.getTitulo() + ", copias: " + libro.getCopias())
                .toArray(String[]::new));
        listaLibrosJList.revalidate();
        listaLibrosJList.repaint();

    }


    private Font getFuenteConTamano(int tamano) {
        return new Font("Arial", Font.PLAIN, tamano);
    }

}