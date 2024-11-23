package modelo.entidades;

/**
 * Clase que representa un tipo de movimiento.
 * Los tipos de movimiento permiten clasificar las transacciones en categorías principales.
 * Normalmente 2 tipos de movimientos (INGRESO - PAGO)
 * 
 * @author Francisco Javier Gómez Gamero
 */
public class TipoMovimiento {
    // Identificador único del tipo de movimiento
    private int idTipoMovimiento;
    // Descripción del tipo de movimiento (por ejemplo, "Ingreso", "Gasto")
    private String descripcion;

    /**
     * Constructor para crear un tipo de movimiento con sus datos.
     * 
     * @param idTipoMovimiento ID único del tipo de movimiento.
     * @param descripcion Descripción del tipo de movimiento.
     */
    public TipoMovimiento(int idTipoMovimiento, String descripcion) {
        this.idTipoMovimiento = idTipoMovimiento;
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el ID del tipo de movimiento.
     * 
     * @return El ID del tipo de movimiento.
     */
    public int getIdTipoMovimiento() {
        return idTipoMovimiento;
    }

    /**
     * Cambia el ID del tipo de movimiento.
     * 
     * @param idTipoMovimiento Nuevo ID del tipo de movimiento.
     */
    public void setIdTipoMovimiento(int idTipoMovimiento) {
        this.idTipoMovimiento = idTipoMovimiento;
    }

    /**
     * Obtiene la descripción del tipo de movimiento.
     * 
     * @return La descripción del tipo de movimiento.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Cambia la descripción del tipo de movimiento.
     * 
     * @param descripcion Nueva descripción para el tipo de movimiento.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
