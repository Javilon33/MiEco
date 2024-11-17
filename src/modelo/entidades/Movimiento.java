package modelo.entidades;

import java.util.Date;

/**
 *
 * @author Francisco Javier Gomez Gamero
 */
public class Movimiento {

    private int id_movimiento;
    private int idCuenta;
    private Date fecha;
    private int tipo;
    private int subtipo;
    private Integer tipoGasto;
    private String notas;
    private double importe;

    // Constructor
    public Movimiento(int id_movimiento, int idCuenta, Date fecha, int tipo, int subtipo, Integer tipoGasto, String notas, double importe) {
        this.id_movimiento = id_movimiento;
        this.idCuenta = idCuenta;
        this.fecha = fecha;
        this.tipo = tipo;
        this.subtipo = subtipo;
        this.tipoGasto = tipoGasto;
        this.notas = notas;
        this.importe = importe;
    }

    //Getters y Setters
    public int getId_movimiento() {
        return id_movimiento;
    }

    public void setId_movimiento(int id_movimiento) {
        this.id_movimiento = id_movimiento;
    }

    public int getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(int idCuenta) {
        this.idCuenta = idCuenta;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getSubtipo() {
        return subtipo;
    }

    public void setSubtipo(int subtipo) {
        this.subtipo = subtipo;
    }

    public Integer getTipoGasto() {
        return tipoGasto;
    }

    public void setTipoGasto(Integer tipoGasto) {
        this.tipoGasto = tipoGasto;
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
    
    
}