
package login;

import controlador.ControladorPruebas;
import vista.VistaPruebas;

/**
 *
 * @author PC-Casa
 */
public class pruebas {
    
    
    public static void main(String[] args) {
        VistaPruebas vista = new VistaPruebas();
        ControladorPruebas controlador = new ControladorPruebas(vista);
        vista.setVisible(true);
    }
    
}
