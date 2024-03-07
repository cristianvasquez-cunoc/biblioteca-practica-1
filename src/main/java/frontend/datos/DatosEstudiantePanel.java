package frontend.datos;

import backend.Estudiante;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

public class DatosEstudiantePanel extends JPanel {
    private JLabel titulo;
    private JLabel filtrarTexto;
    private JTextField filtrarTextField;
    private Button filtrarBoton;
    private JComboBox filtrarCombobox;
    private JList<String> listaEstudiantesJList;
    private List<Estudiante> estudiantes;


    public DatosEstudiantePanel(List<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
        construirPanel();
    }

    private void construirPanel() {
        setLayout(new BorderLayout());

        titulo = new JLabel("Listado de Estudiantes");
        titulo.setFont(getFuenteConTamano(24));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 0));

        setLayout(new BorderLayout());
        add(titulo, BorderLayout.NORTH);

        JPanel center = new JPanel(new BorderLayout());
        add(center, BorderLayout.CENTER);

        JPanel filtrarPanel = new JPanel(new FlowLayout());
        center.add(filtrarPanel, BorderLayout.NORTH);

        filtrarTexto = new JLabel("Filtrar por: ");
        String[] opcionesFiltrar = new String[]{"Carnet", "Nombre", "Codigo carrera", "Todos"};
        filtrarCombobox = new JComboBox(opcionesFiltrar);
        filtrarCombobox.setSelectedIndex(3);
        filtrarTextField = new JTextField();
        filtrarTextField.setPreferredSize(new Dimension(300, 32));
        filtrarBoton = new Button("Filtrar");

        filtrarPanel.add(filtrarTexto);
        filtrarPanel.add(filtrarCombobox);
        filtrarPanel.add(filtrarTextField);
        filtrarPanel.add(filtrarBoton);


        listaEstudiantesJList = new JList<>();
        listaEstudiantesJList.setVisibleRowCount(15);
        JScrollPane scrollPane = new JScrollPane(listaEstudiantesJList);
        JPanel listaPanel = new JPanel();
        scrollPane.setPreferredSize(new Dimension(700, 400));
        listaPanel.setPreferredSize(new Dimension(700, 400));
        listaPanel.add(scrollPane);

        center.add(listaPanel, BorderLayout.CENTER);

        filtrarEstudiantes();

        filtrarBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    filtrarEstudiantes();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "El codigo de carrera debe ser un numero", "Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

    }

    private void filtrarEstudiantes() {

        int i = filtrarCombobox.getSelectedIndex();
        String textoAfiltrar = filtrarTextField.getText();

        List<Estudiante> estudiantes = new LinkedList<>();

        if (i == 0)
            estudiantes.addAll(this.estudiantes.stream().filter(estudiante -> estudiante.getIdentificador().contains(textoAfiltrar)).toList());
        if (i == 1)
            estudiantes.addAll(this.estudiantes.stream().filter(estudiante -> estudiante.getNombre().contains(textoAfiltrar)).toList());
        if (i == 2)
            estudiantes.addAll(this.estudiantes.stream().filter(estudiante -> estudiante.getCarrera().getCodigo() == Integer.parseInt(textoAfiltrar)).toList());
        if (i == 3)
            estudiantes.addAll(this.estudiantes);

        mostrarEstudiantesSegunFiltro(estudiantes);
    }

    private void mostrarEstudiantesSegunFiltro(List<Estudiante> estudiantes) {
        listaEstudiantesJList.removeAll();
        listaEstudiantesJList.setListData(estudiantes.stream()
                .map(estudiante -> "carnet: " + estudiante.getIdentificador() + ", nombre: " + estudiante.getNombre() + ", carrera: " + estudiante.getCarrera() + ", prestamos activos: " + estudiante.getNoPrestamosActivos())
                .toArray(String[]::new));
        listaEstudiantesJList.revalidate();
        listaEstudiantesJList.repaint();

    }


    private Font getFuenteConTamano(int tamano) {
        return new Font("Arial", Font.PLAIN, tamano);
    }

}
