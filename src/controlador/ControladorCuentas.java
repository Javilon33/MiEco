
package controlador;

import java.util.List;
import javax.swing.table.DefaultTableModel;
import modelo.ConsultaCuentas;
import modelo.ConsultaCuentas.Cuenta;
import modelo.Usuario;
import vista.PanelCuentas;

/**
 *
 * @author Francisco Javier Gómez Gamero
 */
public class ControladorCuentas {
    private final PanelCuentas vista; //Instancia de la vista PanelCuentas
    private final ConsultaCuentas consultaCuentas; //Instancia del modelo ConsultaCuentas
    private final Usuario usuario; //Instancia del usuario para poder acceder a los datos
    
    
    public ControladorCuentas(PanelCuentas vista, ConsultaCuentas consultaCuentas, Usuario usuario) {
        this.vista = vista;
        this.consultaCuentas = consultaCuentas;  
        this.usuario = usuario;
        inicializarEventos();    
        cargarCuentas(); // Cargar datos al iniciar
    }

    private void inicializarEventos() {
        
        vista.etiNombre.setText("Hola, " + usuario.getNombre()+ "!!");
        vista.etiSaldoTotal.setText(consultaCuentas.obtenerSaldoTotal(usuario.getCodigo()) + "€");
        
    }
    
    // Método para cargar las cuentas del usuario en la tabla
    private void cargarCuentas() {
        List<Cuenta> cuentas = consultaCuentas.obtenerCuentas(usuario.getCodigo());
        
        // Obtener el modelo de la tabla y limpiar cualquier dato existente
        DefaultTableModel modelo = (DefaultTableModel) vista.tablaCuentas.getModel();
        modelo.setRowCount(0); // Limpiar las filas

        // Añadir cada cuenta a la tabla
        for (Cuenta cuenta : cuentas) {
            Object[] fila = { 
                cuenta.getBanco(),
                cuenta.getAlias(),
                cuenta.getIban(),
                cuenta.getSaldo()
            };
            modelo.addRow(fila);
        }
    }
    
    
    
}
