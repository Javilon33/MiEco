
package controlador;

import modelo.ConsultaCuentas;
import vista.PanelCuentas;

/**
 *
 * @author Francisco Javier Gómez Gamero
 */
public class ControladorCuentas {
    private final PanelCuentas vista; //Instancia de la vista PanelCuentas
    private final ConsultaCuentas consultaCuentas;
    
    
    public ControladorCuentas(PanelCuentas vista, ConsultaCuentas consultaCuentas) {
        this.vista = vista;
        this.consultaCuentas = consultaCuentas;        
        inicializarEventos();    
    }

    private void inicializarEventos() {
        
        vista.etiNombre.setText("Ya funciona!!!");
        
        
    }
    
    
    
}
