
package modelo.entidades;

/**
 *
 * @author Francisco Javier Gómez Gamero
 */
public class Cuenta {
    private int idCuenta;
    private String alias;
    private String banco;     
    private String iban;
    private double saldo;

    public Cuenta(int idCuenta, String alias, String banco, String iban, double saldo) {
        this.idCuenta = idCuenta;
        this.alias = alias;
        this.banco = banco;
        this.iban = iban;
        this.saldo = saldo;
    }

    public int getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(int idCuenta) {
        this.idCuenta = idCuenta;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

}