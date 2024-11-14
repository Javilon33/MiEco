
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Francisco javier Gómez Gamero
 */
public class ConsultaMovimientos {
    
    // Método para obtener los movimientos de una cuenta específica
    public List<Movimiento> obtenerMovimientos(int idCuenta) {
        List<Movimiento> movimientos = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();

        String sql = "SELECT tipo, id_categoria, id_subcategoria, notas, importe, fecha FROM MOVIMIENTOS WHERE id_cuenta = ? ORDER BY fecha ASC";

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
    public boolean addMovimiento(int idCuenta, String fecha, String tipo, int idCategoria, int idSubcategoria, String notas, double importe) {
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();

        String sql = "INSERT INTO MOVIMIENTOS (id_cuenta, fecha, tipo, id_categoria, id_subcategoria, notas, importe) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCuenta);
            stmt.setString(2, fecha);
            stmt.setString(3, tipo);
            stmt.setInt(4, idCategoria);
            stmt.setInt(5, idSubcategoria == -1 ? null : idSubcategoria); // Si no hay subcategoría, pasa null
            stmt.setString(6, notas);
            stmt.setDouble(7, importe);

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
    
    
}
