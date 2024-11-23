package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase para manejar la conexión con la base de datos.
 * Básicamente, aquí configuramos y creamos la conexión con MySQL.
 * 
 * @author Francisco Javier Gómez Gamero
 */
public class Conexion {
    // Variable para guardar la conexión
    Connection cx = null;

    // Configuración de la conexión (url, usuario y contraseña)
    String url = "jdbc:mysql://localhost:3306/MIECO";
    String usuario = "root";
    String password = "";

    /**
     * Este método crea y devuelve la conexión con la base de datos.
     * También imprime en la consola si la conexión fue exitosa o no.
     * 
     * @return La conexión a la base de datos o null si algo falla.
     */
    public Connection getConexion() {
        try {
            // Intenta cargar el controlador JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Intenta establecer la conexión con los datos configurados
            cx = DriverManager.getConnection(url, usuario, password);

            // Si todo salió bien, avisa en la consola
            if (cx != null) {
                System.out.println("Conexión correcta a la base de datos MySQL");
            }
        } catch (ClassNotFoundException e) {
            // Si no encuentra el controlador, avisa con un mensaje de error
            System.err.println("Error: No se pudo encontrar la clase del controlador JDBC");
        } catch (SQLException e) {
            // Si hay un error al conectar, también avisa
            System.err.println("Error: No se pudo conectar a la base de datos");
        }
        // Devuelve la conexión (o null si falló)
        return cx;
    }
}
