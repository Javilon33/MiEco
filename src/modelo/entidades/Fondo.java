package modelo.entidades;

/**
 * Clase que representa un fondo de inversión. Aquí se guardan los datos básicos
 * de un fondo como el nombre, ISIN, tipo, moneda, nivel de riesgo y comisión.
 *
 * @author Francisco Javier Gómez Gamero
 */
public class Fondo {

    private int idFondo;
    private String nombre;
    private int tipoFondo;
    private String isin;
    private String moneda;
    private int riesgo;
    private double saldo;

    /**
     * Constructor para crear un fondo con todos sus datos.
     *
     * @param idFondo ID único del fondo.
     * @param nombre Nombre del fondo.
     * @param tipoFondo ID del tipo de fondo (referencia a TIPOS_FONDO).
     * @param isin Código ISIN único del fondo.
     * @param moneda Moneda en la que opera el fondo.
     * @param riesgo Nivel de riesgo del fondo (1-7).
     * @param saldo Saldo actualizado del fondo.
     */
    public Fondo(int idFondo, String nombre, int tipoFondo, String isin, String moneda, int riesgo, double saldo) {
        this.idFondo = idFondo;
        this.nombre = nombre;
        this.tipoFondo = tipoFondo;
        this.isin = isin;
        this.moneda = moneda;
        this.riesgo = riesgo;
        this.saldo = saldo;
    }

    // Métodos getters y setters
    public int getIdFondo() {
        return idFondo;
    }

    public void setIdFondo(int idFondo) {
        this.idFondo = idFondo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTipoFondo() {
        return tipoFondo;
    }

    public void setTipoFondo(int tipoFondo) {
        this.tipoFondo = tipoFondo;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public int getRiesgo() {
        return riesgo;
    }

    public void setRiesgo(int riesgo) {
        this.riesgo = riesgo;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
}
