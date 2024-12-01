package modelo.entidades;


import java.util.Calendar;
import java.util.Date;

/**
 * Clase que representa un depósito bancario asociado a un usuario. Contiene
 * información como el importe inicial, intereses, fechas, y el usuario al que
 * pertenece.
 *
 * @author Francisco Javier Gómez Gamero
 */
public class Deposito {

    // Identificador único del depósito
    private int idDeposito;
    // Identificador del usuario al que pertenece el depósito
    private int idUsuario;
    // Identificador de la cuenta donde está asociado el depósito
    private int idCuenta;
    // Nombre o alias del depósito
    private String nombre;    
    // Fecha de inicio del depósito    
    private Date fechaInicio;
    // Plazo de contratación (Meses)
    private int meses;    
    // Cantidad inicial invertida en el depósito
    private double importeInicial;
    // Porcentaje de interés anual del depósito
    private double interesAnual;    
    // Importe final esperado tras vencimiento y retenciones
    private double importeFinal;

    /**
     * Constructor para crear un depósito con todos sus datos.
     *
     * @param idDeposito ID único del depósito.
     * @param idUsuario ID del usuario asociado.
     * @param idCuenta ID de la cuenta
     * @param nombre Nombre o alias del depósito.
     * @param fechaInicio Fecha de inicio del depósito.
     * @param meses Plazo de duración del depósito.
     * @param importeInicial Cantidad inicial del depósito.
     * @param interesAnual Porcentaje de interés anual.     *
     * @param importeFinal Importe final esperado tras vencimiento.
     */
    public Deposito(int idDeposito, int idUsuario, int idCuenta, String nombre, Date fechaInicio, int meses, double importeInicial,
            double interesAnual, double importeFinal) {
        this.idDeposito = idDeposito;
        this.idUsuario = idUsuario;
        this.idCuenta = idCuenta;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.meses = meses;
        this.importeInicial = importeInicial;
        this.interesAnual = interesAnual;        
        this.importeFinal = importeFinal;
    }

    /**
     * Obtiene el ID del depósito.
     *
     * @return El ID del depósito.
     */
    public int getIdDeposito() {
        return idDeposito;
    }

    /**
     * Cambia el ID del depósito.
     *
     * @param idDeposito Nuevo ID del depósito.
     */
    public void setIdDeposito(int idDeposito) {
        this.idDeposito = idDeposito;
    }

    /**
     * Obtiene el ID del usuario asociado al depósito.
     *
     * @return El ID del usuario.
     */
    public int getIdUsuario() {
        return idUsuario;
    }

    /**
     * Cambia el ID del usuario asociado al depósito.
     *
     * @param idUsuario Nuevo ID del usuario.
     */
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public int getIdCuenta() {
        return idCuenta;
    }
    
    public void setCuenta(int idCuenta) {
        this.idCuenta = idCuenta;
    }

    /**
     * Obtiene el nombre o alias del depósito.
     *
     * @return El nombre del depósito.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Cambia el nombre o alias del depósito.
     *
     * @param nombre Nuevo nombre del depósito.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }    
    
    /**
     * Obtiene la fecha de inicio del depósito.
     *
     * @return La fecha de inicio.
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Cambia la fecha de inicio del depósito.
     *
     * @param fechaInicio Nueva fecha de inicio.
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * Obtiene el plazo de duración del depósito en meses.
     *
     * @return Los meses de duración.
     */
    public int getMeses() {
        return meses;
    }

    /**
     * Cambia la duración en meses del depósito.
     *
     * @param meses Nuevo plazo de duración del depósito en meses.
     */
    public void setMeses(int meses) {
        this.meses = meses;
    }  
    
    /**
     * Obtiene el importe inicial del depósito.
     *
     * @return El importe inicial.
     */
    public double getImporteInicial() {
        return importeInicial;
    }

    /**
     * Cambia el importe inicial del depósito.
     *
     * @param importeInicial Nuevo importe inicial.
     */
    public void setImporteInicial(double importeInicial) {
        this.importeInicial = importeInicial;
    }

    /**
     * Obtiene el porcentaje de interés anual del depósito.
     *
     * @return El porcentaje de interés anual.
     */
    public double getInteresAnual() {
        return interesAnual;
    }

    /**
     * Cambia el porcentaje de interés anual del depósito.
     *
     * @param interesAnual Nuevo porcentaje de interés anual.
     */
    public void setInteresAnual(double interesAnual) {
        this.interesAnual = interesAnual;
    }
    
    /**
     * Obtiene el importe final esperado tras vencimiento.
     *
     * @return El importe final esperado.
     */
    public double getImporteFinal() {
        return importeFinal;
    }

    /**
     * Cambia el importe final esperado tras vencimiento.
     *
     * @param importeFinal Nuevo importe final esperado.
     */
    public void setImporteFinal(double importeFinal) {
        this.importeFinal = importeFinal;
    }

    /**
     * Calcula la fecha de vencimiento del depósito según los meses.
     *
     * @return La fecha de vencimiento del depósito.
     */
    public Date getFechaVencimiento() {
        // Calcular la fecha final usando Calendar
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(fechaInicio); // Establecer la fecha de inicio
                    calendar.add(Calendar.MONTH, meses); // Añadir los meses
                    Date fechaFin = calendar.getTime(); // Obtener la fecha final
        return fechaFin;
    }
    
}
