package modelo.entidades;

/**
 *
 * @author Francisco Javier Gómez gamero
 */
public class Gasto {
    private int idGasto;
    private String descripcion;
    private String tipoGasto;
    private double total;

    public Gasto(int idGasto, String descripcion, String tipoGasto, double total) {
        this.idGasto = idGasto;
        this.descripcion = descripcion;
        this.tipoGasto = tipoGasto;
        this.total = total;
    }

    // Getters y Setters
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

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}