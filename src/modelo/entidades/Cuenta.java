package modelo.entidades;

/**
 * Clase que representa una cuenta bancaria.
 * Aquí se guardan los datos básicos de una cuenta como el alias, IBAN, banco y saldo.
 * 
 * @author Francisco Javier Gómez Gamero
 */
public class Cuenta {
    // Identificador único de la cuenta
    private int idCuenta;
    // Nombre o apodo de la cuenta para reconocerla fácilmente
    private String alias;
    // Número IBAN de la cuenta
    private String iban;
    // Nombre del banco al que pertenece la cuenta
    private String banco;
    // Saldo actual de la cuenta
    private double saldo;

    /**
     * Constructor para crear una cuenta con todos sus datos.
     * 
     * @param idCuenta ID único de la cuenta.
     * @param alias Apodo o nombre de la cuenta.
     * @param iban Número IBAN de la cuenta.
     * @param banco Nombre del banco.
     * @param saldo Saldo inicial de la cuenta.
     */
    public Cuenta(int idCuenta, String alias, String iban, String banco, double saldo) {
        this.idCuenta = idCuenta;
        this.alias = alias;
        this.iban = iban;
        this.banco = banco;
        this.saldo = saldo;
    }

    /**
     * Obtiene el ID de la cuenta.
     * 
     * @return El ID de la cuenta.
     */
    public int getIdCuenta() {
        return idCuenta;
    }

    /**
     * Cambia el ID de la cuenta.
     * 
     * @param idCuenta Nuevo ID de la cuenta.
     */
    public void setIdCuenta(int idCuenta) {
        this.idCuenta = idCuenta;
    }

    /**
     * Obtiene el alias o nombre de la cuenta.
     * 
     * @return El alias de la cuenta.
     */
    public String getAlias() {
        return alias;
    }

    /**
     * Cambia el alias de la cuenta.
     * 
     * @param alias Nuevo alias de la cuenta.
     */
    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * Obtiene el banco al que pertenece la cuenta.
     * 
     * @return El nombre del banco.
     */
    public String getBanco() {
        return banco;
    }

    /**
     * Cambia el banco al que pertenece la cuenta.
     * 
     * @param banco Nuevo nombre del banco.
     */
    public void setBanco(String banco) {
        this.banco = banco;
    }

    /**
     * Obtiene el IBAN de la cuenta.
     * 
     * @return El IBAN de la cuenta.
     */
    public String getIban() {
        return iban;
    }

    /**
     * Cambia el IBAN de la cuenta.
     * 
     * @param iban Nuevo IBAN de la cuenta.
     */
    public void setIban(String iban) {
        this.iban = iban;
    }

    /**
     * Obtiene el saldo actual de la cuenta.
     * 
     * @return El saldo actual.
     */
    public double getSaldo() {
        return saldo;
    }

    /**
     * Cambia el saldo de la cuenta.
     * 
     * @param saldo Nuevo saldo de la cuenta.
     */
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
}
