package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Francisco Javier Gómez Gamero
 */
public class ConsultaCuentas {

    // Método para obtener todas las cuentas de un usuario específico
    public List<Cuenta> obtenerCuentas(int usuarioId) {
        List<Cuenta> cuentas = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();

        String sql = "SELECT CUENTAS.id_cuenta, CUENTAS.id_banco, BANCOS.nombre_banco, CUENTAS.alias, CUENTAS.iban, CUENTAS.saldo "
                + "FROM CUENTAS "
                + "JOIN BANCOS ON CUENTAS.id_banco = BANCOS.id_banco "
                + "WHERE CUENTAS.id_usuario = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idCuenta = rs.getInt("id_cuenta");
                int idBanco = rs.getInt("id_banco");
                String nombreBanco = rs.getString("nombre_banco");  // Obtener nombre del banco
                String alias = rs.getString("alias");
                String iban = rs.getString("iban");
                double saldo = rs.getDouble("saldo");

                cuentas.add(new Cuenta(idCuenta, idBanco, nombreBanco, alias, iban, saldo));
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
        return cuentas;
    }

    //Método par aobtener la cuenta por el idCuenta
    public Cuenta obtenerCuentaPorId(int idCuenta) {
        Cuenta cuenta = null;
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();

        String sql = "SELECT CUENTAS.id_cuenta, CUENTAS.id_banco, BANCOS.nombre_banco, CUENTAS.alias, CUENTAS.iban, CUENTAS.saldo "
                + "FROM CUENTAS "
                + "JOIN BANCOS ON CUENTAS.id_banco = BANCOS.id_banco "
                + "WHERE CUENTAS.id_cuenta = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCuenta);  // Asigna el ID de la cuenta al parámetro de la consulta
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int idBanco = rs.getInt("id_banco");
                String nombreBanco = rs.getString("nombre_banco");
                String alias = rs.getString("alias");
                String iban = rs.getString("iban");
                double saldo = rs.getDouble("saldo");

                // Crea una instancia de Cuenta con los datos obtenidos
                cuenta = new Cuenta(idCuenta, idBanco, nombreBanco, alias, iban, saldo);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener la cuenta: " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        return cuenta; // Devuelve la cuenta obtenida o null si no existe
    }

    // Método para obtener el saldo total de todas las cuentas de un usuario
    public double obtenerSaldoTotal(int usuarioId) {
        double saldoTotal = 0.0;
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();

        String sql = "SELECT SUM(saldo) AS saldo_total FROM CUENTAS WHERE id_usuario = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
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

    // Método para AÑADIR CUENTA
    public boolean añadirCuenta(int usuarioId, int idBanco, String alias, String iban, double saldo) {
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();

        String sql = "INSERT INTO CUENTAS (id_usuario, id_banco, alias, iban, saldo_inicial, saldo) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            stmt.setInt(2, idBanco);
            stmt.setString(3, alias);
            stmt.setString(4, iban);
            stmt.setDouble(5, saldo);
            stmt.setDouble(6, saldo);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al añadir cuenta: " + e.getMessage());
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

    // Método para MODFICAR CUENTA
    public boolean modificarCuenta(int cuentaId, int idBanco, String alias, String iban, double saldo) {
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();

        String sql = "UPDATE CUENTAS SET id_banco = ?, alias = ?, iban = ?, saldo = ? WHERE id_cuenta = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idBanco);
            stmt.setString(2, alias);
            stmt.setString(3, iban);
            stmt.setDouble(4, saldo);
            stmt.setInt(5, cuentaId);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al modificar cuenta: " + e.getMessage());
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

    //Método para ELIMINAR CUENTA
    public boolean eliminarCuenta(int idCuenta) {
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();

        String sql = "DELETE FROM CUENTAS WHERE id_cuenta = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCuenta);
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar la cuenta: " + e.getMessage());
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

    // Método para obtener una lista de bancos con sus IDs
    public Map<Integer, String> obtenerListaBancos() {
        Map<Integer, String> bancos = new HashMap<>();
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();

        String sql = "SELECT id_banco, nombre_banco FROM BANCOS";

        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int idBanco = rs.getInt("id_banco");
                String nombreBanco = rs.getString("nombre_banco");
                bancos.put(idBanco, nombreBanco);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener lista de bancos: " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }

        return bancos;
    }

    // Método para obtener el nombre de una cuenta específica
    public String obtenerNombreCuenta(int idCuenta) {
        String nombreCuenta = "";
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();

        String sql = "SELECT alias FROM CUENTAS WHERE id_cuenta = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCuenta);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                nombreCuenta = rs.getString("alias");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el nombre de la cuenta: " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }

        return nombreCuenta;
    }

    // Método para obtener el nombre del banco a partir del idCuenta
    public String obtenerNombreBanco(int idCuenta) {
        String nombreBanco = "";
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();

        String sql = "SELECT b.nombre_banco "
                + "FROM CUENTAS c "
                + "JOIN BANCOS b ON c.id_banco = b.id_banco "
                + "WHERE c.id_cuenta = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCuenta);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    nombreBanco = resultSet.getString("nombre_banco");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al obtener el nombre del banco: " + e.getMessage());
        }

        return nombreBanco;
    }

    // Método para actualizar el saldo de la cuenta
    public boolean actualizarSaldoCuenta(int idCuenta, double nuevoSaldo) {
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();

        String sql = "UPDATE CUENTAS SET saldo = ? WHERE id_cuenta = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, nuevoSaldo);
            stmt.setInt(2, idCuenta);
            int filasAfectadas = stmt.executeUpdate();
            return filasAfectadas > 0; // Retorna true si se actualizó correctamente
        } catch (SQLException e) {
            System.err.println("Error al actualizar el saldo de la cuenta: " + e.getMessage());
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

    public double obtenerSaldoInicial(int idCuenta) {
        double saldoInicial = 0.0;
        String sql = "SELECT saldo_inicial FROM CUENTAS WHERE id_cuenta = ?";
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idCuenta);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    saldoInicial = rs.getDouble("saldo_inicial");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Maneja la excepción según tu necesidad, quizás lanzándola o registrándola en un log
        }

        return saldoInicial;
    }

    //----------------------------------------------------
    //-------------Metodos para los MOVIMIENTOS------------
    //----------------------------------------------------
    

    public String obtenerNombreCategoria(int idCategoria) {
        // Lógica para obtener el nombre de la categoría a partir del id
        String nombreCategoria = "";
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();
        String query = "SELECT nombre FROM CATEGORIAS WHERE id_categoria = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idCategoria);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    nombreCategoria = rs.getString("nombre");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nombreCategoria;
    }

    public String obtenerNombreSubcategoria(int idSubcategoria) {
        // Lógica para obtener el nombre de la subcategoría a partir del id
        String nombreSubcategoria = "";
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();
        String query = "SELECT nombre FROM SUBCATEGORIAS WHERE id_subcategoria = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idSubcategoria);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    nombreSubcategoria = rs.getString("nombre");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nombreSubcategoria;
    }

    public Map<Integer, String> obtenerListaCategorias(String tipoMovimiento) {
        Map<Integer, String> listaCategorias = new HashMap<>();
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();

        String query = "SELECT id_categoria, nombre FROM CATEGORIAS WHERE tipo = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, tipoMovimiento);  // "Ingreso" o "Pago"
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    listaCategorias.put(rs.getInt("id_categoria"), rs.getString("nombre"));
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

        return listaCategorias;
    }

    // Método en ConsultaCuentas para obtener la lista de subcategorías
    public Map<Integer, String> obtenerListaSubcategorias(int idCategoria) {
        Map<Integer, String> listaSubcategorias = new HashMap<>();
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();
        String query = "SELECT id_subcategoria, nombre FROM SUBCATEGORIAS WHERE id_categoria = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idCategoria);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int idSubcategoria = rs.getInt("id_subcategoria");
                    String nombreSubcategoria = rs.getString("nombre");
                    listaSubcategorias.put(idSubcategoria, nombreSubcategoria);
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

        return listaSubcategorias;
    }

    // Método en ConsultaCuentas para obtener el id de una subcategoría dado el id de la categoría y el nombre de la subcategoría
    public int obtenerIdSubcategoria(int idCategoria, String nombreSubcategoria) {
        int idSubcategoria = -1;  // Devuelve -1 si no se encuentra la subcategoría
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();
        String query = "SELECT id_subcategoria FROM SUBCATEGORIAS WHERE id_categoria = ? AND nombre = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idCategoria);
            stmt.setString(2, nombreSubcategoria);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    idSubcategoria = rs.getInt("id_subcategoria");
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

        return idSubcategoria;
    }    

}
