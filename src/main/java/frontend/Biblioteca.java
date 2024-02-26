package frontend;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Biblioteca extends JFrame{
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

    public Biblioteca() {
        setContentPane(mainPanel);
        setTitle("Biblioteca CUNOC");
        setSize(804, 635);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        importar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: Abrir explorador de archivos para seleccionar .txt a leer

                JFileChooser chooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                        "Archivos de texto", "txt");
                chooser.setFileFilter(filter);
                int returnVal = chooser.showOpenDialog(getParent());
                if(returnVal == JFileChooser.APPROVE_OPTION) {
                    System.out.println("You chose to open this file: " +
                            chooser.getSelectedFile().getPath());
                }
            }
        });
    }
}
