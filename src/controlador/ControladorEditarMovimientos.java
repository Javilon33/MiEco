package controlador;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import modelo.ConsultaMovimientos;
import vista.PanelEditarMovimientos;

/**
 *
 * @author Francisco Javier Gómez Gamero
 */
public class ControladorEditarMovimientos {

    private final PanelEditarMovimientos vista;
    private final ConsultaMovimientos consultaMovimientos;
    private JDialog dialog;

    public ControladorEditarMovimientos(PanelEditarMovimientos vista, ConsultaMovimientos consultaMovimientos) {
        this.vista = vista;
        this.consultaMovimientos = consultaMovimientos;
        inicializarEventos();

    }
    
    public void setDialog(JDialog dialog) { this.dialog = dialog; }
    public void cerrarDialog() { if (dialog != null) { dialog.dispose(); } }
    
    private void inicializarEventos() {

        // Evento del botón CANCELAR
        vista.cancelarBtnTxt.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                vista.cancelarBtn.setBackground(new Color(0, 156, 223));
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                vista.cancelarBtn.setBackground(new Color(0, 134, 190));
            }

            @Override
            public void mouseClicked(MouseEvent evt) {
                int opcion = JOptionPane.showConfirmDialog(null, "¿Estás seguro que quieres cancelar? los datos no serán guardados", "Confirmación", JOptionPane.YES_NO_OPTION);
                if (opcion == JOptionPane.YES_OPTION) {
                    cerrarDialog();
                }
            }
        });
    }
}
