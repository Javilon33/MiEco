
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
    private final NumberFormat formato; //Formato para mostrar los importes correctamente (2 decimales y puntos en los miles)     
      
    

    public ControladorAdmin(PanelAdmin vista, ConsultaAdmin consultaAdmin, Usuario usuario) {
        this.vista = vista;
        this.consultaAdmin = consultaAdmin;
        this.usuario = usuario;
        
        // Configurar el formato para importes (una sola vez)
        this.formato = NumberFormat.getInstance(new Locale("es", "ES"));
        formato.setMaximumFractionDigits(2);
        formato.setMinimumFractionDigits(2);
        
        inicializarEventos(); // Inicializar eventos de la interfaz
    }
    
    // Configura eventos básicos (clicks en botones y etiquetas)
    public void inicializarEventos() {
       
    } 
    
}
