
package modelo.entidades;

/**
 *
 * @author Francisco Javier Gómez gamero
 */
public class SubtipoMovimiento {
    
    private int idSubtipo;
    private int tipoMovimiento;
    private String descripcion;

    public SubtipoMovimiento(int idSubtipo, int tipoMovimiento, String descripcion) {
        this.idSubtipo = idSubtipo;
        this.tipoMovimiento = tipoMovimiento;
        this.descripcion = descripcion;
    }

    public int getIdSubtipo() {
        return idSubtipo;
    }

    public void setIdSubtipo(int idSubtipo) {
        this.idSubtipo = idSubtipo;
    }

    public int getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(int tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    
}
