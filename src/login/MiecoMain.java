
package login;

import controlador.ControladorLogin;
import vista.VistaLogin;
import vista.VistaPrincipal;
import vista.VistaRegistro;

/**
 *
 * @author Francisco Javier Gómez Gamero
 */
public class MiecoMain {
    
    public static void main(String Args[]){
        
        // Crear una instancia de la vista
        VistaLogin vistaLogin = new VistaLogin();
        VistaRegistro vistaRegistro = new VistaRegistro();
        
        // Pasar la vista al controlador para gestionar los eventos
        new ControladorLogin(vistaLogin);
        
        // Mostrar la vista de login
        vistaLogin.setVisible(true); 
        
    }
}
