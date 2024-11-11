package vista.componentes;

import controlador.ControladorCuentas;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Francisco Javier Gómez Gamero
 */
public class IconRendererEditor extends AbstractCellEditor implements TableCellRenderer, TableCellEditor {

    private final JLabel labelIcon;
    private int selectedRow;

    public IconRendererEditor(JTable table, ControladorCuentas controlador) {
        // Crea el JLabel con el icono deseado
        labelIcon = new JLabel(new ImageIcon(getClass().getResource("/img/detalles.png")));
        labelIcon.setHorizontalAlignment(SwingConstants.CENTER);

        // Agrega un listener para detectar clics en el icono
        labelIcon.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Llama al método para mostrar el panel de movimientos de la cuenta
                controlador.mostrarPanelMovimientos(selectedRow);
                fireEditingStopped();  // Finalizar la edición
            }
        });

        // Listener para actualizar la fila seleccionada
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                selectedRow = table.getSelectedRow();
            }
        });
    }

    // Renderizador: muestra el icono en la celda
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        return labelIcon;
    }

    // Editor: muestra el icono y permite la edición (clic)
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        selectedRow = row; // Actualiza la fila seleccionada
        return labelIcon;
    }

    @Override
    public Object getCellEditorValue() {
        return null;  // No se necesita devolver un valor
    }
}