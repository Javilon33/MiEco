package modelo;

/**
 *
 * @author Francisco Javier Gomez Gamero
 */
public class Movimiento {

    private String tipo;
    private int idCategoria;
    private Integer idSubcategoria;
    private String notas;
    private double importe;
    private String fecha;
    private double saldoResultante; // Esto podría ser calculado si es necesario

    // Constructor
    public Movimiento(String tipo, int idCategoria, Integer idSubcategoria, String notas, double importe, String fecha) {
        this.tipo = tipo;
        this.idCategoria = idCategoria;
        this.idSubcategoria = idSubcategoria;
        this.notas = notas;
        this.importe = importe;
        this.fecha = fecha;
        this.saldoResultante = 0.0; // Inicializamos el saldo resultante en 0, si no es calculado en la base de datos
    }

    // Getters y Setters
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public Integer getIdSubcategoria() {
        return idSubcategoria;
    }

    public void setIdSubcategoria(Integer idSubcategoria) {
        this.idSubcategoria = idSubcategoria;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public double getSaldoResultante() {
        return saldoResultante;
    }

    public void setSaldoResultante(double saldoResultante) {
        this.saldoResultante = saldoResultante;
    }

    
}
