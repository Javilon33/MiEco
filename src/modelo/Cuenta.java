
package modelo;

/**
 *
 * @author Francisco Javier Gómez Gamero
 */
public class Cuenta {
    private int idCuenta;
    private int idBanco;
    private String nombreBanco;  // Nuevo atributo para el nombre del banco
    private String alias;
    private String iban;
    private double saldo;

    // Constructor
    public Cuenta(int idCuenta, int idBanco, String nombreBanco, String alias, String iban, double saldo) {
        this.idCuenta = idCuenta;
        this.idBanco = idBanco;
        this.nombreBanco = nombreBanco;
        this.alias = alias;
        this.iban = iban;
        this.saldo = saldo;
    }

    // Getters
    public int getIdCuenta() { 
        return idCuenta; 
    }

    public int getIdBanco() { 
        return idBanco; 
    }

    public String getNombreBanco() { 
        return nombreBanco; 
    }  

    public String getAlias() { 
        return alias; 
    }

    public String getIban() { 
        return iban; 
    }

    public double getSaldo() { 
        return saldo; 
    }

    // Setter si necesitas establecer valores después de la creación
    public void setIdCuenta(int idCuenta) {
        this.idCuenta = idCuenta;
    }

    public void setIdBanco(int idBanco) {
        this.idBanco = idBanco;
    }

    public void setNombreBanco(String nombreBanco) {
        this.nombreBanco = nombreBanco;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
}
