package modelo.entidades;

/**
 * Clase que representa un subtipo de movimiento.
 * Los subtipos o categorías sirven para categorizar los movimientos de forma más detallada.
 * Indican categorías del movimiento como Salario, Gasto, Cobro de alquiler, Traspaso...
 * 
 * @author Francisco Javier Gómez Gamero
 */
public class SubtipoMovimiento {
    
    private int idSubtipo;
    private int tipoMovimiento;
    private String descripcion;

    /**
     * Constructor para crear un subtipo de movimiento con sus datos.
     * 
     * @param idSubtipo ID único del subtipo.
     * @param tipoMovimiento ID del tipo de movimiento relacionado.
     * @param descripcion Descripción del subtipo.
     */
    public SubtipoMovimiento(int idSubtipo, int tipoMovimiento, String descripcion) {
        this.idSubtipo = idSubtipo;
        this.tipoMovimiento = tipoMovimiento;
        this.descripcion = descripcion;
    }

    /**
     * Obtiene el ID del subtipo de movimiento.
     * 
     * @return El ID del subtipo.
     */
    public int getIdSubtipo() {
        return idSubtipo;
    }

    /**
     * Cambia el ID del subtipo de movimiento.
     * 
     * @param idSubtipo Nuevo ID del subtipo.
     */
    public void setIdSubtipo(int idSubtipo) {
        this.idSubtipo = idSubtipo;
    }

    /**
     * Obtiene el ID del tipo de movimiento al que pertenece el subtipo.
     * 
     * @return El ID del tipo de movimiento.
     */
    public int getTipoMovimiento() {
        return tipoMovimiento;
    }

    /**
     * Cambia el ID del tipo de movimiento relacionado con este subtipo.
     * 
     * @param tipoMovimiento Nuevo ID del tipo de movimiento.
     */
    public void setTipoMovimiento(int tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    /**
     * Obtiene la descripción del subtipo de movimiento.
     * 
     * @return La descripción del subtipo.
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Cambia la descripción del subtipo de movimiento.
     * 
     * @param descripcion Nueva descripción para el subtipo.
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
