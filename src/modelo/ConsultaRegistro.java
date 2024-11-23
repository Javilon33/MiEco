package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Clase para manejar las consultas relacionadas con el registro de nuevos usuarios.
 * 
 * @author Francisco Javier Gómez Gamero
 */
public class ConsultaRegistro {

    /**
     * Registra un nuevo usuario en la base de datos.
     * 
     * @param email Email del usuario.
     * @param nombre Nombre del usuario.
     * @param apellidos Apellidos del usuario.
     * @param telefono Teléfono del usuario.
     * @param fecha Fecha nacimiento del usuario.
     * @param password Contraseña del usuario.
     * @return true si el registro fue exitoso, false en caso contrario.
     */
    public boolean registrarUsuario(String email, String nombre, String apellidos, String telefono, String fecha, String password) {
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();

        boolean registroExitoso = false;
        String sql = "INSERT INTO USUARIOS (rol, email, nombre, apellidos, telefono, fecha, password) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, 0); // Asignar rol 0 (USUARIO estándar) por defecto
            stmt.setString(2, email);
            stmt.setString(3, nombre);
            stmt.setString(4, apellidos);
            stmt.setString(5, telefono);
            stmt.setString(6, fecha);
            stmt.setString(7, password);

            // Ejecutar la inserción
            int filasInsertadas = stmt.executeUpdate();
            registroExitoso = filasInsertadas > 0; // Verificar si se insertaron filas

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
