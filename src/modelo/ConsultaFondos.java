package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.entidades.Fondo;

/**
 *
 * @author Francisco Javier Gómez Gamero
 */
public class ConsultaFondos {

    // Método para obtener todos los fondos asociados a un usuario específico
    public List<Fondo> obtenerFondos(int usuarioId) {
        List<Fondo> fondos = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();

        String sql = "SELECT FONDOS.id_fondo, FONDOS.nombre, FONDOS.tipo_fondo, FONDOS.isin, FONDOS.moneda, "
                + "FONDOS.riesgo, FONDOS.saldo "
                + "FROM FONDOS "
                + "WHERE FONDOS.id_usuario = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idFondo = rs.getInt("id_fondo");
                String nombre = rs.getString("nombre");
                int tipoFondo = rs.getInt("tipo_fondo");
                String isin = rs.getString("isin");
                String moneda = rs.getString("moneda");
                int riesgo = rs.getInt("riesgo");
                double saldo = rs.getDouble("saldo");

                fondos.add(new Fondo(idFondo, nombre, tipoFondo, isin, moneda, riesgo, saldo));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener fondos: " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        return fondos;
    }

    //Método para obtener el nombre del tipo de Fondo     
    public String obtenerNombreTipoFondo(int idFondo) {
        String tipoFondo = "";
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();
        String query = "SELECT tf.nombre AS tipo_fondo_nombre FROM FONDOS f JOIN TIPOS_FONDO tf ON f.tipo_fondo = tf.id_tipo_fondo WHERE f.id_fondo = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idFondo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    tipoFondo = rs.getString("tipo_fondo_nombre");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tipoFondo;
    }

    //Método para AÑADIR FONDO
    public boolean addFondo(int idUsuario, String nombre, String tipo, String isin, String moneda) {
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();

        // Verificamos si el tipo de fondo existe
        String sqlTipoFondo = "SELECT id_tipo_fondo FROM TIPOS_FONDO WHERE nombre = ?";
        int idTipoFondo = -1;

        try (PreparedStatement stmtTipo = conn.prepareStatement(sqlTipoFondo)) {
            stmtTipo.setString(1, tipo);
            ResultSet rs = stmtTipo.executeQuery();

            // Si no existe, lo insertamos en la tabla TIPOS_FONDO
            if (rs.next()) {
                idTipoFondo = rs.getInt("id_tipo_fondo");
            } else {
                // El tipo no existe, así que lo insertamos
                String sqlInsertTipoFondo = "INSERT INTO TIPOS_FONDO (nombre) VALUES (?)";
                try (PreparedStatement stmtInsertTipo = conn.prepareStatement(sqlInsertTipoFondo, Statement.RETURN_GENERATED_KEYS)) {
                    stmtInsertTipo.setString(1, tipo);
                    stmtInsertTipo.executeUpdate();

                    // Obtener el ID del tipo de fondo insertado
                    rs = stmtInsertTipo.getGeneratedKeys();
                    if (rs.next()) {
                        idTipoFondo = rs.getInt(1);
                    }
                }
            }

            // Ahora insertamos el fondo en la tabla FONDOS
            String sqlFondo = "INSERT INTO FONDOS (id_usuario, nombre, tipo_fondo, isin, moneda) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmtFondo = conn.prepareStatement(sqlFondo)) {
                stmtFondo.setInt(1, idUsuario); 
                stmtFondo.setString(2, nombre);
                stmtFondo.setInt(3, idTipoFondo);  // Usamos el id_tipo_fondo encontrado o insertado
                stmtFondo.setString(4, isin);
                stmtFondo.setString(5, moneda);
                stmtFondo.executeUpdate();
                return true;
            }

        } catch (SQLException e) {
            System.err.println("Error al añadir fondo: " + e.getMessage());
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
    
    //Método para ELIMINAR FONDO
    public boolean eliminarFondo(int idFondo) {
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();

        String sql = "DELETE FROM FONDOS WHERE id_fondo = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idFondo);
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar el fondo: " + e.getMessage());
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
    
    // Método para MODIFICAR NOMBRE DEL FONDO
    public boolean modificarNombreFondo(int idFondo, String nombre) {
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();

        String sql = "UPDATE FONDOS SET  nombre = ? WHERE id_fondo = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setInt(2, idFondo);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al modificar fondo: " + e.getMessage());
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
    
    //Método para obtener la cuenta por el idCuenta
    public Fondo obtenerFondoPorId(int idFondo) {
        Fondo fondo = null;
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();

        String sql = "SELECT FONDOS.id_fondo, FONDOS.nombre, FONDOS.isin, FONDOS.tipo_fondo, FONDOS.moneda, FONDOS.riesgo, FONDOS.saldo "
                + "FROM FONDOS "
                + "WHERE FONDOS.id_fondo = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idFondo);  // Asigna el ID del fondo al parámetro de la consulta
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {                
                String nombre = rs.getString("nombre");
                int tipo = rs.getInt("tipo_fondo");
                String isin = rs.getString("isin");                
                String moneda = rs.getString("moneda");
                int riesgo = rs.getInt("riesgo");
                double saldo = rs.getDouble("saldo");

                // Crea una instancia de Fondo con los datos obtenidos
                fondo = new Fondo(idFondo,nombre, tipo,isin,moneda, riesgo, saldo);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el fondo: " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        return fondo; // Devuelve la cuenta obtenida o null si no existe
    }

}
