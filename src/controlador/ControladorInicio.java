
package controlador;

import java.text.NumberFormat;
import java.util.Locale;
import modelo.ConsultaInicio;
import modelo.entidades.Usuario;
import vista.Paneles.PanelInicio;

/**
 *
 * @author Francisco Javier Gómez Gamero
 */
public class ControladorInicio {
    
    private final PanelInicio vista; // La vista donde se muestra el panel Inicio
    private final ConsultaInicio consultaInicio; // El modelo para obtener los datos de Inicio
    private final Usuario usuario; // Usuario actual para obtener sus datos
    
    //Formato para mostrar los importes correctamente (2 decimales y puntos en los miles)
    NumberFormat formato = NumberFormat.getInstance(new Locale("es", "ES"));

    public ControladorInicio(PanelInicio vista, ConsultaInicio consultaInicio, Usuario usuario) {
        this.vista = vista;
        this.consultaInicio = consultaInicio;
        this.usuario = usuario;
        inicializarEventos(); // Inicializar eventos de la interfaz
    }
    
    // Configura eventos básicos (clicks en botones y etiquetas)
    public void inicializarEventos() {
        // Saludo con el nombre del usuario
        vista.etiNombre.setText("Hola, " + usuario.getNombre() + "!!");
        
        //Formato de los importes 
        formato.setMaximumFractionDigits(2);
        formato.setMinimumFractionDigits(2);
    } 
        
}
