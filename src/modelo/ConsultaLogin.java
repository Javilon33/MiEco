package modelo;

import modelo.entidades.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase que realiza las consultas relacionadas con el inicio de sesión.
 * Proporciona métodos para validar usuarios y obtener sus datos desde la base de datos.
 * 
 * @author Francisco Javier Gómez Gamero
 */
public class ConsultaLogin {

    /**
     * Valida si un usuario existe en la base de datos según su email y contraseña.
     * 
     * @param email Email del usuario.
     * @param password Contraseña del usuario.
     * @return true si el usuario existe y las credenciales son correctas, false en caso contrario.
     */
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
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        return esValido;
    }

    /**
     * Obtiene los datos completos de un usuario según su email y contraseña.
     * 
     * @param email Email del usuario.
     * @param password Contraseña del usuario.
     * @return Un objeto Usuario con los datos del usuario, o null si no se encuentra.
     */
    public Usuario obtenerUsuario(String email, String password) {
        Usuario usuario = null;
        Conexion conexion = new Conexion();
        Connection con = conexion.getConexion();
        String sql = "SELECT * FROM USUARIOS WHERE email = ? AND password = ?";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    usuario = new Usuario(
                        rs.getInt("codigo"),
                        rs.getInt("rol"),
                        rs.getString("email"),
                        rs.getString("nombre"),
                        rs.getString("apellidos"),
                        rs.getString("telefono"),
                        rs.getDate("fecha"),
                        rs.getString("password")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el usuario: " + e.getMessage());
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        return usuario;
    }
}
