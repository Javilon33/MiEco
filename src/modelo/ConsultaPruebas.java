package modelo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;

public class ConsultaPruebas {
    private Connection conexion;

    public ConsultaPruebas(Connection conexion) {
        this.conexion = conexion;
    }

    public DefaultTableModel obtenerUsuarios() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Código");
        modelo.addColumn("Admin");
        modelo.addColumn("Email");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellidos");
        modelo.addColumn("Teléfono");
        modelo.addColumn("Fecha");
        modelo.addColumn("Contraseña");

        String sql = "SELECT * FROM USUARIOS";
        try {
            Statement statement = conexion.createStatement();
            ResultSet resultado = statement.executeQuery(sql);

            while (resultado.next()) {
                Object[] fila = new Object[8];
                fila[0] = resultado.getInt("codigo");
                fila[1] = resultado.getInt("admin");
                fila[2] = resultado.getString("email");
                fila[3] = resultado.getString("nombre");
                fila[4] = resultado.getString("apellidos");
                fila[5] = resultado.getString("telefono");
                fila[6] = resultado.getDate("fecha");
                fila[7] = resultado.getString("password");
                modelo.addRow(fila);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener usuarios: " + e.getMessage());
        }

        return modelo;
    }
}
