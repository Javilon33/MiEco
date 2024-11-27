
package controlador;

import java.text.NumberFormat;
import java.util.Locale;
import modelo.ConsultaBolsa;
import modelo.entidades.Usuario;
import vista.Paneles.PanelBolsa;

/**
 *
 * @author Francisco Javier Gómez Gamero
 */
public class ControladorBolsa {
    
    private final PanelBolsa vista; // La vista donde se muestra el panel Bolsa (Acciones)
    private final ConsultaBolsa consultaBolsa; // El modelo para obtener los datos de las Acciones
    private final Usuario usuario; // Usuario actual para obtener sus datos    
    private final NumberFormat formato; //Formato para mostrar los importes correctamente (2 decimales y puntos en los miles)
    

    public ControladorBolsa(PanelBolsa vista, ConsultaBolsa consultaBolsa, Usuario usuario) {
        this.vista = vista;
        this.consultaBolsa = consultaBolsa;
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
