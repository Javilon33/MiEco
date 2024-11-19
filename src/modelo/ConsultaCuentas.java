package modelo;

import modelo.entidades.Cuenta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import modelo.entidades.Gasto;

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

        String sql = "SELECT CUENTAS.id_cuenta,  CUENTAS.alias, CUENTAS.iban, CUENTAS.banco, CUENTAS.saldo "
                + "FROM CUENTAS "
                + "WHERE CUENTAS.id_usuario = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idCuenta = rs.getInt("id_cuenta");
                String alias = rs.getString("alias");
                String iban = rs.getString("iban");
                String banco = rs.getString("banco");
                double saldo = rs.getDouble("saldo");

                cuentas.add(new Cuenta(idCuenta, alias, iban, banco, saldo));
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

        String sql = "SELECT CUENTAS.id_cuenta, CUENTAS.alias, CUENTAS.iban, CUENTAS.banco, CUENTAS.saldo "
                + "FROM CUENTAS "
                + "WHERE CUENTAS.id_cuenta = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCuenta);  // Asigna el ID de la cuenta al parámetro de la consulta
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String alias = rs.getString("alias");
                String iban = rs.getString("iban");
                String nombreBanco = rs.getString("banco");
                double saldo = rs.getDouble("saldo");

                // Crea una instancia de Cuenta con los datos obtenidos
                cuenta = new Cuenta(idCuenta, alias, iban, nombreBanco, saldo);
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
    public boolean addCuenta(int usuarioId, String alias, String iban, String banco) {
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();

        String sql = "INSERT INTO CUENTAS (id_usuario, alias, iban, banco) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            stmt.setString(2, alias);
            stmt.setString(3, iban);
            stmt.setString(4, banco);
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
    public boolean modificarCuenta(int cuentaId, String alias, String iban, String banco, double saldo) {
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();

        String sql = "UPDATE CUENTAS SET  alias = ?, iban = ?, banco = ?, saldo = ? WHERE id_cuenta = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, alias);
            stmt.setString(2, iban);
            stmt.setString(3, banco);
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

    //Obtienr los ingresos totales de un usuario concreto
    public double obtenerSumaIngresos(int idUsuario) {
        double totalIngresos = 0.0;
        String sql = "SELECT SUM(m.importe) AS total_importe \n"
                + "      FROM mieco.movimientos m \n"
                + "      JOIN mieco.cuentas c ON m.id_cuenta = c.id_cuenta \n"
                + "      WHERE c.id_usuario = ? AND m.id_tipo_movimiento = 1";

        //Ejemplo de consulta de lo mismo pero de los últimos 30 días
        /*
        SELECT SUM(m.importe) AS total_importe 
        FROM mieco.movimientos m 
        JOIN mieco.cuentas c ON m.id_cuenta = c.id_cuenta 
        WHERE c.id_usuario = ? AND m.id_tipo_movimiento = 1 AND m.fecha >= CURDATE() - INTERVAL 30 DAY
         */
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    totalIngresos = rs.getDouble("total_importe");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalIngresos;
    }

    /*//Obtiene los gastos de un tipo concreto de un usuario concreto    
    public double obtenerSumaGastos(int idUsuario, int idTipoGasto) {
        double totalIngresos = 0.0;
        String sql = "SELECT SUM(m.importe) AS total_gasto"
                + "FROM MOVIMIENTOS m"
                + "JOIN CUENTAS c ON m.id_cuenta = c.id_cuenta"
                + "WHERE c.id_usuario = ? AND m.id_tipo_gasto = ?";

        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    totalIngresos = rs.getDouble("total_gasto");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalIngresos;
    }*/
    
    //Obtiene los gastos de un usuario concreto    
    public List<Gasto> obtenerTodosLosGastos(int idUsuario) {
        String sql = "SELECT tg.id_tipo_gasto, tg.descripcion, tg.tipo, SUM(m.importe) AS total_gasto "
                + "FROM MOVIMIENTOS m "
                + "JOIN CUENTAS c ON m.id_cuenta = c.id_cuenta "
                + "JOIN TIPOS_GASTO tg ON m.id_tipo_gasto = tg.id_tipo_gasto "
                + "WHERE c.id_usuario = ? "
                + "AND YEAR(m.fecha) = YEAR(CURDATE()) "
                + "GROUP BY tg.id_tipo_gasto, tg.descripcion, tg.tipo";

        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();
        List<Gasto> listaGastos = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int idGasto = rs.getInt("id_tipo_gasto");
                    String descripcion = rs.getString("descripcion");
                    String tipo = rs.getString("tipo");
                    double totalGasto = rs.getDouble("total_gasto");
                    totalGasto= -totalGasto; //Pasa el gasto a número positivo
                    if (rs.wasNull()) {
                        totalGasto = 0.0; // Manejo de valores nulos
                    }

                    // Crear un objeto Gasto y añadirlo a la lista
                    Gasto gasto = new Gasto(idGasto, descripcion, tipo, totalGasto);
                    listaGastos.add(gasto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return listaGastos;
    }

}
