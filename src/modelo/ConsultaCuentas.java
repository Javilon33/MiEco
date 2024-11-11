package modelo;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;

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
    
    String sql = "SELECT CUENTAS.id_cuenta, CUENTAS.id_banco, BANCOS.nombre_banco, CUENTAS.alias, CUENTAS.iban, CUENTAS.saldo "
               + "FROM CUENTAS "
               + "JOIN BANCOS ON CUENTAS.id_banco = BANCOS.id_banco "
               + "WHERE CUENTAS.id_usuario = ?";
    
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, usuarioId);
        ResultSet rs = stmt.executeQuery();
        
        while (rs.next()) {
            int idCuenta = rs.getInt("id_cuenta");
            int idBanco = rs.getInt("id_banco");
            String nombreBanco = rs.getString("nombre_banco");  // Obtener nombre del banco
            String alias = rs.getString("alias");
            String iban = rs.getString("iban");
            double saldo = rs.getDouble("saldo");
            
            cuentas.add(new Cuenta(idCuenta, idBanco, nombreBanco, alias, iban, saldo));
        }
    } catch (SQLException e) {
        System.err.println("Error al obtener cuentas: " + e.getMessage());
    } finally {
        try {
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
    
    return cuentas;
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
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        
        return saldoTotal;
    }

    // Método para obtener los movimientos de una cuenta específica
    public List<Movimiento> obtenerMovimientos(int idCuenta) {
        List<Movimiento> movimientos = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();
        
        String sql = "SELECT tipo, id_categoria, id_subcategoria, notas, importe, fecha FROM MOVIMIENTOS WHERE id_cuenta = ? ORDER BY fecha DESC";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCuenta);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                String tipo = rs.getString("tipo");
                int idCategoria = rs.getInt("id_categoria");
                Integer idSubcategoria = (rs.getObject("id_subcategoria") != null) ? rs.getInt("id_subcategoria") : null;
                String notas = rs.getString("notas");
                double importe = rs.getDouble("importe");
                String fecha = rs.getString("fecha");
                
                movimientos.add(new Movimiento(tipo, idCategoria, idSubcategoria, notas, importe, fecha));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener movimientos: " + e.getMessage());
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        
        return movimientos;
    }
    
    // Método para AÑADIR CUENTA
    public boolean añadirCuenta(int usuarioId, int idBanco, String alias, String iban, double saldo) {
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();
        
        String sql = "INSERT INTO CUENTAS (id_usuario, id_banco, alias, iban, saldo) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            stmt.setInt(2, idBanco);
            stmt.setString(3, alias);
            stmt.setString(4, iban);
            stmt.setDouble(5, saldo);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al añadir cuenta: " + e.getMessage());
            return false;
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }

    // Método para MODFICAR CUENTA
    public boolean modificarCuenta(int cuentaId, int idBanco, String alias, String iban, double saldo) {
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();
        
        String sql = "UPDATE CUENTAS SET id_banco = ?, alias = ?, iban = ?, saldo = ? WHERE id_cuenta = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idBanco);
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
                if (conn != null) conn.close();
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
            if (conn != null) conn.close();
        } catch (SQLException e) {
            System.err.println("Error al cerrar la conexión: " + e.getMessage());
        }
    }
}
    
    // Método para obtener una lista de bancos con sus IDs
    public Map<Integer, String> obtenerListaBancos() {
        Map<Integer, String> bancos = new HashMap<>();
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();
        
        String sql = "SELECT id_banco, nombre_banco FROM BANCOS";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
             
            while (rs.next()) {
                int idBanco = rs.getInt("id_banco");
                String nombreBanco = rs.getString("nombre_banco");
                bancos.put(idBanco, nombreBanco);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener lista de bancos: " + e.getMessage());
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        
        return bancos;
    }
    
    // Método para obtener el nombre de una cuenta específica
    public String obtenerNombreCuenta(int idCuenta) {
        String nombreCuenta = "";
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();

        String sql = "SELECT alias FROM CUENTAS WHERE id_cuenta = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCuenta);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                nombreCuenta = rs.getString("alias");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el nombre de la cuenta: " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }

        return nombreCuenta;
    }
    
    // Método para obtener el nombre del banco a partir del idCuenta
    public String obtenerNombreBanco(int idCuenta) {
        String nombreBanco = "";
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();
        
        String sql = "SELECT b.nombre_banco " +
                     "FROM CUENTAS c " +
                     "JOIN BANCOS b ON c.id_banco = b.id_banco " +
                     "WHERE c.id_cuenta = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCuenta);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    nombreBanco = resultSet.getString("nombre_banco");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al obtener el nombre del banco: " + e.getMessage());
        }

        return nombreBanco;
    }
}
