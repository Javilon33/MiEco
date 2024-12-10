package modelo.entidades;

import java.util.Date;

/**
 * Clase que representa un movimiento relacionado con un fondo de inversión.
 * Aquí se guardan los datos de operaciones como compras, ventas o traspasos de
 * fondos.
 *
 * @author Francisco Javier Gómez gamero
 */
public class MovimientoFondo {

    private int idMovimientoFondo;
    private int idFondo;
    private int idCuenta;
    private String tipoMovimiento; // 'compra', 'venta' o 'traspaso'
    private Date fecha;
    private double importe;
    private double participaciones;
    private double valorLiquidativo;
    private String notas;

    /**
     * Constructor para crear un movimiento de fondo con todos sus datos.
     *
     * @param idMovimientoFondo ID único del movimiento.
     * @param idFondo ID del fondo relacionado con el movimiento.
     * @param idCuenta ID de la cuenta relacionada con el movimiento.
     * @param tipoMovimiento Tipo de movimiento ('compra', 'venta' o
     * 'traspaso').
     * @param fecha Fecha del movimiento.
     * @param importe Importe del movimiento.
     * @param participaciones Número de participaciones adquiridas o vendidas.
     * @param valorLiquidativo Valor liquidativo del fondo en el momento del
     * movimiento.
     * @param notas Notas adicionales sobre el movimiento.
     */
    public MovimientoFondo(int idMovimientoFondo, int idFondo, int idCuenta, String tipoMovimiento, Date fecha, double importe, double participaciones, double valorLiquidativo, String notas) {
        this.idMovimientoFondo = idMovimientoFondo;
        this.idFondo = idFondo;
        this.idCuenta = idCuenta;
        this.tipoMovimiento = tipoMovimiento;
        this.fecha = fecha;
        this.importe = importe;
        this.participaciones = participaciones;
        this.valorLiquidativo = valorLiquidativo;
        this.notas = notas;
    }

    // Métodos getters y setters
    public int getIdMovimientoFondo() {
        return idMovimientoFondo;
    }

    public void setIdMovimientoFondo(int idMovimientoFondo) {
        this.idMovimientoFondo = idMovimientoFondo;
    }

    public int getIdFondo() {
        return idFondo;
    }

    public void setIdFondo(int idFondo) {
        this.idFondo = idFondo;
    }

    public int getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(int idCuenta) {
        this.idCuenta = idCuenta;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public double getParticipaciones() {
        return participaciones;
    }

    public void setParticipaciones(double participaciones) {
        this.participaciones = participaciones;
    }

    public double getValorLiquidativo() {
        return valorLiquidativo;
    }

    public void setValorLiquidativo(double valorLiquidativo) {
        this.valorLiquidativo = valorLiquidativo;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }
}
