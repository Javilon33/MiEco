package modelo;

import modelo.entidades.Cuenta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Francisco Javier Gómez Gamero
 */
public class ConsultaCuentas {

    // Método para obtener todas las cuentas de un usuario específico
    public List<Cuenta> obtenerCuentas(int usuarioId) {
        List<Cuenta> cuentas = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();

        String sql = "SELECT CUENTAS.id_cuenta,  CUENTAS.alias, CUENTAS.banco, CUENTAS.iban, CUENTAS.saldo "
                + "FROM CUENTAS "
                + "WHERE CUENTAS.id_usuario = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idCuenta = rs.getInt("id_cuenta"); 
                String alias = rs.getString("alias");
                String banco = rs.getString("banco");                 
                String iban = rs.getString("iban");
                double saldo = rs.getDouble("saldo");

                cuentas.add(new Cuenta(idCuenta, alias, banco, iban, saldo));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener cuentas: " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        return cuentas;
    }

    //Método par aobtener la cuenta por el idCuenta
    public Cuenta obtenerCuentaPorId(int idCuenta) {
        Cuenta cuenta = null;
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();

        String sql = "SELECT CUENTAS.id_cuenta, CUENTAS.banco, CUENTAS.alias, CUENTAS.iban, CUENTAS.saldo "
                + "FROM CUENTAS "
                + "WHERE CUENTAS.id_cuenta = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCuenta);  // Asigna el ID de la cuenta al parámetro de la consulta
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {                
                String nombreBanco = rs.getString("banco");
                String alias = rs.getString("alias");
                String iban = rs.getString("iban");
                double saldo = rs.getDouble("saldo");

                // Crea una instancia de Cuenta con los datos obtenidos
                cuenta = new Cuenta(idCuenta, nombreBanco, alias, iban, saldo);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener la cuenta: " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        return cuenta; // Devuelve la cuenta obtenida o null si no existe
    }

    // Método para obtener el saldo total de todas las cuentas de un usuario
    public double obtenerSaldoTotal(int usuarioId) {
        double saldoTotal = 0.0;
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();

        String sql = "SELECT SUM(saldo) AS saldo_total FROM CUENTAS WHERE id_usuario = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                saldoTotal = rs.getDouble("saldo_total");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el saldo total: " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        return saldoTotal;
    }

    // Método para AÑADIR CUENTA
    public boolean addCuenta(int usuarioId, String alias,String banco, String iban) {
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();

        String sql = "INSERT INTO CUENTAS (id_usuario, alias, banco, iban) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            stmt.setString(2, alias);
            stmt.setString(3, banco);
            stmt.setString(4, iban);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al añadir cuenta: " + e.getMessage());
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }

    // Método para MODFICAR CUENTA
    public boolean modificarCuenta(int cuentaId, String banco, String alias, String iban, double saldo) {
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();

        String sql = "UPDATE CUENTAS SET banco = ?, alias = ?, iban = ?, saldo = ? WHERE id_cuenta = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, banco);
            stmt.setString(2, alias);
            stmt.setString(3, iban);
            stmt.setDouble(4, saldo);
            stmt.setInt(5, cuentaId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al modificar cuenta: " + e.getMessage());
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }

    //Método para ELIMINAR CUENTA
    public boolean eliminarCuenta(int idCuenta) {
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();

        String sql = "DELETE FROM CUENTAS WHERE id_cuenta = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCuenta);
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar la cuenta: " + e.getMessage());
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
    
    
    // Método para actualizar el saldo de la cuenta
    public boolean actualizarSaldoCuenta(int idCuenta, double nuevoSaldo) {
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();

        String sql = "UPDATE CUENTAS SET saldo = ? WHERE id_cuenta = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, nuevoSaldo);
            stmt.setInt(2, idCuenta);
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0; // Retorna true si se actualizó correctamente
        } catch (SQLException e) {
            System.err.println("Error al actualizar el saldo de la cuenta: " + e.getMessage());
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }

    public double obtenerSaldoInicial(int idCuenta) {
        double saldoInicial = 0.0;
        String sql = "SELECT saldo_inicial FROM CUENTAS WHERE id_cuenta = ?";
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCuenta);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    saldoInicial = rs.getDouble("saldo_inicial");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Maneja la excepción según tu necesidad, quizás lanzándola o registrándola en un log
        }

        return saldoInicial;
    }

}
