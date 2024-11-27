package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import modelo.entidades.Deposito;

/**
 *
 * @author Francisco Javier Gómez Gamero
 */
public class ConsultaDepositos {

    // Método para obtener todas los depósitos de un usuario específico
    public List<Deposito> obtenerDepositos(int usuarioId) {
        List<Deposito> depositos = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();

        String sql = "SELECT DEPOSITOS.id_deposito,  DEPOSITOS.nombre, DEPOSITOS.banco, DEPOSITOS.fecha_inicio, DEPOSITOS.plazo_meses, DEPOSITOS.importe, DEPOSITOS.interes  "
                + "FROM DEPOSITOS "
                + "WHERE DEPOSITOS.id_usuario = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idDeposito = rs.getInt("id_deposito");
                String nombre = rs.getString("nombre");
                String banco = rs.getString("banco");
                Date fechaInicio = rs.getDate("fecha_inicio");
                int meses = rs.getInt("plazo_meses");
                double importeInicial = rs.getDouble("importe");
                double interes = rs.getDouble("interes");
                double importeFinal = calcularImporteFinal(fechaInicio, meses, importeInicial, interes);

                depositos.add(new Deposito(idDeposito, usuarioId, nombre, banco, fechaInicio, meses, importeInicial, interes, importeFinal));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener cuentas: " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        return depositos;
    }

    // Método para calcular el importe final del depósito después de retenciones sobre los intereses
    public double calcularImporteFinal(Date fechaInicio, int meses, double importeInicial, double interes) {
        // Convertir la duración en meses a años
        double anios = meses / 12.0;

        // Calcular el importe total con interés compuesto
        double importeConIntereses = importeInicial * Math.pow((1 + interes / 100), anios);

        // Calcular los intereses generados
        double interesesGenerados = importeConIntereses - importeInicial;

        // Aplicar la retención del 19% sobre los intereses
        double retencion = interesesGenerados * 0.19;

        // Calcular el importe final después de retenciones
        double importeFinal = importeConIntereses - retencion;

        return importeFinal;
    }

    // Método para AÑADIR DEPOSITO
    public boolean addDeposito(int idUsuario, String nombre, String banco, String fechaInicio, int meses, double importeInicial, double interesAnual) {
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();

        String sql = "INSERT INTO DEPOSITOS (id_usuario, nombre, banco, fecha_inicio, plazo_meses, importe, interes) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            stmt.setString(2, nombre);
            stmt.setString(3, banco);
            stmt.setString(4, fechaInicio);
            stmt.setInt(5, meses);
            stmt.setDouble(6, importeInicial);
            stmt.setDouble(7, interesAnual);

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al añadir el depósito: " + e.getMessage());
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

    // Método para MODIFICAR DEPOSITO
    public boolean modificarDeposito(int idDeposito, String nombre, String banco, String fechaInicio, int meses, double importeInicial, double interesAnual) {
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();

        String sql = "UPDATE DEPOSITOS SET nombre = ?, banco = ?, fecha_inicio = ?, plazo_meses = ?, importe = ?, interes = ? WHERE id_deposito = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, banco);
            stmt.setString(3, fechaInicio);
            stmt.setInt(4, meses);
            stmt.setDouble(5, importeInicial);
            stmt.setDouble(6, interesAnual);
            stmt.setInt(7, idDeposito); // Cláusula WHERE

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al añadir el depósito: " + e.getMessage());
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

    //Método para ELIMINAR DEPOSITO 
    public boolean eliminarDeposito(int idDeposito) {
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();

        String sql = "DELETE FROM DEPOSITOS WHERE id_deposito = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idDeposito);
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar el depósito: " + e.getMessage());
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

    public Deposito obtenerDepositoPorId(int idDeposito) {
        Deposito deposito = null;
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();

        String sql = "SELECT DEPOSITOS.id_usuario,  DEPOSITOS.nombre, DEPOSITOS.banco, DEPOSITOS.fecha_inicio, DEPOSITOS.plazo_meses, DEPOSITOS.importe, DEPOSITOS.interes  "
                + "FROM DEPOSITOS "
                + "WHERE DEPOSITOS.id_deposito = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idDeposito);  // Asigna el ID del movimiento al parámetro de la consulta
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int idUsuario = rs.getInt("id_usuario");
                String nombre = rs.getString("nombre");
                String banco = rs.getString("banco");
                Date fechaInicio = rs.getDate("fecha_inicio");
                int meses = rs.getInt("plazo_meses");
                double importeInicial = rs.getDouble("importe");
                double interes = rs.getDouble("interes");
                double importeFinal = calcularImporteFinal(fechaInicio, meses, importeInicial, interes);

                // Crea una instancia de Movimiento con los datos obtenidos
                deposito = new Deposito(idDeposito, idUsuario, nombre, banco, fechaInicio, meses, importeInicial, interes, importeFinal);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el depósito: " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        return deposito; // Devuelve el movimiento obtenido o null si no existe
    }
    
    public double obtenerTotalDepositos(int idUsuario){
        
        double saldoTotal = 0.0;        
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();

        String sql = "SELECT SUM(importe) AS saldo_total FROM DEPOSITOS WHERE id_usuario = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                saldoTotal = rs.getDouble("saldo_total");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el saldo total: " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        return saldoTotal;
    }

}
