package frontend;

import backend.lectortxt.RegistroFallido;

import javax.swing.*;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.List;

public class RegistrosFallidosFrame extends JFrame{
    private JScrollPane jScrollPane;
    private JTable jTable;
    private String[] columnas;
    private Object[][] informacion;
    private List<RegistroFallido> registrosFallidos;

    public RegistrosFallidosFrame(List<RegistroFallido> registrosFallidos) {
        super("Registros Fallidos");
        this.columnas = new String[]{"Texto", "Motivo", "Tipo"};
        this.registrosFallidos = registrosFallidos;
        this.informacion = getInformacion();
        DefaultTableModel model = new DefaultTableModel(informacion, columnas);
        this.jTable = new JTable(model);
        TableColumnModel tableColumnModel = jTable.getColumnModel();
        tableColumnModel.getColumn(0).setPreferredWidth(150);
        tableColumnModel.getColumn(1).setPreferredWidth(450);
        tableColumnModel.getColumn(2).setPreferredWidth(140);
        this.jScrollPane = new JScrollPane(jTable);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(jScrollPane, BorderLayout.CENTER);
        setPreferredSize(new Dimension(750, 500));
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

    }

    public void mostrarRegistrosFallidosFrame(JPanel parent) {
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    private Object[][] getInformacion() {

        Object[][] informacion = new Object[registrosFallidos.size()][columnas.length];
        for (int i = 0; i < registrosFallidos.size(); i++) {
            RegistroFallido rf = registrosFallidos.get(i);
            informacion[i][0] = rf.getTexto();
            informacion[i][1] = rf.getError();
            informacion[i][2] = rf.getTipo();
        }
        return informacion;

    }
}

