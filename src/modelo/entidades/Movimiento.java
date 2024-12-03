package modelo.entidades;

import java.util.Date;

/**
 * Clase que representa un movimiento financiero.
 * Aquí se guardan los datos de cada operación, como su fecha, tipo, importe y notas.
 * 
 * @author Francisco Javier Gomez Gamero
 */
public class Movimiento {
    // Identificador único del movimiento
    private int id_movimiento;
    // ID de la cuenta a la que pertenece este movimiento
    private int idCuenta;
    // Fecha en la que se realizó el movimiento
    private Date fecha;
    // Tipo de movimiento (por ejemplo, ingreso o gasto)
    private int tipo;
    // Subtipo del movimiento (categorías más específicas)
    private int subtipo;
    // Tipo de gasto, si aplica (puede ser null para otros tipos de movimientos)
    private Integer tipoGasto;
    // Notas o comentarios sobre el movimiento
    private String notas;
    // Monto del movimiento (positivo o negativo dependiendo del tipo)
    private double importe;
    //Id del deposito asociado si procede
    private Integer idDeposito;

    /**
     * Constructor para crear un movimiento con todos sus datos.
     * 
     * @param id_movimiento ID único del movimiento.
     * @param idCuenta ID de la cuenta asociada.
     * @param fecha Fecha del movimiento.
     * @param tipo Tipo de movimiento (ingreso, gasto, etc.).
     * @param subtipo Subtipo del movimiento.
     * @param tipoGasto Tipo de gasto, si aplica (puede ser null).
     * @param notas Notas adicionales sobre el movimiento.
     * @param importe Importe del movimiento.
     * @param idDeposito Id del deposito asociado si procede
     */
    public Movimiento(int id_movimiento, int idCuenta, Date fecha, int tipo, int subtipo, Integer tipoGasto, String notas, double importe, Integer idDeposito) {
        this.id_movimiento = id_movimiento;
        this.idCuenta = idCuenta;
        this.fecha = fecha;
        this.tipo = tipo;
        this.subtipo = subtipo;
        this.tipoGasto = tipoGasto;
        this.notas = notas;
        this.importe = importe;
        this.idDeposito = idDeposito;
    }

    /**
     * Obtiene el ID del movimiento.
     * 
     * @return El ID del movimiento.
     */
    public int getId_movimiento() {
        return id_movimiento;
    }

    /**
     * Cambia el ID del movimiento.
     * 
     * @param id_movimiento Nuevo ID del movimiento.
     */
    public void setId_movimiento(int id_movimiento) {
        this.id_movimiento = id_movimiento;
    }

    /**
     * Obtiene el ID de la cuenta asociada al movimiento.
     * 
     * @return El ID de la cuenta.
     */
    public int getIdCuenta() {
        return idCuenta;
    }

    /**
     * Cambia el ID de la cuenta asociada al movimiento.
     * 
     * @param idCuenta Nuevo ID de la cuenta.
     */
    public void setIdCuenta(int idCuenta) {
        this.idCuenta = idCuenta;
    }

    /**
     * Obtiene la fecha del movimiento.
     * 
     * @return La fecha del movimiento.
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * Cambia la fecha del movimiento.
     * 
     * @param fecha Nueva fecha del movimiento.
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * Obtiene el tipo del movimiento.
     * 
     * @return El tipo del movimiento.
     */
    public int getTipo() {
        return tipo;
    }

    /**
     * Cambia el tipo del movimiento.
     * 
     * @param tipo Nuevo tipo del movimiento.
     */
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene el subtipo del movimiento.
     * 
     * @return El subtipo del movimiento.
     */
    public int getSubtipo() {
        return subtipo;
    }

    /**
     * Cambia el subtipo del movimiento.
     * 
     * @param subtipo Nuevo subtipo del movimiento.
     */
    public void setSubtipo(int subtipo) {
        this.subtipo = subtipo;
    }

    /**
     * Obtiene el tipo de gasto asociado, si aplica.
     * 
     * @return El tipo de gasto o null si no aplica.
     */
    public Integer getTipoGasto() {
        return tipoGasto;
    }

    /**
     * Cambia el tipo de gasto asociado.
     * 
     * @param tipoGasto Nuevo tipo de gasto (puede ser null).
     */
    public void setTipoGasto(Integer tipoGasto) {
        this.tipoGasto = tipoGasto;
    }

    /**
     * Obtiene las notas asociadas al movimiento.
     * 
     * @return Las notas del movimiento.
     */
    public String getNotas() {
        return notas;
    }

    /**
     * Cambia las notas asociadas al movimiento.
     * 
     * @param notas Nuevas notas para el movimiento.
     */
    public void setNotas(String notas) {
        this.notas = notas;
    }

    /**
     * Obtiene el importe del movimiento.
     * 
     * @return El importe del movimiento.
     */
    public double getImporte() {
        return importe;
    }

    /**
     * Cambia el importe del movimiento.
     * 
     * @param importe Nuevo importe del movimiento.
     */
    public void setImporte(double importe) {
        this.importe = importe;
    }

    /**
     * Obtiene el id del depósito asociado (si procede).
     * 
     * @return El id del depósito asociado (si procede).
     */
    public Integer getIdDeposito() {
        return idDeposito;
    }

    /**
     * Cambia el id del depósito asociado (si procede).
     * 
     * @param idDeposito Nuevo id del depósito asociado (si procede).
     */
    public void setIdDeposito(Integer idDeposito) {
        this.idDeposito = idDeposito;
    }
    
    
}
