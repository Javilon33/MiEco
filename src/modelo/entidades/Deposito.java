package modelo.entidades;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Clase que representa un depósito bancario asociado a un usuario.
 * Contiene información como el importe inicial, intereses, fechas, 
 * y el usuario al que pertenece.
 * 
 * @author Francisco Javier Gómez Gamero
 */
public class Deposito {
    // Identificador único del depósito
    private int idDeposito;
    // Identificador del usuario al que pertenece el depósito
    private int idUsuario;
    // Nombre o alias del depósito
    private String nombre;
    // Cantidad inicial invertida en el depósito
    private BigDecimal importeInicial;
    // Porcentaje de interés anual del depósito
    private BigDecimal interesAnual;
    // Fecha de inicio del depósito
    private LocalDate fechaInicio;
    // Fecha de vencimiento del depósito
    private LocalDate fechaVencimiento;
    // Importe final esperado tras vencimiento y retenciones
    private BigDecimal importeFinal;

    /**
     * Constructor para crear un depósito con todos sus datos.
     * 
     * @param idDeposito ID único del depósito.
     * @param idUsuario ID del usuario asociado.
     * @param nombre Nombre o alias del depósito.
     * @param importeInicial Cantidad inicial del depósito.
     * @param interesAnual Porcentaje de interés anual.
     * @param fechaInicio Fecha de inicio del depósito.
     * @param fechaVencimiento Fecha de vencimiento del depósito.
     * @param importeFinal Importe final esperado tras vencimiento.
     */
    public Deposito(int idDeposito, int idUsuario, String nombre, BigDecimal importeInicial,
                    BigDecimal interesAnual, LocalDate fechaInicio, LocalDate fechaVencimiento, 
                    BigDecimal importeFinal) {
        this.idDeposito = idDeposito;
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.importeInicial = importeInicial;
        this.interesAnual = interesAnual;
        this.fechaInicio = fechaInicio;
        this.fechaVencimiento = fechaVencimiento;
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
     * Obtiene el importe inicial del depósito.
     * 
     * @return El importe inicial.
     */
    public BigDecimal getImporteInicial() {
        return importeInicial;
    }

    /**
     * Cambia el importe inicial del depósito.
     * 
     * @param importeInicial Nuevo importe inicial.
     */
    public void setImporteInicial(BigDecimal importeInicial) {
        this.importeInicial = importeInicial;
    }

    /**
     * Obtiene el porcentaje de interés anual del depósito.
     * 
     * @return El porcentaje de interés anual.
     */
    public BigDecimal getInteresAnual() {
        return interesAnual;
    }

    /**
     * Cambia el porcentaje de interés anual del depósito.
     * 
     * @param interesAnual Nuevo porcentaje de interés anual.
     */
    public void setInteresAnual(BigDecimal interesAnual) {
        this.interesAnual = interesAnual;
    }

    /**
     * Obtiene la fecha de inicio del depósito.
     * 
     * @return La fecha de inicio.
     */
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Cambia la fecha de inicio del depósito.
     * 
     * @param fechaInicio Nueva fecha de inicio.
     */
    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * Obtiene la fecha de vencimiento del depósito.
     * 
     * @return La fecha de vencimiento.
     */
    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    /**
     * Cambia la fecha de vencimiento del depósito.
     * 
     * @param fechaVencimiento Nueva fecha de vencimiento.
     */
    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    /**
     * Obtiene el importe final esperado tras vencimiento.
     * 
     * @return El importe final esperado.
     */
    public BigDecimal getImporteFinal() {
        return importeFinal;
    }

    /**
     * Cambia el importe final esperado tras vencimiento.
     * 
     * @param importeFinal Nuevo importe final esperado.
     */
    public void setImporteFinal(BigDecimal importeFinal) {
        this.importeFinal = importeFinal;
    }
}

