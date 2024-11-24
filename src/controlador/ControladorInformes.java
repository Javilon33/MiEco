
package controlador;

import java.text.NumberFormat;
import java.util.Locale;
import modelo.ConsultaInformes;
import modelo.entidades.Usuario;
import vista.Paneles.PanelInformes;

/**
 *
 * @author Francisco Javier Gómez Gamero
 */
public class ControladorInformes {
    
    private final PanelInformes vista; // La vista donde se muestra el panel Informes
    private final ConsultaInformes consultaInformes; // El modelo para obtener los datos para los Informes
    private final Usuario usuario; // Usuario actual para obtener sus datos
    
    //Formato para mostrar los importes correctamente (2 decimales y puntos en los miles)
    NumberFormat formato = NumberFormat.getInstance(new Locale("es", "ES"));

    public ControladorInformes(PanelInformes vista, ConsultaInformes consultaInformes, Usuario usuario) {
        this.vista = vista;
        this.consultaInformes = consultaInformes;
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
