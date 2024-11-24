
package controlador;

import java.text.NumberFormat;
import java.util.Locale;
import modelo.ConsultaAdmin;
import modelo.entidades.Usuario;
import vista.Paneles.PanelAdmin;

/**
 *
 * @author Francisco Javier Gómez Gamero
 */
public class ControladorAdmin {
    
    private final PanelAdmin vista; // La vista donde se muestra el panel Informes
    private final ConsultaAdmin consultaAdmin; // El modelo para obtener los datos para los Informes
    private final Usuario usuario; // Usuario actual para obtener sus datos
    
    //Formato para mostrar los importes correctamente (2 decimales y puntos en los miles)
    NumberFormat formato = NumberFormat.getInstance(new Locale("es", "ES"));

    public ControladorAdmin(PanelAdmin vista, ConsultaAdmin consultaAdmin, Usuario usuario) {
        this.vista = vista;
        this.consultaAdmin = consultaAdmin;
        this.usuario = usuario;
        inicializarEventos(); // Inicializar eventos de la interfaz
    }
    
    // Configura eventos básicos (clicks en botones y etiquetas)
    public void inicializarEventos() {
        
        
        //Formato de los importes 
        formato.setMaximumFractionDigits(2);
        formato.setMinimumFractionDigits(2);
    } 
    
}
