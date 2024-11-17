package modelo;

import modelo.entidades.Movimiento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        String sql = "SELECT id_movimiento, id_cuenta, id_tipo_movimiento, id_subtipo_movimiento, id_tipo_gasto, notas, importe, fecha FROM MOVIMIENTOS WHERE id_cuenta = ? ORDER BY fecha ASC";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCuenta);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id_movimiento = rs.getInt("id_movimiento");
                int id_cuenta = rs.getInt("id_cuenta");
                int id_tipo_movimiento = rs.getInt("id_tipo_movimiento");
                int id_subtipo_movimiento = rs.getInt("id_subtipo_movimiento");
                Integer id_tipo_gasto = (rs.getObject("id_tipo_gasto") != null) ? rs.getInt("id_tipo_gasto") : null;
                String notas = rs.getString("notas");
                double importe = rs.getDouble("importe");
                Date fecha = rs.getDate("fecha");

                movimientos.add(new Movimiento(id_movimiento, idCuenta, fecha, id_tipo_movimiento, id_subtipo_movimiento, id_tipo_gasto, notas, importe));
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
    public boolean addMovimiento(int idCuenta, String fecha, int tipo, int idCategoria, Integer idGasto, String notas, double importe) {
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();

        String sql = "INSERT INTO MOVIMIENTOS (id_cuenta, fecha, id_tipo_movimiento, id_subtipo_movimiento, id_tipo_gasto, notas, importe) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCuenta);
            stmt.setString(2, fecha);
            stmt.setInt(3, tipo);
            stmt.setInt(4, idCategoria);
            // Cambiado: usa setNull para el caso en que idGasto sea null
            if (idGasto == null || idGasto == -1 || idGasto==0) {
                stmt.setNull(5, java.sql.Types.INTEGER);  // Establece null para id_tipo_gasto
            } else {
                stmt.setInt(5, idGasto);  // Si idGasto tiene valor, se pasa como entero
            }
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

    // Método para ELIMINAR MOVIMIENTO
    public boolean eliminarMovimiento(int idMovimiento) {
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();

        String sql = "DELETE FROM MOVIMIENTOS WHERE id_movimiento = ?";

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

    public String obtenerNombreTipoMovimiento(int idMovimiento) {
        String tipoMovimiento = "";
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();
        String query = "SELECT tm.descripcion AS tipo_movimiento_descripcion FROM MOVIMIENTOS m JOIN TIPOS_MOVIMIENTO tm ON m.id_tipo_movimiento = tm.id_tipo_movimiento WHERE m.id_movimiento = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idMovimiento);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    tipoMovimiento = rs.getString("tipo_movimiento_descripcion");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tipoMovimiento;
    }

    public String obtenerNombreSubtipoMovimiento(int idMovimiento) {
        String subtipoMovimiento = "";
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();

        // Consulta para obtener la descripción del subtipo basado en el id_movimiento
        String query = "SELECT sm.descripcion AS subtipo_descripcion "
                + "FROM MOVIMIENTOS m "
                + "JOIN SUBTIPOS_MOVIMIENTO sm ON m.id_subtipo_movimiento = sm.id_subtipo_movimiento "
                + "WHERE m.id_movimiento = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idMovimiento);

            // Ejecutamos la consulta y obtenemos el resultado
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    subtipoMovimiento = rs.getString("subtipo_descripcion");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subtipoMovimiento;
    }

    public String obtenerNombreGasto(int idMovimiento) {
        String nombreGasto = "";
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();

        // Consulta para obtener la descripción del gasto basado en el id_movimiento y con id_subtipo_movimiento = 9
        String query = "SELECT tg.descripcion "
                + "FROM MOVIMIENTOS m "
                + "JOIN TIPOS_GASTO tg ON m.id_tipo_gasto = tg.id_tipo_gasto "
                + "WHERE m.id_movimiento = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idMovimiento);

            // Ejecutamos la consulta y obtenemos el resultado
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    nombreGasto = rs.getString("descripcion");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar la conexión si es necesario
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return nombreGasto;
    }
    public Map<Integer, String> listaTipo() {
        Map<Integer, String> listaTipo = new HashMap<>();
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();

        String query = "SELECT id_tipo_movimiento, descripcion FROM TIPOS_MOVIMIENTO";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            //stmt.setString(1, null);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    listaTipo.put(rs.getInt("id_tipo_movimiento"), rs.getString("descripcion"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return listaTipo;
    }

    public Map<Integer, String> listaSubtipos(int idTipoMovimiento) {
        Map<Integer, String> listaSubtipos = new HashMap<>();
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();
        String query = "SELECT id_subtipo_movimiento, descripcion FROM SUBTIPOS_MOVIMIENTO WHERE id_tipo_movimiento = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idTipoMovimiento);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int idSubtipo = rs.getInt("id_subtipo_movimiento");
                    String descripcion = rs.getString("descripcion");
                    listaSubtipos.put(idSubtipo, descripcion);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return listaSubtipos;
    }

    public Map<Integer, String> listaGastos() {
        Map<Integer, String> listaGastos = new HashMap<>();
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();

        String query = "SELECT id_tipo_gasto, descripcion, tipo FROM TIPOS_GASTO";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            //stmt.setString(1, null);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    listaGastos.put(rs.getInt("id_tipo_gasto"), rs.getString("descripcion"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return listaGastos;
    }

}
