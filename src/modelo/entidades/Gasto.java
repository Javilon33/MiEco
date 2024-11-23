package modelo.entidades;

/**
 * Clase que representa un gasto.
 * Aquí se guardan los datos básicos de un gasto, como su descripción, tipo y total.
 * 
 * @author Francisco Javier Gómez Gamero
 */
public class Gasto {
    // Identificador único del gasto
    private int idGasto;
    // Breve descripción del gasto
    private String descripcion;
    // Tipo de gasto (por ejemplo, alimentación, transporte, etc.)
    private String tipoGasto;
    // Importe total del gasto
    private double total;

    /**
     * Constructor para crear un gasto con todos sus datos.
     * 
     * @param idGasto ID único del gasto.
     * @param descripcion Breve descripción del gasto.
     * @param tipoGasto Tipo del gasto (categoría).
     * @param total Importe total del gasto.
     */
    public Gasto(int idGasto, String descripcion, String tipoGasto, double total) {
        this.idGasto = idGasto;
        this.descripcion = descripcion;
        this.tipoGasto = tipoGasto;
        this.total = total;
    }

    /**
     * Obtiene el ID del gasto.
     * 
     * @return El ID del gasto.
     */
    public int getIdGasto() {
        return idGasto;
    }

    /**
     * Cambia el ID del gasto.
     * 
     * @param idGasto Nuevo ID del gasto.
     */
    public void setIdGasto(int idGasto) {
        this.idGasto = idGasto;
    }

    /**
     * Obtiene la descripción del gasto.
     * 
     * @return La descripción del gasto.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Cambia la descripción del gasto.
     * 
     * @param descripcion Nueva descripción del gasto.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el tipo de gasto (categoría).
     * 
     * @return El tipo de gasto.
     */
    public String getTipoGasto() {
        return tipoGasto;
    }

    /**
     * Cambia el tipo de gasto (categoría).
     * 
     * @param tipoGasto Nuevo tipo de gasto.
     */
    public void setTipoGasto(String tipoGasto) {
        this.tipoGasto = tipoGasto;
    }

    /**
     * Obtiene el Importe total del gasto.
     * 
     * @return El monto total del gasto.
     */
    public double getTotal() {
        return total;
    }

    /**
     * Cambia el Importe total del gasto.
     * 
     * @param total Nuevo monto total del gasto.
     */
    public void setTotal(double total) {
        this.total = total;
    }
}
