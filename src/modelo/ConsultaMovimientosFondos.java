
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import modelo.entidades.MovimientoFondo;

/**
 *
 * @author Francisco Javier Gómez Gamero
 */
public class ConsultaMovimientosFondos {
    
    // Método para obtener los movimientos de una cuenta específica
    public List<MovimientoFondo> obtenerMovimientos(int idFondo) {
        List<MovimientoFondo> movimientos = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();

        String sql = "SELECT id_movimiento_fondo, tipo_movimiento, fecha, importe, participaciones, valor_liquidativo, notas FROM MOVIMIENTOS_FONDOS WHERE id_fondo = ? ORDER BY fecha ASC";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idFondo);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id_movimiento = rs.getInt("id_movimiento_fondo");
                String tipo = rs.getString("tipo_movimiento");
                Date fecha = rs.getDate("fecha");
                double importe = rs.getDouble("importe");
                double participaciones = rs.getDouble("participaciones");
                double valor = rs.getDouble("valor_liquidativo");
                String notas = rs.getString("notas");
                
                // Agregar el movimiento a la lista
                movimientos.add(new MovimientoFondo(id_movimiento, idFondo, 1, tipo, fecha, importe, participaciones, valor, notas));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener movimientos: " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }

        return movimientos;
    }
    
    // Método para AÑADIR MOVIMIENTO
    public boolean addMovimiento(int idFondo, int idCuenta, String tipo, String fecha, double importe, double participaciones, Double valor, String notas) {
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();

        String sql = "INSERT INTO MOVIMIENTOS_FONDOS (id_fondo, id_cuenta, tipo_movimiento, fecha, importe, participaciones, valor_liquidativo, notas) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idFondo);
            stmt.setInt(2, idCuenta);
            stmt.setString(3, tipo);
            stmt.setString(4, fecha);
            stmt.setDouble(5, importe);            
            stmt.setDouble(6, participaciones);
            stmt.setDouble(7, valor);
            stmt.setString(8, notas);
            

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al añadir el movimiento: " + e.getMessage());
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
    
    // Método para ELIMINAR MOVIMIENTO
    public boolean eliminarMovimiento(int idMovimiento) {
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();

        String sql = "DELETE FROM MOVIMIENTOS_FONDOS WHERE id_movimiento_fondo = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idMovimiento);
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar el movimiento: " + e.getMessage());
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
    
   
}
