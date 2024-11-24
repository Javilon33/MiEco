
package controlador;

import java.text.NumberFormat;
import java.util.Locale;
import modelo.ConsultaFondos;
import modelo.entidades.Usuario;
import vista.Paneles.PanelFondos;

/**
 *
 * @author Francisco Javier Gómez Gamero
 */
public class ControladorFondos {
    
    private final PanelFondos vista; // La vista donde se muestra el panel Fondos
    private final ConsultaFondos consultaFondos; // El modelo para obtener los datos de los Fondos de Inversion
    private final Usuario usuario; // Usuario actual para obtener sus datos
    
    //Formato para mostrar los importes correctamente (2 decimales y puntos en los miles)
    NumberFormat formato = NumberFormat.getInstance(new Locale("es", "ES"));

    public ControladorFondos(PanelFondos vista, ConsultaFondos consultaFondos, Usuario usuario) {
        this.vista = vista;
        this.consultaFondos = consultaFondos;
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
