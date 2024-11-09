package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        
        String sql = "SELECT id_banco, alias, iban, saldo FROM CUENTAS WHERE id_usuario = ?";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                int idBanco = rs.getInt("id_banco");
                String alias = rs.getString("alias");
                String iban = rs.getString("iban");
                double saldo = rs.getDouble("saldo");
                
                cuentas.add(new Cuenta(idBanco, alias, iban, saldo));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener cuentas: " + e.getMessage());
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        
        return cuentas;
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
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        
        return saldoTotal;
    }

    // Método para obtener los movimientos de una cuenta específica
    public List<Movimiento> obtenerMovimientos(String iban) {
        List<Movimiento> movimientos = new ArrayList<>();
        Conexion conexion = new Conexion();
        Connection conn = conexion.getConexion();
        
        String sql = "SELECT tipo, descripcion, monto, fecha FROM MOVIMIENTOS WHERE iban = ? ORDER BY fecha DESC";
        
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, iban);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                String tipo = rs.getString("tipo");
                String descripcion = rs.getString("descripcion");
                double monto = rs.getDouble("monto");
                String fecha = rs.getString("fecha");
                
                movimientos.add(new Movimiento(tipo, descripcion, monto, fecha));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener movimientos: " + e.getMessage());
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
        
        return movimientos;
    }

    // Clase anidada para representar una Cuenta
    public static class Cuenta {
        private int idBanco;
        private String alias;
        private String iban;
        private double saldo;

        public Cuenta(int idBanco, String alias, String iban, double saldo) {
            this.idBanco = idBanco;
            this.alias = alias;
            this.iban = iban;
            this.saldo = saldo;
        }

        public int getBanco() { return idBanco; }
        public String getAlias() { return alias; }
        public String getIban() { return iban; }
        public double getSaldo() { return saldo; }
    }

    public static class Movimiento {
        private String tipo;
        private String descripcion;
        private double monto;
        private String fecha;

        public Movimiento(String tipo, String descripcion, double monto, String fecha) {
            this.tipo = tipo;
            this.descripcion = descripcion;
            this.monto = monto;
            this.fecha = fecha;
        }

        public String getTipo() { return tipo; }
        public String getDescripcion() { return descripcion; }
        public double getMonto() { return monto; }
        public String getFecha() { return fecha; }
    }
}
