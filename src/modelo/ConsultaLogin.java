
package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Francisco Javier Gómez Gamero
 */
public class ConsultaLogin {
    
    // Método para validar si el usuario existe en la base de datos
    public boolean validarUsuario(String email, String password) {
        Conexion conexion = new Conexion();
        Connection con = conexion.getConexion();
        
        boolean esValido = false;
        String sql = "SELECT * FROM USUARIOS WHERE email = ? AND password = ?";
        
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, password);
            
            try (ResultSet rs = stmt.executeQuery()) {
                // Si el ResultSet tiene un resultado, el usuario es válido
                if (rs.next()) {
                    esValido = true;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al validar el usuario: " + e.getMessage());
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        
        return esValido;
    }
    
    
}
