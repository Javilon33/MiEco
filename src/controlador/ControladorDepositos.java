
package controlador;

import java.text.NumberFormat;
import java.util.Locale;
import modelo.ConsultaDepositos;
import modelo.entidades.Usuario;
import vista.Paneles.PanelDepositos;

/**
 *
 * @author Francisco Javier Gómez Gamero
 */
public class ControladorDepositos {
    
    private final PanelDepositos vista; // La vista donde se muestran los depósitos
    private final ConsultaDepositos consultaInicio; // El modelo para obtener los datos de los depósitos
    private final Usuario usuario; // Usuario actual para obtener sus datos
    
    //Formato para mostrar los importes correctamente (2 decimales y puntos en los miles)
    NumberFormat formato = NumberFormat.getInstance(new Locale("es", "ES"));

    public ControladorDepositos(PanelDepositos vista, ConsultaDepositos consultaInicio, Usuario usuario) {
        this.vista = vista;
        this.consultaInicio = consultaInicio;
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
