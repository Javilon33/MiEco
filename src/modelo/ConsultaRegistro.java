
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Francisco Javier Gómez Gamero
 */
public class ConsultaRegistro {
    
    // Método para insertar un nuevo usuario en la base de datos
    public boolean registrarUsuario(String email, String nombre, String apellidos, String telefono, String fecha, String password) {
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();
        
        boolean registroExitoso = false;
        String sql = "INSERT INTO USUARIOS (admin, email, nombre, apellidos, telefono, fecha, password) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, 0); // Campo "admin" se establece siempre a 0
            stmt.setString(2, email);
            stmt.setString(3, nombre);
            stmt.setString(4, apellidos);
            stmt.setString(5, telefono);
            stmt.setString(6, fecha);
            stmt.setString(7, password);

            // Ejecuta la inserción
            int filasInsertadas = stmt.executeUpdate();
            registroExitoso = filasInsertadas > 0; // Verifica si la inserción fue exitosa

        } catch (SQLException e) {
            System.err.println("Error al registrar usuario: " + e.getMessage());
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }

        return registroExitoso;
    }
    
    
}
