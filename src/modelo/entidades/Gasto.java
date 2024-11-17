
package modelo.entidades;

/**
 *
 * @author Francisco Javier Gómez gamero
 */
public class Gasto {
    
    private int idGasto;
    private String descripcion;
    private String tipoGasto;

    public Gasto(int idGasto, String descripcion, String tipoGasto) {
        this.idGasto = idGasto;
        this.descripcion = descripcion;
        this.tipoGasto = tipoGasto;
    }

    public int getIdGasto() {
        return idGasto;
    }

    public void setIdGasto(int idGasto) {
        this.idGasto = idGasto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipoGasto() {
        return tipoGasto;
    }

    public void setTipoGasto(String tipoGasto) {
        this.tipoGasto = tipoGasto;
    }
    
    
    
}
