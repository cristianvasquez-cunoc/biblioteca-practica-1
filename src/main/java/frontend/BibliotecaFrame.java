package frontend;

import backend.Biblioteca;
import backend.Estudiante;
import backend.Libro;
import backend.Prestamo;
import backend.lectortxt.LectorTxt;
import backend.lectortxt.RegistroFallido;
import frontend.datos.DatosEstudiantePanel;
import frontend.datos.DatosLibroPanel;
import frontend.registros.RegistrarEstudiantePanel;
import frontend.registros.RegistrarLibroPanel;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class BibliotecaFrame extends JFrame {
    private JPanel mainPanel;
    private JMenu archivo;
    private JMenuItem importar;
    private JMenu registro;
    private JPanel body;
    private JMenuBar header;
    private JMenuItem registroLibro;
    private JMenuItem registroEstudiante;
    private JLabel bibliotecaImg;
    private JMenu datos;
    private JMenuItem datosLibro;
    private JMenuItem datosEstudiante;
    private JMenu prestamo;
    private JMenuItem realizar;
    private JMenuItem finalizar;
    private JMenu reportes;

    private Biblioteca biblioteca;

    public BibliotecaFrame() {
        setContentPane(mainPanel);
        setTitle("Biblioteca CUNOC");
        setSize(804, 635);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        biblioteca = new Biblioteca();
        LectorTxt lector = new LectorTxt(biblioteca);
        List<Estudiante> estudiantes = (List<Estudiante>) lector.cargarObjetos("estudiantes");
        List<Libro> libros = (List<Libro>) lector.cargarObjetos("libros");
        List<Prestamo> prestamos = (List<Prestamo>) lector.cargarObjetos("prestamos");
        biblioteca.setLibros(libros);
        biblioteca.setEstudiantes(estudiantes);
        biblioteca.setPrestamos(prestamos);

        importar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "Archivos de texto", "txt");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showOpenDialog(getParent());
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    String rutaArchivo = chooser.getSelectedFile().getPath();
                    try {
                        List<RegistroFallido> registrosFallidos = lector.leer(rutaArchivo);
                        RegistrosFallidosFrame registrosFallidosFrame = new RegistrosFallidosFrame(registrosFallidos);
                        if (registrosFallidos.size() == 0)
                            JOptionPane.showMessageDialog(mainPanel, "Los datos se han agregado exitosamente", "Datos agregados", JOptionPane.INFORMATION_MESSAGE);
                        else {
                            JOptionPane.showMessageDialog(mainPanel, "Algunos datos no se han podido ingresar", "Datos agregados", JOptionPane.WARNING_MESSAGE);
                            registrosFallidosFrame.mostrarRegistrosFallidosFrame(mainPanel);
                        }
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        datosLibro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatosLibroPanel pane = new DatosLibroPanel(libros);
                cambiarMainPanel(pane);
            }
        });

        datosEstudiante.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatosEstudiantePanel pane = new DatosEstudiantePanel(estudiantes);
                cambiarMainPanel(pane);
            }
        });

        registroEstudiante.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegistrarEstudiantePanel pane = new RegistrarEstudiantePanel(biblioteca, lector);
                cambiarMainPanel(pane);
            }
        });

        registroLibro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegistrarLibroPanel pane = new RegistrarLibroPanel(biblioteca, lector);
                cambiarMainPanel(pane);
            }
        });
    }

    private void cambiarMainPanel (JPanel pane){
        mainPanel.remove(1);
        mainPanel.add(pane);

        revalidate();
        repaint();
    }
}
