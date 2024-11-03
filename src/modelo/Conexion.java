
package modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Francisco Javier Gómez Gamero
 */
public class Conexion {
     Connection cx = null;
    
     // Configura la información de la conexión
     String url = "jdbc:mysql://localhost:3306/MIECO";
     String usuario = "root";
     String password = "";
     
     public Connection getConexion() {
         try {
            // Carga el controlador JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establece la conexión con la base de datos
            cx = DriverManager.getConnection(url, usuario, password);

            // Verifica si la conexión ha sido correcta
            if (cx != null) {
                System.out.println("Conexión correcta a la base de datos MySQL");
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Error: No se pudo encontrar la clase del controlador JDBC");
        } catch (SQLException e) {
            System.err.println("Error: No se pudo conectar a la base de datos");
        }
         return cx;
     }
}